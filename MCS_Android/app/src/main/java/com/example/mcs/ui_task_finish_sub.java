package com.example.mcs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ui_task_finish_sub extends AppCompatActivity {
    ImageView imageView;
    Button button1, button2, button3;
    TextView textView;
    private int imgIndex = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_finish_sub);

        imageView = (ImageView) findViewById(R.id.imageView_task_finish_sub);
        textView = (TextView) findViewById(R.id.textView1_task_finish_sub);

        final int[] imageItems = new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7};
        imageView.setImageResource(imageItems[imgIndex]);
        button1 = (Button) findViewById(R.id.button1_task_finish_sub);
        button3 = (Button) findViewById(R.id.button3_task_finish_sub);
        button2 = (Button) findViewById(R.id.button2_task_finish_sub);
        button1.setEnabled(false);
        button3.setEnabled(true);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("image id", imgIndex);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgIndex = imgIndex - 1;
                if (imgIndex == 0) {
                    button1.setEnabled(false);
                } else {
                    button1.setEnabled(true);
                }
                if (imgIndex >= imageItems.length - 1) {
                    button3.setEnabled(false);
                } else {
                    button3.setEnabled(true);
                }
                imageView.setImageResource(imageItems[imgIndex]);
            }
        });

        button3 = (Button) findViewById(R.id.button3_task_finish_sub);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgIndex = imgIndex + 1;
                if (imgIndex == 0) {
                    button1.setEnabled(false);
                } else {
                    button1.setEnabled(true);
                }
                if (imgIndex >= imageItems.length - 1) {
                    button3.setEnabled(false);
                } else {
                    button3.setEnabled(true);
                }
                imageView.setImageResource(imageItems[imgIndex]);
            }
        });

    }
}