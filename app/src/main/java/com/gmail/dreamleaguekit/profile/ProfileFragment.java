package com.gmail.dreamleaguekit.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.dreamleaguekit.PlayersDB;
import com.gmail.dreamleaguekit.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final int RC_SIGN_IN = 120;
    private static final String LOGOUT_TITLE = "Logout";
    private static final String LOGOUT_MESSAGE = "Do you really want to logout?";
    private static final String POSITIVE_BTN = "Yes";
    private static final String NEGATIVE_BTN = "NO!";

    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextView nUsername;
    private ImageView nUser_photo;
    private RelativeLayout nG_sign_in_layout;
    private RelativeLayout nG_sign_out_layout;
    private SignInButton nG_sign_in_button;
    private LinearLayout nLogout;
    private LinearLayout nInvite;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private GoogleSignInOptions gso;

    // Date
    private String currentDateTime;

    View view;

    @Override
    public void onStart() {
        super.onStart();

        userInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        init();

        userInfo();

        signedInOutLayoutStatus();

        nG_sign_in_button.setOnClickListener(view_in -> signIn());

        nInvite.setOnClickListener(view1 -> sendDiscordInvitation());
        nLogout.setOnClickListener(view1 -> logoutDialog());

        return view;
    }
    private void sendDiscordInvitation() {
        String link = "https://discord.gg/NFv2rqjr5q";
        String shareMsg = "\nJoin Dream League Soccer Kits discord\n\n";
        shareMsg = shareMsg + link;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "KIT");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
        startActivity(Intent.createChooser(shareIntent, "Choose one"));
    }
    private void logoutDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View logout_dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.logout_dialog, null, false);
        builder.setTitle(LOGOUT_TITLE);
        builder.setMessage(LOGOUT_MESSAGE);
        //builder.setView(logout_dialog_view);

        builder.setPositiveButton(POSITIVE_BTN, (dialogInterface, i) -> logOut());
        builder.setNegativeButton(NEGATIVE_BTN, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void init() {
        // Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        currentDateTime = simpleDateFormat.format(date);

        // current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // components
        nG_sign_in_layout = view.findViewById(R.id.g_sign_in_layout);
        nG_sign_out_layout = view.findViewById(R.id.g_sign_out_layout);
        nUsername = view.findViewById(R.id.username);
        nUser_photo = view.findViewById(R.id.comment_user_photo);
        nG_sign_in_button = view.findViewById(R.id.g_sign_in_btn_p);
        nLogout = view.findViewById(R.id.logout_btn);
        nInvite = view.findViewById(R.id.discord_invite_btn);

        // google sign in
        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        // google client
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), gso);
    }

    private void signedInOutLayoutStatus() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null)
        {
            userSignedIinLayout();
        }
        else
        {
            userSignedOutLayout();
        }
    }

    private void userSignedOutLayout() {
        nG_sign_out_layout.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(nG_sign_in_layout, new AutoTransition());
        nG_sign_in_layout.setVisibility(View.VISIBLE);
    }

    private void userSignedIinLayout() {
        nG_sign_in_layout.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(nG_sign_out_layout, new AutoTransition());
        nG_sign_out_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                handleSignInResult(task);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("google sign in", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), task -> {
                    if (task.isSuccessful()) {

                        Log.d("firebaseAuthWithGoogle", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {addUserToFirestore(user);}
                        updateUI(user);
                    } else {

                        Log.w("firebaseAuthWithGoogle", "signInWithCredential:failure", task.getException());
                        Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void addUserToFirestore(FirebaseUser user) {
        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        DocumentReference users_collection = firestore.collection("users")
                .document(user.getUid());
        HashMap<String, String> usersMap = new HashMap<>();
        usersMap.put("username", user.getDisplayName());
        usersMap.put("user_photo", String.valueOf(user.getPhotoUrl()));
        usersMap.put("user_email", user.getEmail());
        usersMap.put("joined_at", currentDateTime);
        users_collection.set(usersMap, SetOptions.merge()).addOnCompleteListener(task1 ->
        {
            if (task1.isComplete()) {
                if (task1.isSuccessful()) {
                    new PlayersDB(getContext()).playerCheck();
                }
                else {
                    Toast.makeText(getContext(),
                            ""+ Objects.requireNonNull(task1.getException()).getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            //updateUI(account);
        } catch (ApiException e)
        {

            Log.w("handleSignInResult", "signInResult:failed code = " + e.getStatusCode());
            updateUI(null);
        }
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
        .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), task ->
        {
            revokeAccess();
            userSignedOutLayout();
        });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), task -> {
                    // ...
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null)
        {
            try {
                userInfo();

            }catch (Exception e)
            {
                Log.e("updateUI_if_not_null", e.getMessage());
            }
        }
    }

    private void userInfo() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null)
        {
            try {
                userSignedIinLayout();
                Uri photoUrl = mUser.getPhotoUrl();
                String name = mUser.getDisplayName();

                Picasso.get().load(photoUrl)
                        .placeholder(R.drawable.ic_branded_profile_iv_placeholder)
                        .into(nUser_photo);

                nUsername.setText(name);

            }catch (Exception e) {
                Log.e("userInfo", e.getMessage());
            }
        }
    }
}