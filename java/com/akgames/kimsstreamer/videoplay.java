package com.akgames.kimsstreamer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class videoplay extends AppCompatActivity {
    private VideoView videoView;
    private MediaController mediaController;
//    private SimpleExoPlayerView videoView;
//    //private ProgressBar progressBar;
//    private SimpleExoPlayer simpleExoPlayer;
    private String author, description, icon, strTitle, type;
    private Long views;
    private String vid;
    private boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);
        VideoView videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        author = getIntent().getStringExtra("author");
        description = getIntent().getStringExtra("description");

        try {
            icon = getIntent().getStringExtra("icon");
            strTitle = getIntent().getStringExtra("title");
            type = getIntent().getStringExtra("type");
            views = Long.parseLong(getIntent().getStringExtra("views"));
            check = getIntent().getBooleanExtra("track", true);
            vid = getIntent().getStringExtra("vid");
        }catch (Exception e){

        }

        try{
            //BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            //TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            //simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            //specify the location of media file
            Uri uri= Uri.parse(vid);
            //videoView..setAudioStreamType(AudioManager.STREAM_MUSIC);
            videoView.setVideoURI(uri);

            videoView.setMediaController(mediaController);
            videoView.start();



            //DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            //ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            //MediaSource mediaSource = new ExtractorMediaSource(uri,dataSourceFactory,extractorsFactory, null, null);
            //videoView.setPlayer(simpleExoPlayer);
            //simpleExoPlayer.prepare(mediaSource);
            //simpleExoPlayer.setPlayWhenReady(true);

        }
        catch (Exception e){

        }

    }
    public void onBackPressed() {
        Intent intent = new Intent(videoplay.this,movieChoice.class);
        intent.putExtra("vid", vid);
        intent.putExtra("author", author);
        intent.putExtra("description", description);
        intent.putExtra("icon", icon);
        intent.putExtra("title", strTitle);
        intent.putExtra("views", views.toString());
        intent.putExtra("type", type);
        intent.putExtra("track", true);
        startActivity(intent);
        finish();
    }
}