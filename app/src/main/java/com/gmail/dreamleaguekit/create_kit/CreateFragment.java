package com.gmail.dreamleaguekit.create_kit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedadeltito.photoeditorsdk.BrushDrawingView;
import com.ahmedadeltito.photoeditorsdk.OnPhotoEditorSDKListener;
import com.ahmedadeltito.photoeditorsdk.PhotoEditorSDK;
import com.ahmedadeltito.photoeditorsdk.ViewType;
import com.gmail.dreamleaguekit.AdMobClass;
import com.gmail.dreamleaguekit.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import yuku.ambilwarna.AmbilWarnaDialog;


public class CreateFragment extends Fragment {

    private static final String KITS_FOLDER = "My Kits";
    private static final int KIT_HEIGHT = 512;
    private static final int KIT_WIDTH = 512;
    private static final String ORIGINAL_KIT = "kit_";
    private static final String RESIZED_KIT = "resized_kit_";
    private static final int PERMISSION_TO_OPEN_FOLDERS = 200;
    private static final int PERMISSION_TO_IN_FOLDER = 100;
    private static final int PROMO_RC = 300;
    private static final int SHIRT_RC = 400;
    private static final int SHORTS_RC = 500;
    private static final int SOCKS_RC = 600;
    private static final int CUSTOM_RC = 700;

    public PhotoEditorSDK photoEditorSDK;
    private RelativeLayout nParentImageRelativeLayout;
    private ImageView nPhotoEditImageView;
    private View deleteRelativeLayout;
    private BrushDrawingView brushDrawingView;

    private CreateCanvas createCanvas;
    private TabLayout nMain_tabs;
    private CardView nHost_card;
    private CardView nHost_card_strips;
    private ImageButton nSave_btn;

    private static final int FIRST_TAB = 0;
    private static final int SECOND_TAB = 1;
    private static final int THIRD_TAB = 2;
    private static final int FOURTH_TAB = 3;

    private int default_color;
    private int picked_color;

    private AdMobClass adMobClass;

    // checkboxes
    public CheckBox nAll_check_box;
    public CheckBox nShirt_check_box;
    public CheckBox nShort_sleeves_check_box;
    public CheckBox nLong_sleeves_check_box;
    public CheckBox nUnder_sleeve_check_box;
    public CheckBox nShorts_check_box;
    public CheckBox nShirt_number_check_box;
    public CheckBox nShort_number_check_box;
    public CheckBox nRings_check_box;
    public CheckBox nCircle_check_box;
    public CheckBox nBackground_check_box;
    public CheckBox nSocks_check_box;

    public CheckBox nSocks_check_box_c;
    public CheckBox nShorts_check_box_c;
    public CheckBox nLong_sleeves_check_box_c;
    public CheckBox nShort_sleeves_check_box_c;
    public CheckBox nShoulder_check_box_c;
    public CheckBox nAll_check_box_c;

    public CheckBox nAll_check_box_sp;
    public CheckBox nShorts_check_box_sp;
    public CheckBox nShort_sleeve_check_box_sp;
    public CheckBox nLong_sleeve_check_box_sp;
    public CheckBox nShoulder_check_box_sp;
    public CheckBox nSocks_check_box_sp;

    public CardView nStrip_btn_i;
    public CardView nStrip_btn_ii;
    public CardView nStrip_btn_iii;
    public TextView nI_tv;
    public TextView nIi_tv;
    public TextView nIii_tv;
    public ColorStateList inactive;
    public ColorStateList active;
    public ColorStateList cardBackgroundColori;
    public ColorStateList cardBackgroundColorii;
    public ColorStateList cardBackgroundColoriii;
    public ColorStateList textColorsi;
    public ColorStateList textColorsii;
    public ColorStateList textColorsiii;

    private View view;

    public Uri contentURI;

