package nanodevlab.imran.aqwalehazratalias.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import nanodevlab.imran.aqwalehazratalias.Adapters.AqwalAdapter;
import nanodevlab.imran.aqwalehazratalias.AqwalDisplay;
import nanodevlab.imran.aqwalehazratalias.Connectivity.InternetConnection;
import nanodevlab.imran.aqwalehazratalias.Interfaces.recyclerItemClickListener;
import nanodevlab.imran.aqwalehazratalias.MainActivity;
import nanodevlab.imran.aqwalehazratalias.R;

public class Aqwal extends Fragment {


    private Activity mActivity;
    private Context context;
    private AqwalAdapter imageAdapter;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Aqwals");
    private DatabaseReference mDatabaseOffline = FirebaseDatabase.getInstance().getReference("Aqwals");

    private ArrayList<String> mUploads = new ArrayList<>();
    private ProgressDialog progressDialog;
    private RecyclerView aqwalRecyclerview;
    private recyclerItemClickListener.RecyclerViewClickListener clickListener;
    private InterstitialAd Aqwal_InterstitialAdVideos;
    public Aqwal(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aqwal, container, false);

        showProgressDialog();
        Aqwal_InterstitialAdVideos = new InterstitialAd(getContext());
        Aqwal_InterstitialAdVideos.setAdUnitId("ca-app-pub-8002015381165076/2493945671");
        AdRequest displayAdRequest = new AdRequest.Builder().build();
        Aqwal_InterstitialAdVideos.loadAd(displayAdRequest);

        aqwalRecyclerview = view.findViewById(R.id.aqwalRecycler);
        aqwalRecyclerview.setLayoutManager(new GridLayoutManager(context,3));
        aqwalRecyclerview.setHasFixedSize(true);


        if (InternetConnection.checkConnection(getContext())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getAqwalAdapterData();
                }
            }).start();

        } else {

            Snackbar.make(container, "Please check your internet !", Snackbar.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getAqwalAdapterData();
                }
            }).start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(container, "Your are viewing offline synced data !", Snackbar.LENGTH_LONG).show();
                }
            }, 5000);
        }

        clickListener = new recyclerItemClickListener.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mActivity, AqwalDisplay.class);
                intent.putStringArrayListExtra("Images", mUploads);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        };


        return view;
    }


    private void getAqwalAdapterData() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        mDatabaseOffline.keepSynced(true);
                        String url = postSnapshot.child("imageurl").getValue().toString();
                        mUploads.add(url);
                    }
                    Collections.reverse(mUploads);
                    imageAdapter = new AqwalAdapter(mActivity, mUploads, clickListener);
                    aqwalRecyclerview.setAdapter(imageAdapter);
                    imageAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("nanoTAG", "onCancelled: " + databaseError.getMessage());

            }
        });

    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setTitle("Loading Aqwals");
        progressDialog.setMessage("Please wait few seconds");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }


}
