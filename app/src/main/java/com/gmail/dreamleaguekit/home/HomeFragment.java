package com.gmail.dreamleaguekit.home;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.gmail.dreamleaguekit.R;
import com.gmail.dreamleaguekit.modal.KitsModal;
import com.gmail.dreamleaguekit.posts.KitPreviewActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private FirestoreRecyclerOptions<KitsModal> options;
    private FirestoreRecyclerAdapter<KitsModal, HomeViewHolder> adapter;

    private SearchView nSearch_et;
    private TextView nReport_tv;
    private LinearLayout nReport_banner;
    private RecyclerView nHome_rv;

    private FirebaseFirestore firestore;

    private boolean isFinishedAnimation = true;

    private int last_position;
    private SharedPreferences getPref;
    private final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
    private int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        nSearch_et = view.findViewById(R.id.search_et);
        nReport_tv = view.findViewById(R.id.report_tv);
        nReport_banner = view.findViewById(R.id.report_banner);
        nHome_rv = view.findViewById(R.id.home_rv);

        nReport_tv.setOnClickListener(view1 -> reportDialog());

        homeRecyclerView();

        searchBar();

        return view;
    }

    private void reportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.team_no_exist_report_dialog, null, false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        TextInputLayout nLeague_tv_layout = view.findViewById(R.id.league_tv_layout);
        TextInputEditText nLeague_tv = view.findViewById(R.id.league_tv);
        TextInputLayout nTeam_tv_layout = view.findViewById(R.id.team_tv_layout);
        TextInputEditText nTeam_tv = view.findViewById(R.id.team_tv);
        Button nReport_btn = view.findViewById(R.id.report_btn);
        Button nCancel = view.findViewById(R.id.cancel_btn);
        TextView nJoin_discord_btn = view.findViewById(R.id.join_discord_btn);

        String league = String.valueOf(nLeague_tv.getText()).trim();
        String team = String.valueOf(nTeam_tv.getText()).trim();

        nReport_btn.setOnClickListener(view1 -> {
            if (!TextUtils.isEmpty(nLeague_tv.getText()) && !TextUtils.isEmpty(nTeam_tv.getText())){
                firestore = FirebaseFirestore.getInstance();
                CollectionReference reports = firestore.collection("reports");
                HashMap<String, String> report_map = new HashMap<>();
                report_map.put("team_name", team);
                report_map.put("league_name", league);
                reports.document().set(report_map).addOnCompleteListener(task -> {
                    if (task.isComplete()){
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Thanks for reporting", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getContext(), ""+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else {
                if (TextUtils.isEmpty(nLeague_tv.getText())){
                    Toast.makeText(getContext(), "Please enter league name", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(nTeam_tv.getText())){
                    Toast.makeText(getContext(), "Please enter team name", Toast.LENGTH_LONG).show();
                }
            }
        });
        nCancel.setOnClickListener(view1 -> dialog.dismiss());
        nJoin_discord_btn.setOnClickListener(view1 -> {
            dialog.dismiss();
            joinDiscord();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void joinDiscord() {
        String discord_url = "https://discord.gg/NFv2rqjr5q";
        Uri uri = Uri.parse(discord_url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void searchBar() {
        nSearch_et.setOnSearchClickListener(view1 -> TransitionManager
                .beginDelayedTransition(nSearch_et, new AutoTransition()));

        nSearch_et.setOnCloseListener(() ->
        {
            TransitionManager.beginDelayedTransition(nSearch_et, new AutoTransition());
            return false;
        });
        nSearch_et.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String input) {
                // when submitting
                searchUserInput(firestore, input.trim().toLowerCase());
                return false;
            }
            @Override
            public boolean onQueryTextChange(String input)
            {
                // when typing
                searchUserInput(firestore, input.trim().toLowerCase());
                return false;
            }
        });
    }

    private void homeRecyclerView() {
        firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("kits");

        options = new FirestoreRecyclerOptions.Builder<KitsModal>()
                .setQuery(query.orderBy("date", Query.Direction.DESCENDING), KitsModal.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<KitsModal, HomeViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull KitsModal model)
            {
                holder.nItem_card.setOnClickListener(view ->
                {
                    Intent viewIntent = new Intent(getContext(), KitPreviewActivity.class);
                    viewIntent.putExtra("team_name", model.getTeam_name());
                    startActivity(viewIntent);
                });
                Picasso.get()
                        .load(model.getTeam_logo())
                        .placeholder(R.drawable.ic_branded_img_placeholder)
                        .into(holder.nItem_image);

                holder.nTeam_name_home.setText(capitalizeInitials(model.getTeam_name()));
            }

            @NonNull
            @Override
            public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_item, parent, false);
                return new HomeViewHolder(view);
            }
        };
        //((SimpleItemAnimator) nHome_rv.getItemAnimator()).setSupportsChangeAnimations(false);
        //((DefaultItemAnimator) nHome_rv.getItemAnimator()).setSupportsChangeAnimations(false);
        //nHome_rv.getItemAnimator().setChangeDuration(0);
        nHome_rv.setHasFixedSize(true);
        nHome_rv.setLayoutManager(layoutManager);
        nHome_rv.setAdapter(adapter);


        nHome_rv.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            if (i3 < 0){
                if (isOnline()){
                    showReportBanner();
                }
            }
            else if (i3 > 0){
                hideReportBanner();
            }
        });
    }



    private void searchUserInput(FirebaseFirestore firestore, String input) {
        Query query;
        if (!TextUtils.isEmpty(input)) {
            query = firestore.collection("kits")
            //.orderBy("date")
            //.whereEqualTo("team_name", input)
            // .whereEqualTo("league_name", input)
            //.whereGreaterThanOrEqualTo("league_name", input)
            .whereGreaterThanOrEqualTo("team_name", input)
            //.whereLessThanOrEqualTo("team_name", input)
            //.startAt(input.toLowerCase())
            //.endAt(input.toLowerCase()+"\uf8ff")
            //.whereEqualTo("team_name", input)
            ;
        }
        else
        {
            query = firestore.collection("kits");
        }
        options = new FirestoreRecyclerOptions.Builder<KitsModal>()
                .setQuery(query, KitsModal.class)
                .build();

        adapter.updateOptions(options);
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private final CardView nItem_card;
        private final ImageView nItem_image;
        private final TextView nTeam_name_home;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            nItem_card = itemView.findViewById(R.id.item_card_home);
            nTeam_name_home = itemView.findViewById(R.id.team_name_home);
            StateListAnimator stateListAnimator = AnimatorInflater
                    .loadStateListAnimator(itemView.getContext(),R.animator.card_click_effect);
            nItem_card.setStateListAnimator(stateListAnimator);
            nItem_image = itemView.findViewById(R.id.item_image_home);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private String capitalizeInitials(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find())
        {
            capMatcher.appendReplacement(capBuffer,
                    Objects.requireNonNull(capMatcher.group(1)).toUpperCase()
                            + Objects.requireNonNull(capMatcher.group(2)).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }
    private void showReportBanner() {
        if (nReport_banner == null || nReport_banner.getVisibility() == View.VISIBLE){
            return;
        }
        Animation animation_up = AnimationUtils.loadAnimation(getContext(), R.anim.report_banner_up);
        animation_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                nReport_banner.setVisibility(View.VISIBLE);
                isFinishedAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //view_anim.setVisibility(View.GONE);
                isFinishedAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (isFinishedAnimation){
            nReport_banner.startAnimation(animation_up);
        }
    }

    private void hideReportBanner() {
        if (nReport_banner == null || nReport_banner.getVisibility() == View.GONE){
            return;
        }
        Animation animation_down = AnimationUtils.loadAnimation(nReport_banner.getContext(), R.anim.report_banner_down);
        animation_down.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                nReport_banner.setVisibility(View.VISIBLE);
                isFinishedAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nReport_banner.setVisibility(View.GONE);
                isFinishedAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (isFinishedAnimation){
            nReport_banner.startAnimation(animation_down);
        }
    }

    private boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnected();
        }catch (Exception e) {
            return false;
        }
    }
}