    public CreateFragment() {
        // Required empty public constructor
    }


    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (view != null) {
            init();
        }
        /*if (getArguments() != null) {
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create, container, false);
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        }
        if(view != null) {init();}
        adMobClass.loadRewardedAd();
        mainTabsLintener();
        // adds the base template
        nParentImageRelativeLayout.addView(createCanvas);
        nSave_btn.setOnClickListener(view1 -> {
            if (createCanvas.logos_placements_d.getAlpha() == 0){
                save();
            }else {
                Toast.makeText(getContext(), "Hide guides before saving", Toast.LENGTH_LONG).show();
            }
        });
        photoEditorSDK.setOnPhotoEditorSDKListener(new OnPhotoEditorSDKListener() {
            @Override
            public void onEditTextChangeListener(String text, int colorCode) {
                //Toast.makeText(KitCreatorActivity.this, "txt: "+text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
                //Toast.makeText(KitCreatorActivity.this, "views added: "+numberOfAddedViews, Toast.LENGTH_SHORT).show();
                //nUndo_btn.setVisibility(View.VISIBLE);
            }
            @Override
            public void onRemoveViewListener(int numberOfAddedViews) {
                //Toast.makeText(KitCreatorActivity.this, "txt views deleted: "+numberOfAddedViews, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartViewChangeListener(ViewType viewType) {
                //Toast.makeText(KitCreatorActivity.this, "changing view type: "+viewType, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStopViewChangeListener(ViewType viewType) {
                //Toast.makeText(KitCreatorActivity.this, "stopped changing: "+viewType, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void init() {
        nParentImageRelativeLayout = view.findViewById(R.id.parentImageRelativeLayout);
        nSave_btn = view.findViewById(R.id.save_btn);
        nPhotoEditImageView = view.findViewById(R.id.photoEditImageView);
        deleteRelativeLayout = new View(getContext());
        brushDrawingView = new BrushDrawingView(getContext());
        createCanvas = new CreateCanvas(getContext());
        nMain_tabs = view.findViewById(R.id.main_tabs);
        nHost_card = view.findViewById(R.id.host_card);
        nHost_card.setVisibility(View.GONE);
        nHost_card_strips = view.findViewById(R.id.host_card_strips);
        nHost_card_strips.setVisibility(View.GONE);

        adMobClass = new AdMobClass(new AdRequest.Builder(), getContext());

        default_color = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.dark_blue);
        picked_color = 0;

        photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(getContext())
                //add parent image view
                .parentView(nParentImageRelativeLayout)
                //add the desired image view
                .childView(nPhotoEditImageView)
                //add the deleted view that will appear during the movement of the views
                .deleteView(deleteRelativeLayout)
                // add the brush drawing view that is responsible for drawing on the image view
                .brushDrawingView(brushDrawingView)
                // build photo editor sdk
                .buildPhotoEditorSDK();

        inactive = ColorStateList.valueOf(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black));
        active = ColorStateList.valueOf(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.dark_white));
    }

    private void save() {

        long currentTimeMillis = System.currentTimeMillis();
        String kit_name = ORIGINAL_KIT + currentTimeMillis + ".png";
        saveKit(kit_name);
        kitViewDialog(kit_name);
    }

    private void addImageDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        View add_image_dialog = LayoutInflater.from(getContext())
                .inflate(R.layout.add_image_dialog, null, false);
        builder.setView(add_image_dialog);

        LinearLayout nAdd_promo_logo = add_image_dialog.findViewById(R.id.add_promo_logo);
        LinearLayout nAdd_shirt_logo = add_image_dialog.findViewById(R.id.add_shirt_logo);
        LinearLayout nAdd_shorts_logo = add_image_dialog.findViewById(R.id.add_shorts_logo);
        LinearLayout nAdd_socks_logo = add_image_dialog.findViewById(R.id.add_socks_logo);
        LinearLayout nAdd_image = add_image_dialog.findViewById(R.id.add_image);

        AlertDialog dialog = builder.create();

        nAdd_promo_logo.setOnClickListener(view1 -> {
            choosePhotoFromGallery(PROMO_RC);
            dialog.dismiss();
        });
        nAdd_shirt_logo.setOnClickListener(view1 -> {
            choosePhotoFromGallery(SHIRT_RC);
            dialog.dismiss();
        });
        nAdd_shorts_logo.setOnClickListener(view1 -> {
            choosePhotoFromGallery(SHORTS_RC);
            dialog.dismiss();
        });
        nAdd_socks_logo.setOnClickListener(view1 -> {
            choosePhotoFromGallery(SOCKS_RC);
            dialog.dismiss();
        });
        nAdd_image.setOnClickListener(view1 -> {
            choosePhotoFromGallery(CUSTOM_RC);
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void mainTabsLintener() {
        nMain_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // reveal selected layout

                selectTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /*if (tab.getPosition() != FOURTH_TAB){
                    closeColorHostCard();
                }*/
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                // hide selected layout
                if (tab.getPosition() == FIRST_TAB){
                    selectTab(tab);
                    /*nMain_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {

                            selectTab(tab);
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {
                            if (tab.getPosition() != FOURTH_TAB){
                                closeColorHostCard();
                            }
                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            selectTab(tab);
                        }
                    });*/
                }
                if (tab.getPosition() == SECOND_TAB) {
                    selectTab(tab);
                }
                if (tab.getPosition() == THIRD_TAB) {
                    if (nHost_card.getVisibility() == View.GONE){
                        //colorLayout();
                        //openColorHostCard();
                        selectTab(tab);
                    }else {
                        closeColorHostCard();
                    }
                }
                if (tab.getPosition() == FOURTH_TAB) {
                    if (nHost_card.getVisibility() == View.GONE){
                        //colorLayout();
                        //openColorHostCard();
                        selectTab(tab);
                    }else {
                        closeColorHostCard();
                    }
                }
            }
        });
    }
    private void selectTab(TabLayout.Tab tab) {
        if (tab.getPosition() == FIRST_TAB) {
            createCanvas.hideRevealLogoPlacements(tab);
        }
        else if (tab.getPosition() == SECOND_TAB) {
            addImageDialog();
        }
        else if (tab.getPosition() == THIRD_TAB) {
            if (nHost_card.getVisibility() == View.VISIBLE){
                closeColorHostCard();
            }else {
                if (nHost_card_strips.getVisibility() == View.GONE){
                    stripsLayout();
                }else {
                    closeStripsHostCard();
                }
            }
        }
        else if (tab.getPosition() == FOURTH_TAB) {
            if (nHost_card_strips.getVisibility() == View.VISIBLE){
                closeStripsHostCard();
            }else {

                if (nHost_card.getVisibility() == View.GONE){
                    colorPickerLayout();
                }else {
                    closeColorHostCard();
                }
            }
        }
    }

    public void choosePhotoFromGallery(int requestCode){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, requestCode);
    }
    public void onActivityResult(int requstCode, int resultCode, Intent data){
        super.onActivityResult(requstCode, resultCode, data);
        if(requstCode == Activity.RESULT_CANCELED){
            return;
        }
        if(requstCode == PROMO_RC && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                getPromoLogo(data);
            }
        }
        if(requstCode == SHIRT_RC && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                getShirtLogo(data);
            }
        }
        if(requstCode == SHORTS_RC && resultCode == Activity.RESULT_OK) {
            if(data != null){
                getShortsLogo(data);
            }
        }
        if(requstCode == SOCKS_RC && resultCode == Activity.RESULT_OK){
            if(data != null) {
                getSocksLogo(data);
            }
        }
        if(requstCode == CUSTOM_RC && resultCode == Activity.RESULT_OK){
            if(data != null){
                getCustomImage(data);
            }
        }
        /*if (requstCode == OPEN_MY_KITS_FOLDER){
            // . . .
        }*/
    }

    private void getPromoLogo(Intent data) {
        contentURI = data.getData();
        try {
            createCanvas.promo_logo_b = MediaStore.Images.Media
                    .getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),
                    contentURI).copy(Bitmap.Config.ARGB_8888, true);

            createCanvas.promo_logo_b = Bitmap.createScaledBitmap(createCanvas.promo_logo_b,
                    160,
                    72,
                    true);

            Matrix matrix = new Matrix();
            matrix.postRotate(-90);

            createCanvas.promo_logo_b = Bitmap.createBitmap(createCanvas.promo_logo_b,
                    0,
                    0,
                    160 , 72,
                    matrix, true);

            photoEditorSDK.addImage(createCanvas.promo_logo_b);
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "Failed to add image, please try again", Toast.LENGTH_LONG).show();
        }
    }
    private void getShirtLogo(Intent data) {
        contentURI = data.getData();
        try {
            createCanvas.promo_logo_b = MediaStore.Images.Media
                    .getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),
                            contentURI).copy(Bitmap.Config.ARGB_8888, true);

            createCanvas.promo_logo_b = Bitmap.createScaledBitmap(createCanvas.promo_logo_b,
                    30,
                    30,
                    true);

            Matrix matrix = new Matrix();
            matrix.postRotate(-90);

            createCanvas.promo_logo_b = Bitmap.createBitmap(createCanvas.promo_logo_b,
                    0,
                    0,
                    30 , 30,
                    matrix, true);

            photoEditorSDK.addImage(createCanvas.promo_logo_b);
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "Failed to add image, please try again", Toast.LENGTH_LONG).show();
        }
    }
    private void getShortsLogo(Intent data) {
        contentURI = data.getData();
        try {
            createCanvas.promo_logo_b = MediaStore.Images.Media
                    .getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),
                            contentURI).copy(Bitmap.Config.ARGB_8888, true);

            createCanvas.promo_logo_b = Bitmap.createScaledBitmap(createCanvas.promo_logo_b,
                    44,
                    44,
                    true);

            photoEditorSDK.addImage(createCanvas.promo_logo_b);
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "Failed to add image, please try again", Toast.LENGTH_LONG).show();
        }
    }
    private void getSocksLogo(Intent data) {
        contentURI = data.getData();
        try {
            createCanvas.promo_logo_b = MediaStore.Images.Media
                    .getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),
                            contentURI).copy(Bitmap.Config.ARGB_8888, true);

            createCanvas.promo_logo_b = Bitmap.createScaledBitmap(createCanvas.promo_logo_b,
                    44,
                    44,
                    true);

            Matrix matrix = new Matrix();
            matrix.postRotate(-90);

            createCanvas.promo_logo_b = Bitmap.createBitmap(createCanvas.promo_logo_b,
                    0,
                    0,
                    44 , 44,
                    matrix, true);

            photoEditorSDK.addImage(createCanvas.promo_logo_b);
            photoEditorSDK.addImage(createCanvas.promo_logo_b);
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "Failed to add image, please try again", Toast.LENGTH_LONG).show();
        }
    }
    private void getCustomImage(Intent data) {
        contentURI = data.getData();
        try {
            createCanvas.promo_logo_b = MediaStore.Images.Media
                    .getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),
                            contentURI).copy(Bitmap.Config.ARGB_8888, true);

            photoEditorSDK.addImage(createCanvas.promo_logo_b);
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "Failed to add image, please try again", Toast.LENGTH_LONG).show();
        }
    }

    private void closeStripsHostCard(){
        Animation close_card = AnimationUtils.loadAnimation(getContext(), R.anim.close_host_card_animation);
        nHost_card_strips.startAnimation(close_card);
        nHost_card_strips.setVisibility(View.GONE);
    }
    private void openStripsHostCard(){
        Animation open_card = AnimationUtils.loadAnimation(getContext(), R.anim.open_host_card_animation);
        nHost_card_strips.startAnimation(open_card);
        nHost_card_strips.setVisibility(View.VISIBLE);
    }
    private void stripsLayout() {
        openStripsHostCard();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.strips_layout, null, false);
        // INIT
        nAll_check_box_sp = view.findViewById(R.id.all_check_box_sp);
        nShoulder_check_box_sp = view.findViewById(R.id.shoulder_check_box_sp);
        nShort_sleeve_check_box_sp = view.findViewById(R.id.short_sleeve_check_box_sp);
        nLong_sleeve_check_box_sp = view.findViewById(R.id.long_sleeve_check_box_sp);
        nShorts_check_box_sp = view.findViewById(R.id.shorts_check_box_sp);
        nSocks_check_box_sp = view.findViewById(R.id.socks_check_box_sp);
        Button nApply_btn = view.findViewById(R.id.apply_btn);
        ImageButton nClose_btn = view.findViewById(R.id.close_btn);
        nStrip_btn_i = view.findViewById(R.id.strip_btn_i);
        nStrip_btn_ii = view.findViewById(R.id.strip_btn_ii);
        nStrip_btn_iii = view.findViewById(R.id.strip_btn_iii);
        nI_tv = view.findViewById(R.id.i_tv);
        nIi_tv = view.findViewById(R.id.ii_tv);
        nIii_tv = view.findViewById(R.id.iii_tv);

        // CHECKBOX LISTENERS
        checkboxSelectedListenerStrips(nAll_check_box_sp);
        checkboxSelectedListenerStrips(nShoulder_check_box_sp);
        checkboxSelectedListenerStrips(nShort_sleeve_check_box_sp);
        checkboxSelectedListenerStrips(nLong_sleeve_check_box_sp);
        checkboxSelectedListenerStrips(nShorts_check_box_sp);
        checkboxSelectedListenerStrips(nSocks_check_box_sp);

        cardBackgroundColori = nStrip_btn_i.getCardBackgroundColor();
        cardBackgroundColorii = nStrip_btn_ii.getCardBackgroundColor();
        cardBackgroundColoriii = nStrip_btn_iii.getCardBackgroundColor();

        textColorsi = nI_tv.getTextColors();
        textColorsii = nIi_tv.getTextColors();
        textColorsiii = nIii_tv.getTextColors();

        nStrip_btn_i.setOnClickListener(view1 -> {
            if (cardBackgroundColori == inactive){
                nStrip_btn_i.setCardBackgroundColor(active);
                nI_tv.setTextColor(inactive);
                nIi_tv.setTextColor(active);
                nIii_tv.setTextColor(active);

                nStrip_btn_ii.setCardBackgroundColor(inactive);
                nStrip_btn_iii.setCardBackgroundColor(inactive);
            }if (cardBackgroundColori == active) {
                nStrip_btn_i.setCardBackgroundColor(inactive);
                nI_tv.setTextColor(active);
                nIi_tv.setTextColor(inactive);
                nIii_tv.setTextColor(inactive);
            }
        });
        nStrip_btn_ii.setOnClickListener(view1 -> {
            if (cardBackgroundColorii == inactive){
                nStrip_btn_ii.setCardBackgroundColor(active);
                nIi_tv.setTextColor(inactive);
                nI_tv.setTextColor(active);
                nIii_tv.setTextColor(active);

                nStrip_btn_i.setCardBackgroundColor(inactive);
                nStrip_btn_iii.setCardBackgroundColor(inactive);
            }if (cardBackgroundColorii == active) {
                nStrip_btn_ii.setCardBackgroundColor(inactive);
                nIi_tv.setTextColor(active);
                nI_tv.setTextColor(inactive);
                nIii_tv.setTextColor(inactive);
            }
        });
        nStrip_btn_iii.setOnClickListener(view1 -> {
            if (cardBackgroundColoriii == inactive){
                nStrip_btn_iii.setCardBackgroundColor(active);
                nIii_tv.setTextColor(inactive);
                nI_tv.setTextColor(active);
                nIi_tv.setTextColor(active);

                nStrip_btn_ii.setCardBackgroundColor(inactive);
                nStrip_btn_i.setCardBackgroundColor(inactive);
            }if (cardBackgroundColoriii == active) {
                nStrip_btn_iii.setCardBackgroundColor(inactive);
                nIii_tv.setTextColor(active);
                nI_tv.setTextColor(inactive);
                nIi_tv.setTextColor(inactive);
            }
        });

        // APPLY BUTTON LISTENER
        nApply_btn.setOnClickListener(view1 -> {
            if (isStripsItemChecked()){
                if (nStrip_btn_i.getCardBackgroundColor() == active ||
                        nStrip_btn_ii.getCardBackgroundColor() == active ||
                        nStrip_btn_iii.getCardBackgroundColor() == active) {
                    closeStripsHostCard();
                    applyStrips();
                }else {
                    Toast.makeText(getContext(), "select strip type", Toast.LENGTH_LONG).show();
                }
            }
        });
        nClose_btn.setOnClickListener(view1 -> closeStripsHostCard());
        nHost_card_strips.addView(view);
    }
    private void applyStrips() {
        int max_alpha = 255;
        if (nAll_check_box_sp.isChecked()){
            if (nStrip_btn_i.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "all_1");
            }else if (nStrip_btn_ii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "all_2");
            }else if (nStrip_btn_iii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "all_3");
            }
        }if (nShoulder_check_box_sp.isChecked()){
            if (nStrip_btn_i.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "shoulder_1");
            }else if (nStrip_btn_ii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "shoulder_2");
            }else if (nStrip_btn_iii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "shoulder_3");
            }
        }if (nShort_sleeve_check_box_sp.isChecked()){
            if (nStrip_btn_i.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "short_sleeves_1");
            }else if (nStrip_btn_ii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "short_sleeves_2");
            }else if (nStrip_btn_iii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "short_sleeves_3");
            }
        }if (nLong_sleeve_check_box_sp.isChecked()){
            if (nStrip_btn_i.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "long_sleeves_1");
            }else if (nStrip_btn_ii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "long_sleeves_2");
            }else if (nStrip_btn_iii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "long_sleeves_3");
            }
        }
        if (nShorts_check_box_sp.isChecked()){
            if (nStrip_btn_i.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "shorts_1");
            }else if (nStrip_btn_ii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "shorts_2");
            }else if (nStrip_btn_iii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "shorts_3");
            }
        }
        if (nSocks_check_box_sp.isChecked()){
            if (nStrip_btn_i.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "socks_1");
            }else if (nStrip_btn_ii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "socks_2");
            }else if (nStrip_btn_iii.getCardBackgroundColor() == active){
                createCanvas.setAlph(max_alpha, "socks_3");
            }
        }
    }

    private void closeColorHostCard(){
        Animation close_card = AnimationUtils.loadAnimation(getContext(), R.anim.close_host_card_animation);
        nHost_card.startAnimation(close_card);
        nHost_card.setVisibility(View.GONE);
    }
    private void openColorHostCard(){
        Animation open_card = AnimationUtils.loadAnimation(getContext(), R.anim.open_host_card_animation);
        nHost_card.startAnimation(open_card);
        nHost_card.setVisibility(View.VISIBLE);
    }
    private void colorPickerLayout() {
        openColorHostCard();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.set_color_layout, null, false);
        // INIT
        nAll_check_box = view.findViewById(R.id.all_check_box);
        nShirt_check_box = view.findViewById(R.id.shirt_check_box);
        nShort_sleeves_check_box = view.findViewById(R.id.short_sleeves_check_box);
        nLong_sleeves_check_box = view.findViewById(R.id.long_sleeves_check_box);
        nUnder_sleeve_check_box = view.findViewById(R.id.under_sleeve_check_box);
        nShorts_check_box = view.findViewById(R.id.shorts_check_box);
        nShirt_number_check_box = view.findViewById(R.id.shirt_number_check_box);
        nShort_number_check_box = view.findViewById(R.id.short_number_check_box);
        nRings_check_box = view.findViewById(R.id.rings_check_box);
        nCircle_check_box = view.findViewById(R.id.circle_check_box);
        nBackground_check_box = view.findViewById(R.id.background_check_box);
        nSocks_check_box = view.findViewById(R.id.socks_check_box);

        nAll_check_box_c = view.findViewById(R.id.all_check_box_c);
        nShoulder_check_box_c = view.findViewById(R.id.shoulder_check_box_c);
        nShort_sleeves_check_box_c = view.findViewById(R.id.short_sleeves_check_box_c);
        nLong_sleeves_check_box_c = view.findViewById(R.id.long_sleeves_check_box_c);
        nShorts_check_box_c = view.findViewById(R.id.shorts_check_box_c);
        nSocks_check_box_c = view.findViewById(R.id.socks_check_box_c);

        Button nPaint_btn = view.findViewById(R.id.paint_btn);
        ImageButton nClose_btn_c = view.findViewById(R.id.close_btn_c);

        // CHECKBOX LISTENERS
        checkboxSelectedListener(nAll_check_box);
        checkboxSelectedListener(nShirt_check_box);
        checkboxSelectedListener(nShort_sleeves_check_box);
        checkboxSelectedListener(nLong_sleeves_check_box);
        checkboxSelectedListener(nUnder_sleeve_check_box);
        checkboxSelectedListener(nShorts_check_box);
        checkboxSelectedListener(nShirt_number_check_box);
        checkboxSelectedListener(nShort_number_check_box);
        checkboxSelectedListener(nRings_check_box);
        checkboxSelectedListener(nCircle_check_box);
        checkboxSelectedListener(nBackground_check_box);
        checkboxSelectedListener(nSocks_check_box);

        checkboxSelectedListener(nAll_check_box_c);
        checkboxSelectedListener(nShoulder_check_box_c);
        checkboxSelectedListener(nShort_sleeves_check_box_c);
        checkboxSelectedListener(nLong_sleeves_check_box_c);
        checkboxSelectedListener(nShorts_check_box_c);
        checkboxSelectedListener(nSocks_check_box_c);

        // PAINT BUTTON LISTENER
        nPaint_btn.setOnClickListener(view1 -> {
            if (isColorItemChecked()){
                closeColorHostCard();
                colorPicker();
            }
        });
        nClose_btn_c.setOnClickListener(view1 -> closeColorHostCard());
        nHost_card.addView(view);
    }
    public void colorPicker() {
        if (picked_color != 0){
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getContext(), picked_color,
                    new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {}
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    picked_color = color;
                    if (nAll_check_box.isChecked()){
                        createCanvas.setAllAssetsColor(color);
                    }
                    if (nShirt_check_box.isChecked()){
                        createCanvas.setShirtColor(color);
                    }
                    if (nLong_sleeves_check_box.isChecked()){
                        createCanvas.setLongSleevesColor(color);
                    }
                    if (nShort_sleeves_check_box.isChecked()){
                        createCanvas.setShortSleevesColor(color);
                    }
                    if (nUnder_sleeve_check_box.isChecked()){
                        createCanvas.setUnderSleevesColor(color);
                    }
                    if (nShorts_check_box.isChecked()){
                        createCanvas.setShortsColor(color);
                    }
                    if (nSocks_check_box.isChecked()){
                        createCanvas.setSocksColor(color);
                    }
                    if (nRings_check_box.isChecked()){
                        createCanvas.setRingsColor(color);
                    }
                    if (nCircle_check_box.isChecked()){
                        createCanvas.setCircleColor(color);
                    }
                    if (nShirt_number_check_box.isChecked()){
                        createCanvas.setShirtNumColor(color);
                    }
                    if (nShort_number_check_box.isChecked()){
                        createCanvas.setShortNumColor(color);
                    }
                    if (nBackground_check_box.isChecked()){
                        nPhotoEditImageView.setBackgroundColor(color);
                    }

                    // . . .  . . .

                    if (nAll_check_box_c.isChecked()){
                        createCanvas.setAllStripsColor(color);
                    }
                    if (nShoulder_check_box_c.isChecked()){
                        createCanvas.setShoulderStripsColor(color);
                    }
                    if (nShort_sleeves_check_box_c.isChecked()){
                        createCanvas.setShortSleevesStripsColor(color);
                    }
                    if (nLong_sleeves_check_box_c.isChecked()){
                        createCanvas.setLongSleevesStripsColor(color);
                    }
                    if (nShorts_check_box_c.isChecked()){
                        createCanvas.setShortsStripsColor(color);
                    }
                    if (nSocks_check_box_c.isChecked()){
                        createCanvas.setSocksStripsColor(color);
                    }
                }
            });
            colorPicker.show();
            createCanvas.invalidate();
        }else {
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getContext(),
                    default_color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {}
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    picked_color = color;
                    if (nAll_check_box.isChecked()){
                        createCanvas.setAllAssetsColor(color);
                    }
                    if (nShirt_check_box.isChecked()){
                        createCanvas.setShirtColor(color);
                    }
                    if (nLong_sleeves_check_box.isChecked()){
                        createCanvas.setLongSleevesColor(color);
                    }
                    if (nShort_sleeves_check_box.isChecked()){
                        createCanvas.setShortSleevesColor(color);
                    }
                    if (nUnder_sleeve_check_box.isChecked()){
                        createCanvas.setUnderSleevesColor(color);
                    }
                    if (nShorts_check_box.isChecked()){
                        createCanvas.setShortsColor(color);
                    }
                    if (nSocks_check_box.isChecked()){
                        createCanvas.setSocksColor(color);
                    }
                    if (nRings_check_box.isChecked()){
                        createCanvas.setRingsColor(color);
                    }
                    if (nCircle_check_box.isChecked()){
                        createCanvas.setCircleColor(color);
                    }
                    if (nShirt_number_check_box.isChecked()){
                        createCanvas.setShirtNumColor(color);
                    }
                    if (nShort_number_check_box.isChecked()){
                        createCanvas.setShortNumColor(color);
                    }
                    if (nBackground_check_box.isChecked()){
                        nPhotoEditImageView.setBackgroundColor(color);
                    }

                    // . . .  . . .

                    if (nAll_check_box_c.isChecked()){
                        createCanvas.setAllStripsColor(color);
                    }
                    if (nShoulder_check_box_c.isChecked()){
                        createCanvas.setShoulderStripsColor(color);
                    }
                    if (nShort_sleeves_check_box_c.isChecked()){
                        createCanvas.setShortSleevesStripsColor(color);
                    }
                    if (nLong_sleeves_check_box_c.isChecked()){
                        createCanvas.setLongSleevesStripsColor(color);
                    }
                    if (nShorts_check_box_c.isChecked()){
                        createCanvas.setShortsStripsColor(color);
                    }
                    if (nSocks_check_box_c.isChecked()){
                        createCanvas.setSocksStripsColor(color);
                    }
                }
            });
            colorPicker.show();
            createCanvas.invalidate();
        }
    }

    private boolean isStripsItemChecked() {
        if (
                nAll_check_box_sp.isChecked() ||
                nShoulder_check_box_sp.isChecked() ||
                nShort_sleeve_check_box_sp.isChecked() ||
                nLong_sleeve_check_box_sp.isChecked() ||
                nShorts_check_box_sp.isChecked() ||
                nSocks_check_box_sp.isChecked()
        )
        {
            return true;
        }
        else {
            Toast.makeText(getContext(), "no item selected", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean isColorItemChecked() {
        if (
                nAll_check_box.isChecked() ||
                        nShirt_check_box.isChecked() ||
                        nShort_sleeves_check_box.isChecked() ||
                        nLong_sleeves_check_box.isChecked() ||
                        nUnder_sleeve_check_box.isChecked() ||
                        nShorts_check_box.isChecked() ||
                        nShirt_number_check_box.isChecked() ||
                        nShort_number_check_box.isChecked() ||
                        nRings_check_box.isChecked() ||
                        nCircle_check_box.isChecked() ||
                        nBackground_check_box.isChecked() ||
                        nSocks_check_box.isChecked() ||

                        nAll_check_box_c.isChecked() ||
                        nShoulder_check_box_c.isChecked() ||
                        nShort_sleeves_check_box_c.isChecked() ||
                        nLong_sleeves_check_box_c.isChecked() ||
                        nShorts_check_box_c.isChecked() ||
                        nSocks_check_box_c.isChecked()
        ){
            return true;
        }else {
            Toast.makeText(getContext(), "no item selected", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private void checkboxSelectedListener(CheckBox check_box) {
        check_box.setOnCheckedChangeListener((compoundButton, b) -> {
            assetsCheckboxChecker(check_box, b);
            stripsCheckboxChecker(check_box, b);
        });
    }

    private void assetsCheckboxChecker(CheckBox check_box, boolean b) {
        if (check_box.getId() == R.id.all_check_box) {
            if (b) {
                nShirt_check_box.setChecked(true);
                nShort_sleeves_check_box.setChecked(true);
                nLong_sleeves_check_box.setChecked(true);
                nUnder_sleeve_check_box.setChecked(true);
                nShorts_check_box.setChecked(true);
                nSocks_check_box.setChecked(true);
                nCircle_check_box.setChecked(true);
                nRings_check_box.setChecked(true);
                nShirt_number_check_box.setChecked(true);
                nShort_number_check_box.setChecked(true);
            } /*else {
                *//*nShirt_check_box.setChecked(false);
                nShort_sleeves_check_box.setChecked(false);
                nLong_sleeves_check_box.setChecked(false);
                nUnder_sleeve_check_box.setChecked(false);
                nShorts_check_box.setChecked(false);
                nSocks_check_box.setChecked(false);
                nCircle_check_box.setChecked(false);
                nRings_check_box.setChecked(false);
                nShirt_number_check_box.setChecked(false);
                nShort_number_check_box.setChecked(false);*//*
            }*/
        }
        else if (check_box.getId() == R.id.background_check_box){
            nBackground_check_box.setChecked(b);
        }
        else if (check_box.getId() == R.id.shirt_number_check_box ||
                check_box.getId() == R.id.short_sleeves_check_box ||
                check_box.getId() == R.id.short_number_check_box ||
                check_box.getId() == R.id.long_sleeves_check_box ||
                check_box.getId() == R.id.under_sleeve_check_box ||
                check_box.getId() == R.id.shorts_check_box ||
                check_box.getId() == R.id.socks_check_box ||
                check_box.getId() == R.id.circle_check_box ||
                check_box.getId() == R.id.rings_check_box ||
                check_box.getId() == R.id.shirt_check_box )
        {
            if (b) {
                if (check_box.getId() == R.id.shirt_number_check_box &&
                        check_box.getId() == R.id.short_sleeves_check_box &&
                        check_box.getId() == R.id.short_number_check_box &&
                        check_box.getId() == R.id.long_sleeves_check_box &&
                        check_box.getId() == R.id.under_sleeve_check_box &&
                        check_box.getId() == R.id.shorts_check_box &&
                        check_box.getId() == R.id.socks_check_box &&
                        check_box.getId() == R.id.circle_check_box &&
                        check_box.getId() == R.id.rings_check_box &&
                        check_box.getId() == R.id.shirt_check_box ){

                    nAll_check_box.setChecked(true);
                }

            } else {
                nAll_check_box.setChecked(false);
            }
        }
    }
    private void stripsCheckboxChecker(CheckBox check_box, boolean b) {
        if (check_box.getId() == R.id.all_check_box_c) {
            if (b) {
                nShoulder_check_box_c.setChecked(true);
                nShort_sleeves_check_box_c.setChecked(true);
                nLong_sleeves_check_box_c.setChecked(true);
                nShorts_check_box_c.setChecked(true);
                nSocks_check_box_c.setChecked(true);
            }
        }
        else if (check_box.getId() == R.id.shoulder_check_box_c ||
                check_box.getId() == R.id.short_sleeves_check_box_c ||
                check_box.getId() == R.id.long_sleeves_check_box_c ||
                check_box.getId() == R.id.shorts_check_box_c ||
                check_box.getId() == R.id.socks_check_box_c
                )
        {
            if (b) {
                if (check_box.getId() == R.id.shoulder_check_box_c &&
                    check_box.getId() == R.id.short_sleeves_check_box_c &&
                    check_box.getId() == R.id.long_sleeves_check_box_c &&
                    check_box.getId() == R.id.shorts_check_box_c &&
                    check_box.getId() == R.id.socks_check_box_c
                    ){

                    nAll_check_box_c.setChecked(true);
                }

            } else {
                nAll_check_box_c.setChecked(false);
            }
        }
    }

    private void checkboxSelectedListenerStrips(CheckBox check_box) {
        check_box.setOnCheckedChangeListener((compoundButton, b) -> {

            if (check_box.getId() == R.id.all_check_box_sp) {
                if (b) {
                    nShoulder_check_box_sp.setChecked(true);
                    nShort_sleeve_check_box_sp.setChecked(true);
                    nLong_sleeve_check_box_sp.setChecked(true);
                    nShorts_check_box_sp.setChecked(true);
                    nSocks_check_box_sp.setChecked(true);
                } /*else {
                 *//*nShirt_check_box.setChecked(false);
                    nShort_sleeves_check_box.setChecked(false);
                    nLong_sleeves_check_box.setChecked(false);
                    nUnder_sleeve_check_box.setChecked(false);
                    nShorts_check_box.setChecked(false);
                    nSocks_check_box.setChecked(false);
                    nCircle_check_box.setChecked(false);
                    nRings_check_box.setChecked(false);
                    nShirt_number_check_box.setChecked(false);
                    nShort_number_check_box.setChecked(false);*//*
                }*/
            }
            else if (check_box.getId() == R.id.shoulder_check_box_sp ||
                    check_box.getId() == R.id.short_sleeve_check_box_sp ||
                    check_box.getId() == R.id.long_sleeve_check_box_sp ||
                    check_box.getId() == R.id.shorts_check_box_sp ||
                    check_box.getId() == R.id.socks_check_box_sp)
            {
                if (b) {
                    if (check_box.getId() == R.id.shoulder_check_box_sp &&
                            check_box.getId() == R.id.short_sleeve_check_box_sp &&
                            check_box.getId() == R.id.long_sleeve_check_box_sp &&
                            check_box.getId() == R.id.shorts_check_box_sp &&
                            check_box.getId() == R.id.socks_check_box_sp){

                        nAll_check_box_sp.setChecked(true);
                    }

                } else {
                    nAll_check_box_sp.setChecked(false);
                }
            }
        });
    }

    @NonNull
    private String saveKit(String kit_name) {
        /*long currentTimeMillis = System.currentTimeMillis();
        String kit_name = kit_init+currentTimeMillis+".png";*/
        photoEditorSDK.saveImage(KITS_FOLDER, kit_name);
        return kit_name;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_TO_IN_FOLDER && (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            save();
        }else {
            Toast.makeText(getContext(), "Faild to resize", Toast.LENGTH_LONG).show();
        }
        if (requestCode == PERMISSION_TO_OPEN_FOLDERS && (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            savedKitPopup();
        }else {
            Toast.makeText(getContext(), "Faild to open folder", Toast.LENGTH_LONG).show();
        }
    }

    private void kitViewDialog(String kit_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.saving_kit_dialog, null, false);
        builder.setView(view);

        ImageView nKit_view = view.findViewById(R.id.kit_view);
        RelativeLayout nDialog_kit_view_parent = view.findViewById(R.id.dialog_kit_view_parent);
        TextView nSaving_msg = view.findViewById(R.id.saving_msg);

        ConstraintLayout.LayoutParams resized_kit = new ConstraintLayout.LayoutParams(KIT_WIDTH, KIT_HEIGHT);
        nDialog_kit_view_parent.setLayoutParams(resized_kit);
        photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(getContext())
                //add parent image view
                .parentView(nDialog_kit_view_parent)
                //add the desired image view
                .childView(nKit_view)
                //add the deleted view that will appear during the movement of the views
                .deleteView(deleteRelativeLayout)
                // add the brush drawing view that is responsible for drawing on the image view
                .brushDrawingView(brushDrawingView)
                // build photo editor sdk
                .buildPhotoEditorSDK();

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
        {

            savedKitFinder(kit_name, nKit_view);

            new Handler().postDelayed(() -> {
                long currentTimeMillis = System.currentTimeMillis();
                String kit_name1 = RESIZED_KIT+currentTimeMillis+".png";
                saveKit(kit_name1);
            },2000);
        }
        else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        }
        new Handler().postDelayed(() -> nSaving_msg.setText("Saving..."), 0);
        new Handler().postDelayed(() -> nSaving_msg.setText("Retrieving to\nresize..."), 2900);
        new Handler().postDelayed(() -> nSaving_msg.setText("Resizing..."), 4100);
        new Handler().postDelayed(() -> nSaving_msg.setText("Saving resized\nkit..."), 6200);
        new Handler().postDelayed(() -> {
            //revealPopup();
        }, 8000);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        new Handler().postDelayed(() -> {
            dialog.setCanceledOnTouchOutside(true);
            dialog.dismiss();
            //Toast.makeText(getContext(), "Kit saved", Toast.LENGTH_LONG).show();
            //rewardedAd();
            adMobClass.showRewardedAd();

        }, 9000);
    }

    private void savedKitFinder(String kit_name, ImageView nKit_view) {
        File imgFile = new  File("/storage/emulated/0/Pictures/" + KITS_FOLDER + "/" +kit_name);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            nKit_view.setImageBitmap(myBitmap);
        }
    }

    private void savedKitPopup() {
        // folder path
        //File kit_folder = new  File("/storage/emulated/0/Pictures/" + KITS_FOLDER);


        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            String kit_folder_path = Environment.getExternalStorageDirectory()+"/Pictures/"+KITS_FOLDER+"/";
         /*   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath()
                    + File.separator + KITS_FOLDER + File.separator), "/");

            startActivityForResult(intent, OPEN_MY_KITS_FOLDER);
            Toast.makeText(this, "opening folder", Toast.LENGTH_SHORT).show();*/
            Uri uri = Uri.parse(kit_folder_path);
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(uri, "*/*");
            startActivity(intent);

        }
        else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_TO_OPEN_FOLDERS);
            Toast.makeText(getContext(), "failed to open folder", Toast.LENGTH_SHORT).show();
        }
    }
}