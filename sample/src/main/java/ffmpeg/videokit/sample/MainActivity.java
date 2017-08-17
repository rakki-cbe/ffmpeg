package ffmpeg.videokit.sample;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import processing.ffmpeg.videokit.CommentBuilder;
import processing.ffmpeg.videokit.VideoCompressionResult;
import processing.ffmpeg.videokit.VideoHelper;
import processing.ffmpeg.videokit.VideoKit;
import video_processing.ffmpeg.testing.R;
/**
 * Created by Ilja Kosynkin on 07.07.2016.
 * Copyright by inFullMobile
 */
public class MainActivity extends AppCompatActivity implements VideoListAdapter.Callback,VideoCompressionResult {
    private static final int SPAN_COUNT = 3;
    private static final int REQUEST_CODE = 1337;

    private VideoKit videoKit = new VideoKit();

    private ProgressDialog progressDialog;
    private View rootView;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rootView = findViewById(android.R.id.content);
        model = new Model(this);

        Log.d("ARCHITECTURE", System.getProperty("os.arch"));

        setupDialog();
        setupListIfWritePermissionGranted();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            setupList();
        }
    }

    private void setupDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.processing_message));
        progressDialog.setCancelable(false);
    }

    private void setupListIfWritePermissionGranted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        } else {
            setupList();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void setupList() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);

        final VideoListAdapter adapter = new VideoListAdapter();
        adapter.setCallback(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));

        adapter.setData(model.getVideos());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    @Override
    public void onMediaFileSelected(String path) {
        progressDialog.show();

        CommentBuilder commentBuilder=new CommentBuilder(path,"/storage/emulated/0/aaaa.mp4");
        commentBuilder.setSize(VideoHelper.getInstance().getExpectedResolution(path, CommentBuilder.Default_Width, CommentBuilder.Default_Width));
        videoKit.process(commentBuilder,this);
    }


    @Override
    public void onSucess(String outPath) {
        progressDialog.hide();

    }

    @Override
    public void onFailure(String outPath, int Code) {
        progressDialog.hide();
        Toast.makeText(this, "failed "+Code, Toast.LENGTH_LONG).show();
    }
}
