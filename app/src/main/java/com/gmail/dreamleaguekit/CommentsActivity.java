package com.gmail.dreamleaguekit;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.gmail.dreamleaguekit.modal.KitsModal;
import com.gmail.dreamleaguekit.modal.UsesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsActivity extends AppCompatActivity {

    // Previous activity info
    private String team_name;

    // Components
    private ImageView nToolbar_iv;
    private RecyclerView nComments_rv;
    private EditText nComment_et;
    private ProgressBar nProgressBar;

    // Firestore
    private FirebaseFirestore firestore;
    private FirestoreRecyclerOptions<UsesModel> options;
    private FirestoreRecyclerAdapter<UsesModel, CommentsViewHolder> adapter;

    // Current user info
    private String my_name;
    private String my_photo;
    private String email;
    private final static String isAdmin = "ft.designer91@gmail.com";
    //private final static String isAdmin = "supplements1991@gmail.com";

    // Date
    private String currentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        // Team name
        Bundle bundle = getIntent().getExtras();
        team_name = bundle.getString("team_name");
        showAppbar();

        // Components
        nComments_rv = findViewById(R.id.comments_rv);
        nComment_et = findViewById(R.id.comment_et);
        TextView nPost_btn = findViewById(R.id.post_btn);
        nProgressBar = findViewById(R.id.progressBar);

        // Current user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            my_name = mUser.getDisplayName();
            my_photo = String.valueOf(mUser.getPhotoUrl());
            email = mUser.getEmail();
        }
        // Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        currentDateTime = simpleDateFormat.format(date);

        nComments_rv.setOnTouchListener((view, motionEvent) ->
        {
            hideKeyboard();
            return false;
        });

        nPost_btn.setOnClickListener(view -> postComment());
        showComments();
    }

    private void showAppbar()
    {
        nToolbar_iv = findViewById(R.id.toolbar_iv);
        getTeamLogo();
        Toolbar toolbar = findViewById(R.id.comments_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void getTeamLogo()
    {
        KitsModal modal = setKyes();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("kits").document(team_name)
                .get().addOnSuccessListener(documentSnapshot ->
        {
            String logo = String.valueOf(documentSnapshot.get(modal.getTeam_logo()));
            Picasso.get().load(logo).placeholder(R.drawable.ic_branded_icon).into(nToolbar_iv);

        });
    }

    private void showComments()
    {
        firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("comments").document(team_name)
                .collection(team_name + " comments");

        options = new FirestoreRecyclerOptions.Builder<UsesModel>()
                .setQuery(query.orderBy("date", Query.Direction.DESCENDING), UsesModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<UsesModel, CommentsViewHolder>(options)
        {
            @NonNull
            @Override
            public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.comments_item, parent, false);
                return new CommentsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CommentsViewHolder holder, int position, @NonNull UsesModel model)
            {
                if (email.equals(isAdmin))
                {
                    holder.nComment_username.setTextColor(ContextCompat
                            .getColor(CommentsActivity.this,
                                    R.color.design_default_color_error));
                    holder.nComment_username.setText("Admin");
                    Picasso.get()
                            .load(model.getUsername())
                            .placeholder(R.drawable.ic_branded_profile_tab_icon)
                            .into(holder.nComment_user_photo);
                }
                else
                {
                    Picasso.get()
                            .load(model.getUser_photo())
                            .placeholder(R.drawable.ic_branded_profile_tab_icon)
                            .into(holder.nComment_user_photo);
                    holder.nComment_username.setText(model.getUsername());
                }
                holder.nUser_comment.setText(model.getUser_comment());
                holder.nComment_user_photo.setOnClickListener(view ->
                {
                    //deleteMyComment(position);
                });
            }
            private void deleteMyComment(int position)
            {
                getSnapshots().getSnapshot(position).getReference().get().addOnCompleteListener(task ->
                {
                    String username = String.valueOf(task.getResult().get("username"));
                    Toast.makeText(CommentsActivity.this, "clicked: "+username, Toast.LENGTH_SHORT).show();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    String displayName = null;
                    if (user != null) {
                        displayName = user.getDisplayName();
                    }
                    if (username.equals(displayName))
                    {
                        task.getResult().getReference().delete().addOnCompleteListener(task1 ->
                                Toast.makeText(CommentsActivity.this,
                                        "deleted: "+username,
                                        Toast.LENGTH_SHORT).show());
                    }
                    else
                    {
                        Toast.makeText(CommentsActivity.this,
                                "not yours: "+displayName,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        nComments_rv.setHasFixedSize(true);
        nComments_rv.setLayoutManager(layoutManager);
        nComments_rv.setAdapter(adapter);
    }

    private void postComment()
    {
        try {
            String comment = String.valueOf(nComment_et.getText()).trim();
            nComment_et.setHeight(nComment_et.getHeight());
            hideKeyboard();
            firestore = FirebaseFirestore.getInstance();
            CollectionReference comments = firestore.collection("comments")
                    .document(team_name).collection(team_name+" comments");

            HashMap<String, String> commentMap = new HashMap<>();
            if (!TextUtils.isEmpty(comment))
            {

                commentMap.put("username", my_name);
                commentMap.put("user_comment", comment);
                commentMap.put("user_photo", my_photo);
                commentMap.put("team_name", team_name);
                commentMap.put("date", System.currentTimeMillis()+"&&"+currentDateTime);
                comments.add(commentMap)
                        .addOnCompleteListener(task ->
                        {
                            if (task.isComplete())
                            {
                                nProgressBar.setVisibility(View.GONE);
                                nComment_et.getText().clear();
                                if (task.isSuccessful())
                                {
                                    nComments_rv.scrollToPosition(0);
                                }
                                else
                                {
                                    Toast.makeText(CommentsActivity.this,
                                            ""+ Objects.requireNonNull(task.getException()).getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }catch (Exception e)
        {
            Log.e("postComment", e.getMessage());
        }
    }

    private void hideKeyboard()
    {
        UIUtil.hideKeyboard(CommentsActivity.this);
    }

    private KitsModal setKyes()
    {
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
        return modal;
    }

    private static class CommentsViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView nComment_user_photo;
        private final TextView nComment_username;
        private final TextView nUser_comment;

        public CommentsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nComment_user_photo = itemView.findViewById(R.id.comment_user_photo);
            nComment_username = itemView.findViewById(R.id.comment_username);
            nUser_comment = itemView.findViewById(R.id.user_comment);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}