package nanodevlab.imran.aqwalehazratalias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.UUID;

import nanodevlab.imran.aqwalehazratalias.Adapters.VpDispAdapter;
import nanodevlab.imran.aqwalehazratalias.Connectivity.InternetConnection;

public class AqwalDisplay extends AppCompatActivity {


    private ArrayList<String> ImageUrls;
    private int position;
    private ViewPager ImageViewPager;
    private Button saveBtn, shareBtn, rateBtn;
    private VpDispAdapter vpDispAdapter;
    private AdView display_BannerAd;
    private InterstitialAd dis_InterstitialAdShare;
    private static DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqwal_display);

        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        ActivityCompat.requestPermissions(AqwalDisplay.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        MobileAds.initialize(this, "ca-app-pub-8002015381165076~6830605224");

        display_BannerAd = findViewById(R.id.disp_bannerAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        display_BannerAd.loadAd(adRequest);

        dis_InterstitialAdShare = new InterstitialAd(AqwalDisplay.this);
        dis_InterstitialAdShare.setAdUnitId("ca-app-pub-8002015381165076/4544682228");
        AdRequest disp_AdRequest = new AdRequest.Builder().build();
        dis_InterstitialAdShare.loadAd(disp_AdRequest);


        ImageViewPager = findViewById(R.id.DisplayAqwalVPager);
        saveBtn = findViewById(R.id.SvButton);
        shareBtn = findViewById(R.id.SrButton);
        rateBtn = findViewById(R.id.RtButton);

        ImageUrls = new ArrayList<>();
        Intent intent = getIntent();
        ImageUrls = intent.getStringArrayListExtra("Images");
        position = intent.getIntExtra("position", 0);

        vpDispAdapter = new VpDispAdapter(this, ImageUrls);
        ImageViewPager.setAdapter(vpDispAdapter);
        ImageViewPager.setCurrentItem(position, true);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int result = ContextCompat.checkSelfPermission(AqwalDisplay.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (result == PackageManager.PERMISSION_GRANTED) {

                    try {
                        if (InternetConnection.checkConnection(AqwalDisplay.this)) {
                            SaveAqwal saveAqwal = new SaveAqwal();
                            saveAqwal.execute(vpDispAdapter.getItemAt(ImageViewPager.getCurrentItem()));
                            LoadAd();
                        } else
                            Toast.makeText(AqwalDisplay.this, "Please check your Internet connection !", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AqwalDisplay.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AqwalDisplay.this, "First Grant Permission ", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(AqwalDisplay.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }


            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = ContextCompat.checkSelfPermission(AqwalDisplay.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (result == PackageManager.PERMISSION_GRANTED) {

                    try {
                        if (InternetConnection.checkConnection(AqwalDisplay.this)) {
                            LoadAd();
                            int currentItem = ImageViewPager.getCurrentItem();
                            String url = vpDispAdapter.GetCurrentSelectedItem(currentItem);
                            shareContent(url);
                        } else
                            Toast.makeText(AqwalDisplay.this, "Please check your Internet connection !", Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                        Toast.makeText(AqwalDisplay.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AqwalDisplay.this, "First Grant Permission ", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(AqwalDisplay.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });


        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (InternetConnection.checkConnection(AqwalDisplay.this)) {
                        LoadAd();
                        rateApplication();
                    } else
                        Toast.makeText(AqwalDisplay.this, "Please check your Internet connection !", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(AqwalDisplay.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void LoadAd() {


        if (dis_InterstitialAdShare.isLoaded()) {
            dis_InterstitialAdShare.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


    }

    private void shareContent(String url) {

        Picasso.with(AqwalDisplay.this).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                        bitmap, "Title", null);
                Uri imageUri = Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(share, "Select any app"));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Toast.makeText(AqwalDisplay.this, "Error while sharing, Make sure your are connected to internet ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    private void rateApplication() {

        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }


    }


    private class SaveAqwal extends AsyncTask<String,String,String> {


        private SaveAqwal(){

        }

        @Override
        protected String doInBackground(String... strings) {

            Uri path= Uri.parse(strings[0]);
            String fileName = String.valueOf(UUID.randomUUID());

            File dF = new File(Environment.getExternalStorageDirectory(), "HazratAli");
            if (!dF.exists()) Log.d("Message", "doesn't exist, creating: " + dF.mkdirs());
            else if (!dF.isDirectory()) Log.d("Message", "created: " + (dF.delete() & dF.mkdirs()));

            if (path == null) {
                Toast.makeText(AqwalDisplay.this, "No download url!!", Toast.LENGTH_SHORT).show();

            }

            dF = new File(dF, fileName.concat(".jpg"));
            if (dF.exists()) {
                Toast.makeText(AqwalDisplay.this, "File is already downloaded!!", Toast.LENGTH_SHORT).show();
            }

            DownloadManager.Request request   = new DownloadManager.Request(path)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                    .setTitle("Downloaded Aqwal")
                    .setVisibleInDownloadsUi(false)
                    .setDestinationUri(Uri.fromFile(dF));
            request.allowScanningByMediaScanner();

            downloadManager.enqueue(request);

            return null;
        }

    }
}
