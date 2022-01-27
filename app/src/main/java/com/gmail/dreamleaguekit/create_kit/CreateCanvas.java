package com.gmail.dreamleaguekit.create_kit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.gmail.dreamleaguekit.R;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;

public class CreateCanvas extends View {

    //Bitmap all_assets;
    Context context;

    /* ---------- ASSETS ----------*/
    // SHIRT
    Drawable shirt_d;
    Bitmap shirt_b;
    // CIRCLE
    Drawable circle_d;
    Bitmap circle_b;
    // LONG SLEEVES
    public Drawable long_sleeves_d;
    Bitmap long_sleeves_b;
    // SHORT SLEEVES
    Drawable short_sleeves_d;
    Bitmap short_sleeves_b;
    // UNDER SLEEVES
    Drawable under_sleeves_d;
    Bitmap under_sleeves_b;
    // SOCKS
    Drawable socks_d;
    Bitmap socks_b;
    // SHORTS
    Drawable shorts_d;
    Bitmap shorts_b;
    // RINGS
    Drawable rings_d;
    Bitmap rings_b;
    // SHORT NUMBER
    Drawable short_num_d;
    Bitmap short_num_b;
    // SHIRT NUMBER
    Drawable shirt_num_d;
    Bitmap shirt_num_b;


    /* ---------- STRIPS ----------*/

    // SHIRT STRIPS - DRAWABLES
    Drawable shoulder_1strip_d;
    Drawable shoulder_2strips_d;
    Drawable shoulder_3strips_d;
    // SHIRT STRIPS - BITMAPS
    Bitmap shoulder_1strip_b;
    Bitmap shoulder_2strip_b;
    Bitmap shoulder_3strip_b;

    // SOCKS STRIPS - DRAWABLES
    Drawable socks_1strip_d;
    Drawable socks_2strips_d;
    Drawable socks_3strips_d;
    // SOCKS STRIPS - BITMAPS
    Bitmap socks_1strip_b;
    Bitmap socks_2strip_b;
    Bitmap socks_3strip_b;

    // SHORT SLEEVES STRIPS - DRAWABLES
    Drawable short_sleeve_1strip_d;
    Drawable short_sleeve_2strips_d;
    Drawable short_sleeve_3strips_d;
    // SHORT SLEEVES STRIPS - BITMAPS
    Bitmap short_sleeve_1strip_b;
    Bitmap short_sleeve_2strips_b;
    Bitmap short_sleeve_3strips_b;

    // LONG SLEEVES STRIPS - DRAWABLES
    Drawable long_sleeve_1strip_d;
    Drawable long_sleeve_2strips_d;
    Drawable long_sleeve_3strips_d;
    // LONG SLEEVES STRIPS - BITMAPS
    Bitmap long_sleeve_1strip_b;
    Bitmap long_sleeve_2strips_b;
    Bitmap long_sleeve_3strips_b;

    // LONG SLEEVES STRIPS - DRAWABLES
    Drawable shorts_1strip_d;
    Drawable shorts_2strips_d;
    Drawable shorts_3strips_d;
    // LONG SLEEVES STRIPS - BITMAPS
    Bitmap shorts_1strip_b;
    Bitmap shorts_2strips_b;
    Bitmap shorts_3strips_b;

    /* ---------- logos ----------*/
    // PROMO LOGO
    // DRAWABLES
    Drawable promo_logo_d;
    // BITMAPS
    Bitmap promo_logo_b;

    /* ---------- GUIDES ----------*/
    // LOGO
    Drawable logos_placements_d;
    Bitmap logos_placements_b;

