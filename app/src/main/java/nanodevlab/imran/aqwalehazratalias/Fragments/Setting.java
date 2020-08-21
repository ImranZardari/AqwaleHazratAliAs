package nanodevlab.imran.aqwalehazratalias.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import nanodevlab.imran.aqwalehazratalias.BuildConfig;
import nanodevlab.imran.aqwalehazratalias.R;


public class Setting extends Fragment {

    private ListView settingList;
    private View view;
    private String[] list = {"About App", "About Developer", "Share Application", "Follow on Facebook", "Subscribe on Youtube", "Rate our App"};
    private int images[] = {R.drawable.ic_adb, R.drawable.ic_admin, R.drawable.ic_share2, R.drawable.facebook, R.drawable.youtube, R.drawable.ic_rate};
    private Activity activity;
    private AdView setting_bannerAd;
    private InterstitialAd dis_InterstitialAdSetting;

    public Setting() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_setting, container, false);
        MobileAds.initialize(activity, "ca-app-pub-8002015381165076~6830605224");

        setting_bannerAd = view.findViewById(R.id.setting_bannerAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        setting_bannerAd.loadAd(adRequest);

        dis_InterstitialAdSetting = new InterstitialAd(getContext());
        dis_InterstitialAdSetting.setAdUnitId("ca-app-pub-8002015381165076/2493945671");
        AdRequest displayAdRequest = new AdRequest.Builder().build();
        dis_InterstitialAdSetting.loadAd(displayAdRequest);


        settingList = view.findViewById(R.id.settingListRecycler);
        SettingAdapter SettingAdapter = new SettingAdapter();
        settingList.setAdapter(SettingAdapter);


        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        InterstitialAd();
                        AboutApp();
                        break;

                    case 1:
                        InterstitialAd();
                        AboutDeveloper();
                        break;

                    case 2:
                        InterstitialAd();
                        shareApplication();
                        break;

                    case 3:
                        InterstitialAd();
                        followFbPage();

                        break;


                    case 4:
                        InterstitialAd();
                        subYTChannel();
                        break;

                    case 5:
                        InterstitialAd();
                        rateApplication();
                        break;

                }
            }
        });


        return view;


    }

    private void AboutApp() {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.about_app);


        dialog.show();

        dialog.findViewById(R.id.ic_closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void AboutDeveloper() {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.about_developer);


        dialog.show();

        dialog.findViewById(R.id.ic_closeBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void shareApplication() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose any one"));
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void followFbPage() {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/2075129169238877"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/MolaRazaTv/")));
        }
    }

    private void subYTChannel() {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCQ5htoPJ83o1avTG0QzJ1Bg?sub_confirmation=1/"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCQ5htoPJ83o1avTG0QzJ1Bg?sub_confirmation=1/")));
        }
    }

    private void rateApplication() {

        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }


    }


    public class SettingAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.list_settingitems, parent, false);
            TextView listinput = v.findViewById(R.id.listItemDes);
            ImageView image = v.findViewById(R.id.listItemImage);
            image.setImageResource(images[position]);
            listinput.setText(list[position]);
            return v;
        }

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    private void InterstitialAd() {


        if (dis_InterstitialAdSetting.isLoaded()) {
            dis_InterstitialAdSetting.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


    }


}
