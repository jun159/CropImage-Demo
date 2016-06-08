package com.baojun.cropimage.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baojun.cropimage.R;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "data";
    private static final String RETURN_KEY = "return-data";
    private static final String CROP = "crop";
    private static final String ISCROP = "true";
    private static final String ASPECT_X = "aspectX";
    private static final String ASPECT_Y = "aspectY";
    public static final int CAMERA_REQUEST = 1;

    private ImageView imageView;
    private Button cropButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        cropImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Bundle bundle = data.getExtras();
                Bitmap image = bundle.getParcelable(KEY);
                imageView.setImageBitmap(image);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.image);
        cropButton = (Button) findViewById(R.id.cropButton);
        imageView.setImageResource(R.drawable.cropimage);
    }

    private void cropImage() {
        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageDownload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imageDownload.putExtra(CROP, ISCROP);
                // Ensure cropping is square
                imageDownload.putExtra(ASPECT_X, 1);
                imageDownload.putExtra(ASPECT_Y, 1);
                imageDownload.putExtra(RETURN_KEY, true);
                startActivityForResult(imageDownload, CAMERA_REQUEST);
            }
        });
    }
}
