package com.gmail.dreamleaguekit.posts;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.dreamleaguekit.AdMobClass;
import com.gmail.dreamleaguekit.R;
import com.google.android.gms.ads.AdRequest;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ViewKitImagesAdapter extends PagerAdapter {

    private final Context context;
    private final String[] imageUrls;
    private final String not_rele;
    private String[] keys;

    private AdMobClass adMobClass;

    View view;

    public ViewKitImagesAdapter(Context context, String[] imageUrls, String nRele) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.not_rele = nRele;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.fragment_viewpager_item, container, false);
        adMobClass = new AdMobClass(new AdRequest.Builder(), context);
        adMobClass.initInterstitialAd();
        adMobClass.loadInterstitialAd();

        TextView nTitle_tv = view.findViewById(R.id.title_tv);
        TextView nNot_released = view.findViewById(R.id.not_released);
        ImageView imageView = view.findViewById(R.id.kit_iv);
        CardView nView_kit_card = view.findViewById(R.id.view_kit_card);

        keys = new String[]{"Logo", "Home kit", "Away kit", "Third kit", "GK Home kit", "GK Away kit", "GK Third kit"};

        String not_r = "not released, as of: "+not_rele.substring(3, 10);
        if (imageUrls[position].equals("null"))
        {
            nNot_released.setVisibility(View.VISIBLE);
            nNot_released.setText(not_r);
        }

        nTitle_tv.setText(keys[position]);

        Picasso.get()
                .load(imageUrls[position])
                .placeholder(R.drawable.ic_branded_img_placeholder)
                .into(imageView);
        nView_kit_card.setOnClickListener(view ->
        {
                if (!imageUrls[position].equals("null"))
                {
                    adMobClass.showInterstitialAd();
                    copyLink(position);
                }
                else
                {
                    adMobClass.loadInterstitialAd();
                    Toast.makeText(context, not_r, Toast.LENGTH_LONG).show();
                }
        });

        container.addView(view);
        return view;
    }

    private void copyLink(int position) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, imageUrls[position]);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, keys[position] + " copied", Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
