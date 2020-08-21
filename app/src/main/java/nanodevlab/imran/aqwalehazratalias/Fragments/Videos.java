package nanodevlab.imran.aqwalehazratalias.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import nanodevlab.imran.aqwalehazratalias.Adapters.YtVideosAdapter;
import nanodevlab.imran.aqwalehazratalias.Connectivity.InternetConnection;
import nanodevlab.imran.aqwalehazratalias.Models.Item;
import nanodevlab.imran.aqwalehazratalias.Models.VideoResponse;
import nanodevlab.imran.aqwalehazratalias.R;
import nanodevlab.imran.aqwalehazratalias.YtPlayerActivity;
import nanodevlab.imran.aqwalehazratalias.api.RetrofitClient;
import nanodevlab.imran.aqwalehazratalias.constans.Contans;
import nanodevlab.imran.aqwalehazratalias.service.OnYtItemClickLisetner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Videos extends Fragment implements OnYtItemClickLisetner {

    private View view;
    private Activity activity;
    private RecyclerView VideosRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private YtVideosAdapter ytVideosAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<Item> itemList = new ArrayList<>();
    private boolean isLoaded = false;
    private boolean isLastPage = false;
    private static final int First_Page = 1;
    private int totalPage = 10;
    private int currentPage = First_Page;
    private String nextPageToken;
    private ProgressBar progressBar;
    private String ChannelId = "UCQ5htoPJ83o1avTG0QzJ1Bg";
    private InterstitialAd dis_InterstitialAdVideos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_videos, container, false);

        dis_InterstitialAdVideos = new InterstitialAd(getContext());
        dis_InterstitialAdVideos.setAdUnitId("ca-app-pub-8002015381165076/2493945671");
        AdRequest displayAdRequest = new AdRequest.Builder().build();
        dis_InterstitialAdVideos.loadAd(displayAdRequest);

        progressBar = view.findViewById(R.id.loadMoreProgress);
        progressBar.setVisibility(View.GONE);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_container);
        shimmerFrameLayout.startShimmer();
        VideosRecyclerView = view.findViewById(R.id.ytVideosRecyclerView);
        linearLayoutManager = new LinearLayoutManager(activity);
        VideosRecyclerView.setLayoutManager(linearLayoutManager);
        VideosRecyclerView.setHasFixedSize(true);
        ytVideosAdapter = new YtVideosAdapter(activity, this);
        VideosRecyclerView.setAdapter(ytVideosAdapter);
        VideosRecyclerView.setItemAnimator(new DefaultItemAnimator());


        if (InternetConnection.checkConnection(getContext())) {
            videoApi();
            VideosRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int viewItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoaded && !isLastPage) {

                        if (viewItemCount + firstVisibleItemPosition == totalItemCount && firstVisibleItemPosition >= 0) {
                            isLoaded = true;
                            currentPage += 1;
                            loadMoreItem();
                        }

                    }

                }
            });

        } else
            Snackbar.make(container, "Please check your internet !", Snackbar.LENGTH_LONG).show();


        return view;

    }


    private void InterstitialAd() {


        if (dis_InterstitialAdVideos.isLoaded()) {
            dis_InterstitialAdVideos.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


    }

    private void videoApi() {


        RetrofitClient.getInstance().getService().getVideoList(
                "snippet",
                ChannelId,
                "95",
                nextPageToken,
                "video",
                Contans.API_KEY1 + Contans.API_KEY2 + Contans.API_KEY3).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {

                    itemList = response.body().getItems();
                    ytVideosAdapter.addAll(itemList);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    nextPageToken = response.body().getNextPageToken();


                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });

    }

    private void loadMoreItem() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getService().getVideoList(
                "snippet",
                ChannelId,
                "20",
                nextPageToken,
                "video",
                Contans.API_KEY1 + Contans.API_KEY2 + Contans.API_KEY3).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    isLoaded = false;
                    itemList = response.body().getItems();
                    ytVideosAdapter.addAll(itemList);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    nextPageToken = response.body().getNextPageToken();

                    if (currentPage == totalPage) {
                        isLastPage = true;
                    }


                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void itemClick(View view, int position) {

        InterstitialAd();
        Intent ytIntent = new Intent(activity, YtPlayerActivity.class);
        ytIntent.putExtra("VIDEOID", itemList.get(position).getId().getVideoId());
        startActivity(ytIntent);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }


}
