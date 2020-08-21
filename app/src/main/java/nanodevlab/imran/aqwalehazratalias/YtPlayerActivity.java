package nanodevlab.imran.aqwalehazratalias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import nanodevlab.imran.aqwalehazratalias.constans.Contans;

public class YtPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    private static final int RECOVERY_KEY = 1;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubeplayer;
    private String videoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yt_player);


        Bundle bundle =getIntent().getExtras();
        videoId=bundle.getString("VIDEOID");



        youTubePlayerView=findViewById(R.id.ytPlayerView);
        youTubePlayerView.initialize(Contans.API_KEY1+Contans.API_KEY2+Contans.API_KEY3,this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if(!b){
            this.youTubeplayer=youTubePlayer;
            youTubeplayer.cueVideo(videoId);
            youTubeplayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,RECOVERY_KEY).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RECOVERY_KEY){
            getYoutubePlayerProvider().initialize(Contans.API_KEY1+Contans.API_KEY2+Contans.API_KEY3,this);
        }


    }

    private YouTubePlayer.Provider getYoutubePlayerProvider() {

    return youTubePlayerView;
    }
}
