package com.gmail.dreamleaguekit.posts;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.gmail.dreamleaguekit.CommentsActivity;
import com.gmail.dreamleaguekit.PlayersDB;
import com.gmail.dreamleaguekit.R;
import com.gmail.dreamleaguekit.modal.KitsModal;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class KitPreviewActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 120;
    private static final int start = 0;
    private static final int halfway = 40;
    private static final int end = 50;

    private ViewPager nView_pager;
    private TabLayout nTab_layout;
    private LottieAnimationView nLike_btn;
    private ImageButton nSave_btn;
    private TextView nLikes;
    private LinearLayout nCopy_banner;
    private LinearLayout nAdLayout;
    private ImageButton nComment_btn;
    private ImageButton nShare_btn;

    private AdView nAd_view_kit_pre;
    private AdRequest adRequest;
    //private static final String TEST_AD_BANNER = "ca-app-pub-3940256099942544/6300978111";
    //private static final String MY_AD_BANNER = "ca-app-pub-6999642325846534/6871124949";

    private Animation scale_up;
    private Animation scale_down;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    // google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    private String[] imagesUrl;
    private String notRele;

    private Bundle bundle;
    private String team_name;

    // Date
    private String currentDateTime;
    private SimpleDateFormat simpleDateFormat;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_preview);

        init();

        showAdBanner();

        revealnHideBanner();

        myLikeStatus();

        nLike_btn.setOnClickListener(view -> {
            if (mUser != null) {
                if (isOnline()){
                    userLikeUnlikePost();
                }else {
                    Toast.makeText(this, "please check your internet connection",
                            Toast.LENGTH_LONG).show();
                }
            }
            else {
                signInSnackbar(view);
            }
        });

        nComment_btn.setOnClickListener(this::toCommentsActivity);

        saveBtnClickEff();
        nSave_btn.setOnClickListener(this::saveKitToMyList);

        nShare_btn.setOnClickListener(view -> shareKit());

        showImageSlider();
        likesStatus();
        savesStatus();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        bundle = getIntent().getExtras();
        team_name = bundle.getString("team_name");

        nView_pager = findViewById(R.id.view_pager);
        nTab_layout = findViewById(R.id.tab_layout);
        nComment_btn = findViewById(R.id.comment_btn);
        nShare_btn = findViewById(R.id.share_btn);
        nSave_btn = findViewById(R.id.save_btn);
        nLike_btn = findViewById(R.id.like);
        nLikes = findViewById(R.id.likes);
        nCopy_banner = findViewById(R.id.copy_banner);
        nAdLayout = findViewById(R.id.adLayout);
        nAd_view_kit_pre = findViewById(R.id.ad_view_main_kit_pre);

        adRequest = new AdRequest.Builder().build();

        // Date
        simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        date = Calendar.getInstance().getTime();
        currentDateTime = simpleDateFormat.format(date);

        // Google sign-in
        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        scale_up = AnimationUtils.loadAnimation(this, R.anim.btn_scale_up);
        scale_down = AnimationUtils.loadAnimation(this, R.anim.btn_scale_down);
    }
    private KitsModal setKyes() {
        KitsModal modal = new KitsModal();
        modal.setTeam_name("team_name");
        modal.setLeague_name("league_name");

        modal.setLeague_logo("league_logo");
        modal.setTeam_logo("team_logo");

        modal.setHome_kit("home_kit");
        modal.setAway_kit("away_kit");
        modal.setThird_kit("third_kit");

        modal.setGk_home_kit("gk_home_kit");
        modal.setGk_away_kit("gk_away_kit");
        modal.setGk_third_kit("gk_third_kit");
        modal.setDate("date");
        return modal;
    }

    private void revealnHideBanner() {
        Handler handler = new Handler();
        handler.postDelayed(() ->
        {
            TransitionManager.beginDelayedTransition(nCopy_banner, new AutoTransition());
            //nCopy_banner.setVisibility(View.VISIBLE);
        },1000);

        handler.postDelayed(() ->
        {
            TransitionManager.beginDelayedTransition(nCopy_banner, new AutoTransition());
            //nCopy_banner.setVisibility(View.INVISIBLE);
        },6000);
    }

    private void showAdBanner() {
        MobileAds.initialize(this, initializationStatus -> {

        });
        nAd_view_kit_pre.loadAd(adRequest);
        //nAd_view_kit_pre.setAdUnitId(TEST_AD_BANNER);
    }

    private void showImageSlider() {
        try {

            KitsModal modal = setKyes();
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("kits").document(team_name)
                    .get().addOnSuccessListener(documentSnapshot ->
            {
                String logo = String.valueOf(documentSnapshot.get(modal.getTeam_logo()));

                String home = String.valueOf(documentSnapshot.get(modal.getHome_kit()));
                String away = String.valueOf(documentSnapshot.get(modal.getAway_kit()));
                String third = String.valueOf(documentSnapshot.get(modal.getThird_kit()));

                String gk_home = String.valueOf(documentSnapshot.get(modal.getGk_home_kit()));
                String gk_away = String.valueOf(documentSnapshot.get(modal.getGk_away_kit()));
                String gk_third = String.valueOf(documentSnapshot.get(modal.getGk_third_kit()));

                String nRele = String.valueOf(documentSnapshot.get(modal.getDate()));

                imagesUrl = new String[]{logo, home, away, third, gk_home, gk_away, gk_third};
                //keys = new String[]{"Logo", "Home kit", "Away kit", "Third kit", "GK Home kit", "GK Away kit", "GK Third kit"};

                ViewKitImagesAdapter adapter = new ViewKitImagesAdapter(this, imagesUrl, nRele);
                nView_pager.setAdapter(adapter);
                nTab_layout.setupWithViewPager(nView_pager, true);
            });

        }catch (Exception e)
        {
            Log.e("showImageSlider", e.getMessage());
        }
    }

    private void userLikeUnlikePost() {
        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        DocumentReference my_likes = firestore.collection("users")
                .document(mUser.getUid())
                .collection("my_likes")
                .document(team_name);
        my_likes.get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                if (task.isComplete()) {
                    if (task.isSuccessful()) {
                        Long my_like_status = task.getResult().getLong("my_like_status");
                        if (my_like_status != null){
                            if (my_like_status == 0) {
                                myLike(1);
                                likes(1);
                                btnClicked(start, halfway);
                            }
                            else if (my_like_status == 1) {
                                myLike(0);
                                likes(0);
                                btnClicked(halfway, end);
                            }
                        }
                    }
                    else {
                        Toast.makeText(KitPreviewActivity.this,
                                ""+ Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else {
                myLike(1);
                likes(1);
                btnClicked(start, halfway);
            }
        });
    } // add/remove user like to user firestore
    private void myLikeStatus() {
        try {
            if (mUser != null)
            {
                FirebaseFirestore firestore;
                firestore = FirebaseFirestore.getInstance();
                DocumentReference my_likes = firestore.collection("users")
                        .document(mUser.getUid())
                        .collection("my_likes")
                        .document(team_name);

                    my_likes.addSnapshotListener((value, error) ->
                    {
                        if (value != null) {
                            if (value.exists()) {
                                long my_like_status = value.getLong("my_like_status");
                                if (my_like_status == 1) {
                                    btnClicked(0, 40);
                                }
                            }
                        }
                    });
            }
        }catch (Exception e)
        {
            Log.e("myLikeStatus", e.getMessage());
        }
    } // user likes real-time counter
    private void btnClicked(int start, int end) {
        try {
            nLike_btn.setMinFrame(start);
            nLike_btn.setMaxFrame(end);
            nLike_btn.playAnimation();

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("btnClicked", e.getMessage());
        }
    } // like custom effect
    private void likesStatus() {
        try {


            FirebaseFirestore firestore;
            firestore = FirebaseFirestore.getInstance();
            DocumentReference likes = firestore.collection("likes").document(team_name);
            likes.addSnapshotListener((value, error) ->
            {
                if (value != null)
                {
                    if (value.exists()) {
                        long likes_counter = value.getLong("likes_counter");
                        DecimalFormat format = new DecimalFormat("#,###,###");
                        String formated_likes_counter = format.format(likes_counter);
                        if (likes_counter <= 0) {
                            nLikes.setText("");
                        } else if (likes_counter == 1) {
                            nLikes.setText(formated_likes_counter + " kick");
                        } else if (likes_counter > 1 || likes_counter < 1000000) {

                            nLikes.setText(formated_likes_counter + " kicks");
                        } else if (likes_counter >= 1000000) {
                            nLikes.setText(" 1000000+ kicks");
                            //nLikes.setText("kicked by "+formated_likes_counter);
                        }
                    }

                }
            });


        }catch (Exception e)
        {
            Log.e("likesStatus", e.getMessage());
        }
    } // general likes real-time counter
    private void myLike(int i) {
        try {
            if (mUser != null)
            {
                FirebaseFirestore firestore;
                firestore = FirebaseFirestore.getInstance();
                DocumentReference my_likes = firestore.collection("users")
                        .document(mUser.getUid())
                        .collection("my_likes")
                        .document(team_name);
                HashMap<String, Integer> my_likes_map = new HashMap<>();
                my_likes_map.put("my_like_status", i);
                //my_likes_map.put("my_like_status", i); team_logo here
                my_likes.set(my_likes_map).addOnCompleteListener(task ->
                {
                    if (task.isComplete())
                    {
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(KitPreviewActivity.this,
                                    "" + Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(KitPreviewActivity.this,
                                "" + Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                });
            }

        }catch (Exception e)
        {
            Log.e("myLike", e.getMessage());
        }
    } // add user like to user firestore
    private void likes(long i) {
        try {
            if (mUser != null)
            {
                FirebaseFirestore firestore;
                firestore = FirebaseFirestore.getInstance();
                DocumentReference likes = firestore.collection("likes").document(team_name);

                likes.get().addOnCompleteListener(task ->
                {
                    if (task.getResult().exists()) {
                        if (task.isComplete()) {
                            if (task.isSuccessful()) {
                                Long likes_counter = task.getResult().getLong("likes_counter");
                                HashMap<String, Long> likes_map = new HashMap<>();
                                if (i == 0) {
                                    likes_map.put("likes_counter", likes_counter - 1);

                                } else if (i == 1) {
                                    likes_map.put("likes_counter", likes_counter + 1);
                                }
                                likes.set(likes_map, SetOptions.merge()).addOnCompleteListener(task1 ->
                                {
                                    if (task1.isComplete()) {
                                        if (!task1.isSuccessful()) {
                                            Toast.makeText(KitPreviewActivity.this,
                                                    "" + Objects.requireNonNull(task.getException()).getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    } else {
                        HashMap<String, Long> likes_map = new HashMap<>();

                        likes_map.put("likes_counter", (long) 1);
                        likes.set(likes_map, SetOptions.merge()).addOnCompleteListener(task1 ->
                        {
                            if (task1.isComplete()) {
                                if (!task1.isSuccessful()) {
                                    Toast.makeText(KitPreviewActivity.this,
                                            "" + Objects.requireNonNull(task.getException()).getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }

                });
            }

        }catch (Exception e)
        {
            Log.e("likes", e.getMessage());
        }
    } // add user like to general firestore

    private void toCommentsActivity(View view) {
        if (isOnline()){
            if (mUser != null) {
                team_name = bundle.getString("team_name");
                Intent commentsIntent = new Intent(KitPreviewActivity.this,
                        CommentsActivity.class);
                commentsIntent.putExtra("team_name", team_name);
                startActivity(commentsIntent);
            }
            else {
                signInSnackbar(view);
            }
        }else {
            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void shareKit() {
        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        DocumentReference kits = firestore.collection("kits").document(team_name);
        kits.get().addOnCompleteListener(task ->
        {
            String home_kit = String.valueOf(task.getResult().get("home_kit"));
            String link = "https://discord.gg/Ydx6UDYgTb";
            String shareMsg = team_name+"\n\nHey! try on this cool Home kit I found on Kit app!\n\n";
            String shareMsgAction = "\nJoin Dream League Soccer Kits discord\n\n";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "KIT");
            //shareMsg = shareMsg + link + BuildConfig.APPLICATION_ID + "\n\n"; ACTIVATE THIS BEFORE UPLOADING TO GOOGLE PLAY
            shareMsg = shareMsg + home_kit + "\n" + shareMsgAction + link;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        });
    }

    private void saveKitToMyList(View view) {
        try {
            if (isOnline()){
                if (mUser != null) {
                    FirebaseFirestore firestore;
                    firestore = FirebaseFirestore.getInstance();
                    DocumentReference check_my_saves = firestore.collection("users").document(mUser.getUid())
                            .collection("my_saves").document(team_name);
                    check_my_saves.get().addOnCompleteListener(task -> {
                        if (task.isComplete()) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    check_my_saves.delete(); }
                                else {
                                    save(view);
                                }
                            }
                        }
                    });
                }
                else {
                    signInSnackbar(view);
                }
            }else {
                Toast.makeText(this, "please check your internet connection", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e) {
            Log.e("nSave_btn", e.getMessage());
        }
    } // fire save();/ remove from user firestore if exists
    private void save(View view) {
        try {

            if (mUser != null)
            {
                KitsModal modal = setKyes();
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("kits").document(team_name)
                        .get().addOnSuccessListener(documentSnapshot ->
                {
                    //String team = String.valueOf(documentSnapshot.get(modal.getTeam_name()));
                    String team_logo = String.valueOf(documentSnapshot.get(modal.getTeam_logo()));
                    String league = String.valueOf(documentSnapshot.get(modal.getLeague_name()));
                    String league_logo = String.valueOf(documentSnapshot.get(modal.getLeague_name()));

                    DocumentReference my_saves = firestore.collection("users").document(mUser.getUid())
                            .collection("my_saves").document(team_name);
                    HashMap<String, String> saves_map = new HashMap<>();
                    saves_map.put("saved_team_name", team_name);
                    saves_map.put("saved_team_logo", team_logo);
                    saves_map.put("saved_team_league_name", league);
                    saves_map.put("saved_team_league_logo", league_logo);
                    saves_map.put("time", String.valueOf(System.currentTimeMillis()));
                    my_saves.set(saves_map, SetOptions.merge()).addOnCompleteListener(task ->
                    {
                        if (task.isComplete())
                        {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(KitPreviewActivity.this,
                                        "" + Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            //Snackbar.make(view, team_name+" saved", Snackbar.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(KitPreviewActivity.this,
                                    "" + Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
            else
            {
                Snackbar.make(view, "Sign-in to save "+team_name+" kits", Snackbar.LENGTH_LONG).show();
                Toast.makeText(KitPreviewActivity.this,
                        "",
                        Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Log.e("save", e.getMessage());
        }
    }
    private void saveBtnClickEff() {

        nSave_btn.setOnTouchListener((view, motionEvent) ->
        {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            {
                nSave_btn.startAnimation(scale_up);
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
            {
                nSave_btn.startAnimation(scale_down);
            }
            return false;
        });
    } // save scale-up effect
    private void savesStatus() {
        try {

            if (mUser != null)
            {
                FirebaseFirestore firestore;
                firestore = FirebaseFirestore.getInstance();
                DocumentReference check_my_saves = firestore.collection("users").document(mUser.getUid())
                        .collection("my_saves").document(team_name);
                check_my_saves.addSnapshotListener((value, error) ->
                {
                    if (value != null)
                    {
                        if (value.exists())
                        {
                            nSave_btn.setImageResource(R.drawable.ic_bookmark_24px);
                        }
                        else
                        {
                            nSave_btn.setImageResource(R.drawable.ic_bookmark_24px_inactive);
                        }
                    }
                });
            }

        }catch (Exception e)
        {
            Log.e("savesStatus", e.getMessage());
        }
    } // show if user has already saved kit

    private void signInSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "Opps! you'r not signed in", Snackbar.LENGTH_LONG)
                .setAction("SIGN-IN", view1 -> signIn())
                .setActionTextColor(ContextCompat.getColor(KitPreviewActivity.this, R.color.dark_white))
                .setBackgroundTint(ContextCompat.getColor(KitPreviewActivity.this, R.color.dark_blue));
        snackbar.show();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Log.d("firebaseAuthWithGoogle", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {addUserToFirestore(user);}
                    } else {

                        Log.w("firebaseAuthWithGoogle", "signInWithCredential:failure", task.getException());
                        //Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
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
        usersMap.put("date", currentDateTime);
        users_collection.set(usersMap, SetOptions.merge()).addOnCompleteListener(task1 -> {
            if (task1.isComplete()) {
                if (task1.isSuccessful()) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    Toast.makeText(this,
                            "signed-in successfully",
                            Toast.LENGTH_SHORT).show();
                    new PlayersDB(KitPreviewActivity.this).playerCheck();
                }
                else {
                    Toast.makeText(this,
                            ""+ Objects.requireNonNull(task1.getException()).getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //updateUI(account);
        } catch (ApiException e)
        {

            Log.w("handleSignInResult", "signInResult:failed code = " + e.getStatusCode());
            //updateUI(null);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
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

    private boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnected();
        }catch (Exception e) {
            return false;
        }
    }
}