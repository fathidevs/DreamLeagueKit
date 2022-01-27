package com.gmail.dreamleaguekit;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class PlayersDB {

    FirebaseDatabase player_db;
    DatabaseReference player_ref;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String uid, name, email;
    HashMap<String, String> player_info_map;

    Context context;

    boolean playerExists = false;

    public PlayersDB(Context context){

        this.context = context;

        this.player_db = FirebaseDatabase.getInstance();
        this.player_ref = player_db.getReference("dls_players");

        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.uid = mUser.getUid();
        this.name = mUser.getDisplayName();
        this.email = mUser.getEmail();
        this.player_info_map = new HashMap<>();
    }

    public void addPlayerToDB() {
        player_info_map.put("player_uid", uid);
        player_info_map.put("player_name", name);
        player_info_map.put("player_email", email);
        player_ref.child(uid).setValue(player_info_map);
    }
    public void playerCheck(){

        player_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(uid)){
                    addPlayerToDB();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