    public CreateCanvas(Context context) {
        super(context);
        this.context = context;

        // MAIN DRAWABLES
        // ALL
        //all_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_master_tmp);
        // CIRCLE
        circle_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_circle);
        // LONG SLEEVES
        long_sleeves_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_long_sleeves);
        // RINGS
        rings_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_rings);
        // SHIRT
        shirt_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shirt);
        // SHIRT NUMBER
        shirt_num_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shirt_number_color);
        // SHORT NUMBER
        short_num_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_short_number_color);
        // SHORT SLEEVES
        short_sleeves_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_short_sleeves);
        // SHORTS
        shorts_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shorts);
        // SOCKS
        socks_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_socks);
        // UNDER SLEEVE
        under_sleeves_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_under_sleeves);


        // LOGOS - DRAWABLES
        promo_logo_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_promo_logo);


        // GUIDELINES - DRAWABLES
        // LOGOS
        logos_placements_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_logos_placement);
        //assert logos_placements_d != null;
        if (logos_placements_d != null) {
            logos_placements_d.setAlpha(0);
        }

        // SHOULDER STRIPS - DRAWABLES
        shoulder_1strip_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shirt_1strip);
        assert shoulder_1strip_d != null;
        shoulder_1strip_d.setAlpha(0);
        shoulder_2strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shirt_2strips);
        assert shoulder_2strips_d != null;
        shoulder_2strips_d.setAlpha(0);
        shoulder_3strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shirt_3strips);
        assert shoulder_3strips_d != null;
        shoulder_3strips_d.setAlpha(0);
        // SHORT SLEEVES STRIPS - DRAWABLES
        short_sleeve_1strip_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_short_sleeves_1strip);
        assert short_sleeve_1strip_d != null;
        short_sleeve_1strip_d.setAlpha(0);
        short_sleeve_2strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_short_sleeves_2strips);
        assert short_sleeve_2strips_d != null;
        short_sleeve_2strips_d.setAlpha(0);
        short_sleeve_3strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_short_sleeves_3strips);
        assert short_sleeve_3strips_d != null;
        short_sleeve_3strips_d.setAlpha(0);
        // LONG SLEEVES STRIPS - DRAWABLES
        long_sleeve_1strip_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_long_sleeves_1strip);
        assert long_sleeve_1strip_d != null;
        long_sleeve_1strip_d.setAlpha(0);
        long_sleeve_2strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_long_sleeves_2strips);
        assert long_sleeve_2strips_d != null;
        long_sleeve_2strips_d.setAlpha(0);
        long_sleeve_3strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_long_sleeves_3strips);
        assert long_sleeve_3strips_d != null;
        long_sleeve_3strips_d.setAlpha(0);
        // SHORTS STRIPS - DRAWABLES
        shorts_1strip_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shorts_1strip);
        assert shorts_1strip_d != null;
        shorts_1strip_d.setAlpha(0);
        shorts_2strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shorts_2strips);
        assert shorts_2strips_d != null;
        shorts_2strips_d.setAlpha(0);
        shorts_3strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_shorts_3strips);
        assert shorts_3strips_d != null;
        shorts_3strips_d.setAlpha(0);
        // SOCKS STRIPS - DRAWABLES
        socks_1strip_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_socks_1strip);
        assert socks_1strip_d != null;
        socks_1strip_d.setAlpha(0);
        socks_2strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_socks_2strips);
        assert socks_2strips_d != null;
        socks_2strips_d.setAlpha(0);
        socks_3strips_d = ContextCompat.getDrawable(context, R.drawable.ic_app_temp_socks_3strips);
        assert socks_3strips_d != null;
        socks_3strips_d.setAlpha(0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        processingAsset(canvas);
    }

    private void processingAsset(Canvas canvas) {

        convertedDrawableToBitmap();

        drawAllConvertedAssets(canvas);
    }

    private void convertedDrawableToBitmap() {

        /* ---------- ASSETS ----------*/

        //all_b = drawableToBitmap(all_d);
        circle_b = drawableToBitmap(circle_d);
        long_sleeves_b = drawableToBitmap(long_sleeves_d);
        short_sleeves_b = drawableToBitmap(short_sleeves_d);
        under_sleeves_b = drawableToBitmap(under_sleeves_d);
        shirt_b = drawableToBitmap(shirt_d);
        shorts_b = drawableToBitmap(shorts_d);
        socks_b = drawableToBitmap(socks_d);
        rings_b = drawableToBitmap(rings_d);
        shirt_num_b = drawableToBitmap(shirt_num_d);
        short_num_b = drawableToBitmap(short_num_d);


        /* ---------- STRIPS ----------*/

        // SHIRT SHOULDER STRIPS - CONVERTED TO BITMAP
        shoulder_1strip_b = drawableToBitmap(shoulder_1strip_d);
        shoulder_2strip_b = drawableToBitmap(shoulder_2strips_d);
        shoulder_3strip_b = drawableToBitmap(shoulder_3strips_d);

        // SOCKS STRIPS - CONVERTED TO BITMAP
        socks_1strip_b = drawableToBitmap(socks_1strip_d);
        socks_2strip_b = drawableToBitmap(socks_2strips_d);
        socks_3strip_b = drawableToBitmap(socks_3strips_d);

        // SHORT SLEEVES STRIPS - CONVERTED TO BITMAP
        short_sleeve_1strip_b = drawableToBitmap(short_sleeve_1strip_d);
        short_sleeve_2strips_b = drawableToBitmap(short_sleeve_2strips_d);
        short_sleeve_3strips_b = drawableToBitmap(short_sleeve_3strips_d);

        // LONG SLEEVES STRIPS - CONVERTED TO BITMAP
        long_sleeve_1strip_b = drawableToBitmap(long_sleeve_1strip_d);
        long_sleeve_2strips_b = drawableToBitmap(long_sleeve_2strips_d);
        long_sleeve_3strips_b = drawableToBitmap(long_sleeve_3strips_d);

        // SORTS STRIPS - CONVERTED TO BITMAP
        shorts_1strip_b = drawableToBitmap(shorts_1strip_d);
        shorts_2strips_b = drawableToBitmap(shorts_2strips_d);
        shorts_3strips_b = drawableToBitmap(shorts_3strips_d);

        /* ---------- LOGOS ----------*/
        //promo_logo_b = drawableToBitmap(promo_logo_d);
        /*try {
            if (promo_logo_b != null && new CreateFragment().contentURI != null){
                mask = BitmapFactory.decodeResource(getResources(), R.drawable.ic_app_temp_promo_logo);
                promo_logo_b = Bitmap.createScaledBitmap(promo_logo_b, 100, 100, true);
                promo_logo_b = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),
                        new CreateFragment().contentURI).copy(Bitmap.Config.ARGB_8888, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /* ---------- GUIDLINES ----------*/

        // LOGOS
        logos_placements_b = drawableToBitmap(logos_placements_d);

    }


    private void drawAllConvertedAssets(Canvas canvas) {

        /* ---------- ASSETS ----------*/


        //canvas.drawBitmap(all_b, 0,0, null);
        canvas.drawBitmap(shirt_b, 0,0, null);
        canvas.drawBitmap(long_sleeves_b, 0,0, null);
        canvas.drawBitmap(short_sleeves_b, 0,0, null);
        canvas.drawBitmap(under_sleeves_b, 0,0, null);
        canvas.drawBitmap(shorts_b, 0,0, null);
        canvas.drawBitmap(socks_b, 0,0, null);
        canvas.drawBitmap(circle_b, 0,0, null);
        canvas.drawBitmap(rings_b, 0,0, null);
        canvas.drawBitmap(shirt_num_b, 0,0, null);
        canvas.drawBitmap(short_num_b, 0,0, null);


        /* ---------- LOGOS ----------*/
        //promoLogo(canvas);

        /* ---------- GUIDLINES ----------*/

        // LOGOS
        canvas.drawBitmap(logos_placements_b, 0,0, null);


        /* ---------- STRIPS ----------*/

        // SHIRT
        canvas.drawBitmap(shoulder_1strip_b, 0,0, null);
        canvas.drawBitmap(shoulder_2strip_b, 0,0, null);
        canvas.drawBitmap(shoulder_3strip_b, 0,0, null);

        // SOCKS
        canvas.drawBitmap(socks_1strip_b, 0,0, null);
        canvas.drawBitmap(socks_2strip_b, 0,0, null);
        canvas.drawBitmap(socks_3strip_b, 0,0, null);

        // SHORT SLEEVES
        canvas.drawBitmap(short_sleeve_1strip_b, 0,0, null);
        canvas.drawBitmap(short_sleeve_2strips_b, 0,0, null);
        canvas.drawBitmap(short_sleeve_3strips_b, 0,0, null);

        // LONG SLEEVES
        canvas.drawBitmap(long_sleeve_1strip_b, 0,0, null);
        canvas.drawBitmap(long_sleeve_2strips_b, 0,0, null);
        canvas.drawBitmap(long_sleeve_3strips_b, 0,0, null);

        // SHORTS
        canvas.drawBitmap(shorts_1strip_b, 0,0, null);
        canvas.drawBitmap(shorts_2strips_b, 0,0, null);
        canvas.drawBitmap(shorts_3strips_b, 0,0, null);
    }

    /*public void promoLogo(Canvas canvas) {
        if (promo_logo_b != null){
            canvas.drawBitmap(promo_logo_b, 0,0, null);
        }
    }*/

    public Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        bitmap.setHeight(getWidth());
        bitmap.setWidth(getWidth());
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

       /* Toast.makeText(context,
                "width = "+bitmap.getWidth()
                       +"\nheight = "+bitmap.getHeight(),
                Toast.LENGTH_LONG).show();*/
        return bitmap;
    }

    public void hideRevealLogoPlacements(TabLayout.Tab tab) {

        int max_alpha = 255;
        invalidate();
        if (logos_placements_d.getAlpha() == max_alpha){
            logos_placements_d.setAlpha(0);
            invalidate();
            tab.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_visibility_off_24px));
        }else if (logos_placements_d.getAlpha() == 0){
            logos_placements_d.setAlpha(max_alpha);
            invalidate();
            tab.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_visibility_24px));
        }
    }

    /*public void replaceLogoPromoWithImage(Bitmap bitmap){
        *//*bitmap = Bitmap.createBitmap(promo_logo_d.getIntrinsicWidth(),
                promo_logo_d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        bitmap.setHeight(getHeight());
        bitmap.setWidth(getWidth());*//*
        Bitmap bitmap1 = Bitmap.createBitmap(getWidth(), getHeight()
                ,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);

        invalidate();
    }*/

    public void setAlph(int alpha, String checkBox) {
        if (checkBox.equals("all_1")) {
            shoulder_1strip_d.setAlpha(alpha);
            socks_1strip_d.setAlpha(alpha);
            short_sleeve_1strip_d.setAlpha(alpha);
            long_sleeve_1strip_d.setAlpha(alpha);
            shorts_1strip_d.setAlpha(alpha);

            shoulder_2strips_d.setAlpha(0);
            shoulder_3strips_d.setAlpha(0);
            socks_2strips_d.setAlpha(0);
            socks_3strips_d.setAlpha(0);
            short_sleeve_2strips_d.setAlpha(0);
            short_sleeve_3strips_d.setAlpha(0);
            long_sleeve_2strips_d.setAlpha(0);
            long_sleeve_3strips_d.setAlpha(0);
            shorts_2strips_d.setAlpha(0);
            shorts_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("all_2")) {
            shoulder_2strips_d.setAlpha(alpha);
            socks_2strips_d.setAlpha(alpha);
            short_sleeve_2strips_d.setAlpha(alpha);
            long_sleeve_2strips_d.setAlpha(alpha);
            shorts_2strips_d.setAlpha(alpha);

            shoulder_1strip_d.setAlpha(0);
            shoulder_3strips_d.setAlpha(0);
            socks_1strip_d.setAlpha(0);
            socks_3strips_d.setAlpha(0);
            short_sleeve_1strip_d.setAlpha(0);
            short_sleeve_3strips_d.setAlpha(0);
            long_sleeve_1strip_d.setAlpha(0);
            long_sleeve_3strips_d.setAlpha(0);
            shorts_1strip_d.setAlpha(0);
            shorts_3strips_d.setAlpha(0);
            invalidate();
            }
        if (checkBox.equals("all_3")) {
            shoulder_3strips_d.setAlpha(alpha);
            socks_3strips_d.setAlpha(alpha);
            short_sleeve_3strips_d.setAlpha(alpha);
            long_sleeve_3strips_d.setAlpha(alpha);
            shorts_3strips_d.setAlpha(alpha);

            shoulder_1strip_d.setAlpha(0);
            shoulder_2strips_d.setAlpha(0);
            socks_1strip_d.setAlpha(0);
            socks_2strips_d.setAlpha(0);
            short_sleeve_1strip_d.setAlpha(0);
            short_sleeve_2strips_d.setAlpha(0);
            long_sleeve_1strip_d.setAlpha(0);
            long_sleeve_2strips_d.setAlpha(0);
            shorts_1strip_d.setAlpha(0);
            shorts_2strips_d.setAlpha(0);
            invalidate();
            }
        if (checkBox.equals("shoulder_1")) {
            shoulder_1strip_d.setAlpha(alpha);

            shoulder_2strips_d.setAlpha(0);
            shoulder_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("shoulder_2")) {
            shoulder_2strips_d.setAlpha(alpha);

            shoulder_1strip_d.setAlpha(0);
            shoulder_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("shoulder_3")) {
            shoulder_3strips_d.setAlpha(alpha);

            shoulder_1strip_d.setAlpha(0);
            shoulder_2strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("short_sleeves_1")) {
            short_sleeve_1strip_d.setAlpha(alpha);

            short_sleeve_2strips_d.setAlpha(0);
            short_sleeve_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("short_sleeves_2")) {
            short_sleeve_2strips_d.setAlpha(alpha);

            short_sleeve_1strip_d.setAlpha(0);
            short_sleeve_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("short_sleeves_3")) {
            short_sleeve_3strips_d.setAlpha(alpha);

            short_sleeve_1strip_d.setAlpha(0);
            short_sleeve_2strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("long_sleeves_1")) {
            long_sleeve_1strip_d.setAlpha(alpha);

            long_sleeve_2strips_d.setAlpha(0);
            long_sleeve_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("long_sleeves_2")) {
            long_sleeve_2strips_d.setAlpha(alpha);

            long_sleeve_1strip_d.setAlpha(0);
            long_sleeve_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("long_sleeves_3")) {
            long_sleeve_3strips_d.setAlpha(alpha);

            long_sleeve_1strip_d.setAlpha(0);
            long_sleeve_2strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("shorts_1")) {
            shorts_1strip_d.setAlpha(alpha);

            shorts_2strips_d.setAlpha(0);
            shorts_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("shorts_2")) {
            shorts_2strips_d.setAlpha(alpha);

            shorts_1strip_d.setAlpha(0);
            shorts_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("shorts_3")) {
            shorts_3strips_d.setAlpha(alpha);

            shorts_1strip_d.setAlpha(0);
            shorts_2strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("socks_1")) {
            socks_1strip_d.setAlpha(alpha);

            socks_2strips_d.setAlpha(0);
            socks_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("socks_2")) {
            socks_2strips_d.setAlpha(alpha);

            socks_1strip_d.setAlpha(0);
            socks_3strips_d.setAlpha(0);
            invalidate();
        }
        if (checkBox.equals("socks_3")) {
            socks_3strips_d.setAlpha(alpha);

            socks_1strip_d.setAlpha(0);
            socks_2strips_d.setAlpha(0);
            invalidate();
        }
        }

    public void setAllAssetsColor(int color) {
        //new SetAssetColor(context).setAllColor(color);
        //all_d.setTint(color);
            circle_d.setTint(color);
            long_sleeves_d.setTint(color);
            short_sleeves_d.setTint(color);
            under_sleeves_d.setTint(color);
            shorts_d.setTint(color);
            socks_d.setTint(color);
            rings_d.setTint(color);
            short_num_d.setTint(color);
            shirt_num_d.setTint(color);
            shirt_d.setTint(color);

        invalidate();
    }
    public void setCircleColor(int color) {
        circle_d.setTint(color);
        invalidate();
    }
    public void setLongSleevesColor(int color) {
        long_sleeves_d.setTint(color);
        invalidate();
    }
    public void setShortSleevesColor(int color) {
        short_sleeves_d.setTint(color);
        invalidate();
    }
    public void setUnderSleevesColor(int color) {
        under_sleeves_d.setTint(color);
        invalidate();
    }
    public void setShortsColor(int color) {
        shorts_d.setTint(color);
        invalidate();
    }
    public void setSocksColor(int color) {
        socks_d.setTint(color);
        invalidate();
    }
    public void setRingsColor(int color) {
        rings_d.setTint(color);
        invalidate();
    }
    public void setShortNumColor(int color) {
        short_num_d.setTint(color);
        invalidate();
    }
    public void setShirtNumColor(int color) {
        shirt_num_d.setTint(color);
        invalidate();
    }
    public void setShirtColor(int color) {
        shirt_d.setTint(color);
        invalidate();
    }

    // . . .  . . .

    public void setAllStripsColor(int color) {
        shoulder_1strip_d.setTint(color);
        shoulder_2strips_d.setTint(color);
        shoulder_3strips_d.setTint(color);

        short_sleeve_1strip_d.setTint(color);
        short_sleeve_2strips_d.setTint(color);
        short_sleeve_3strips_d.setTint(color);

        long_sleeve_1strip_d.setTint(color);
        long_sleeve_2strips_d.setTint(color);
        long_sleeve_3strips_d.setTint(color);

        shorts_1strip_d.setTint(color);
        shorts_2strips_d.setTint(color);
        shorts_3strips_d.setTint(color);

        socks_1strip_d.setTint(color);
        socks_2strips_d.setTint(color);
        socks_3strips_d.setTint(color);
        invalidate();
    }
    public void setShoulderStripsColor(int color) {
        shoulder_1strip_d.setTint(color);
        shoulder_2strips_d.setTint(color);
        shoulder_3strips_d.setTint(color);
        invalidate();
    }
    public void setShortSleevesStripsColor(int color) {
        short_sleeve_1strip_d.setTint(color);
        short_sleeve_2strips_d.setTint(color);
        short_sleeve_3strips_d.setTint(color);
        invalidate();
    }
    public void setLongSleevesStripsColor(int color) {
        long_sleeve_1strip_d.setTint(color);
        long_sleeve_2strips_d.setTint(color);
        long_sleeve_3strips_d.setTint(color);
        invalidate();
    }
    public void setShortsStripsColor(int color) {
        shorts_1strip_d.setTint(color);
        shorts_2strips_d.setTint(color);
        shorts_3strips_d.setTint(color);
        invalidate();
    }
    public void setSocksStripsColor(int color) {
        socks_1strip_d.setTint(color);
        socks_2strips_d.setTint(color);
        socks_3strips_d.setTint(color);
        invalidate();
    }
}