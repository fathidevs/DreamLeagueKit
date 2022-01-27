package com.gmail.dreamleaguekit.favorite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.gmail.dreamleaguekit.PlayersDB;
import com.gmail.dreamleaguekit.R;
import com.gmail.dreamleaguekit.modal.SavesModel;
import com.gmail.dreamleaguekit.posts.KitPreviewActivity;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class FavoriteFragment extends Fragment {

    private static final int RC_SIGN_IN = 120;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // Components
    private RecyclerView nSaves_list_rv;
    private LinearLayout nFav_signed_out_layout;
    private LinearLayout nFav_signed_in_layout;
    private SwipeRefreshLayout nFave_refresh;
    private SignInButton nG_sign_in_btn;

    // Firestore
    private FirestoreRecyclerOptions<SavesModel> options;
    private FirestoreRecyclerAdapter<SavesModel, SavesListViewHolder> adapter;

    // Google sign-in
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private GoogleSignInOptions gso;

    // Current user
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    // Date
    private String currentDateTime;
    private SimpleDateFormat simpleDateFormat;
    private Date date;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        init();

        signedInOutLayoutStatus();

        nG_sign_in_btn.setOnClickListener(view -> signIn());

        showSavesList();

        return view;
    }

    private void init() {
        // Components
        nSaves_list_rv = view.findViewById(R.id.saves_list_rv);
        nFav_signed_out_layout = view.findViewById(R.id.fav_signed_out_layout);
        nFav_signed_in_layout = view.findViewById(R.id.fav_signed_in_layout);
        nG_sign_in_btn = view.findViewById(R.id.g_sign_in_btn_f);
        nFave_refresh = view.findViewById(R.id.fav_refresh);

        // Current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Date
        simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        date = Calendar.getInstance().getTime();
        currentDateTime = simpleDateFormat.format(date);

        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                // Google Sign In was successful, authenticate with Firebase

                    account = task.getResult(ApiException.class);
                    Log.w("onActivityResult", "signInResult: "+account.getEmail());


                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                handleSignInResult(task);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("onActivityResult", "Google sign in failed: "+ e.getMessage());
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), task -> {
                    if (task.isSuccessful()) {

                        Log.d("firebaseAuthWithGoogle", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {addUserToFirestore(user);}
                    } else {

                        Log.w("firebaseAuthWithGoogle", "signInWithCredential:failure", task.getException());
                        Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

        } catch (ApiException e)
        {
            Log.w("handleSignInResult", "signInResult:failed code = " + e.getStatusCode());
        }
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
        usersMap.put("date", currentDateTime);
        users_collection.set(usersMap, SetOptions.merge()).addOnCompleteListener(task1 ->
        {
            if (task1.isComplete()) {
                if (task1.isSuccessful()) {
                    userSignedIinLayout();

                    Objects.requireNonNull(getActivity()).finish();
                    getActivity().overridePendingTransition(0, 0);
                    startActivity(getActivity().getIntent());
                    getActivity().overridePendingTransition(0, 0);
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void userSignedOutLayout() {
        //nFav_signed_in_layout.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(nFav_signed_out_layout, new AutoTransition());
        nFav_signed_out_layout.setVisibility(View.VISIBLE);
    }

    private void userSignedIinLayout() {
        TransitionManager.beginDelayedTransition(nFav_signed_out_layout, new AutoTransition());
        nFav_signed_out_layout.setVisibility(View.GONE);
        //nFav_signed_in_layout.setVisibility(View.VISIBLE);
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

    private void showSavesList() {
        if (mUser != null)
        {
            try {

                userSignedIinLayout();
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                Query query = firestore.collection("users").document(mUser.getUid())
                        .collection("my_saves");

                options = new FirestoreRecyclerOptions.Builder<SavesModel>()
                        .setQuery(query.orderBy("time", Query.Direction.DESCENDING), SavesModel.class)
                        .build();

                adapter = new FirestoreRecyclerAdapter<SavesModel, SavesListViewHolder>(options) {
                    @NonNull
                    @Override
                    public SavesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saves_list_item, parent, false);
                        return new SavesListViewHolder(v);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull SavesListViewHolder holder, int position, @NonNull SavesModel model) {
                        Picasso.get().load(model.getSaved_team_logo())
                                .resize(1000, 1000)
                                .centerInside()
                                .placeholder(R.drawable.ic_branded_img_placeholder)
                                .into(holder.nSaved_team_iv);
                        holder.nTeam_name.setText(model.getSaved_team_name());
                        holder.nTeam_league.setText(model.getSaved_team_league_name());
                        holder.nSaves_card.setOnClickListener(view -> {
                            Intent viewIntent = new Intent(getContext(), KitPreviewActivity.class);
                            viewIntent.putExtra("team_name", model.getSaved_team_name());
                            startActivity(viewIntent);
                        });
                    }
                };
                nSaves_list_rv.setHasFixedSize(true);
                nSaves_list_rv.setLayoutManager(new LinearLayoutManager(getContext()));
                nSaves_list_rv.setAdapter(adapter);
                nFave_refresh.setOnRefreshListener(() ->
                {
                    adapter.startListening();
                    adapter.notifyDataSetChanged();
                    nFave_refresh.setRefreshing(false);
                });

            } catch (Exception e) {
                Log.e("showSavesList", e.getMessage());
            }
        }
        else
        {
            userSignedOutLayout();
        }
    }

    private static class SavesListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView nSaved_team_iv;
        private final TextView nTeam_name;
        private final TextView nTeam_league;
        private final CardView nSaves_card;
        
        public SavesListViewHolder(@NonNull View itemView) 
        {
            super(itemView);
            nSaved_team_iv = itemView.findViewById(R.id.saved_team_iv);
            nTeam_name = itemView.findViewById(R.id.team_name);
            nTeam_league = itemView.findViewById(R.id.team_league);
            nSaves_card = itemView.findViewById(R.id.saves_card);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mUser != null)
        {
            adapter.stopListening();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mUser != null)
        {
            adapter.startListening();
        }
    }
}