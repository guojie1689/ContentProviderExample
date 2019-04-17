package com.gj.example;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Uri uri = Uri.parse("content://com.gj.cpe.databaseprovider/bookdb");
    private ContentResolver contentResolver = null;

    private ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_view = findViewById(R.id.img_view);

        getSystemService(Context.AUDIO_SERVICE);

        contentResolver = getContentResolver();

        contentResolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                querybook(null);
            }
        });
    }

    public void addBook(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("KEY_NAME", "时间简史");
        contentValues.put("KEY_PRICE", 21.98);

        contentResolver.insert(uri, contentValues);
    }

    public void delbook(View view) {
        contentResolver.delete(uri, "时间简史", null);
    }

    public void querybook(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Cursor cursor = contentResolver.query(uri, null, null, null, null, null);

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                Log.d("GJ", "name:" + name + "   price:" + price);
            }

            cursor.close();
        }
    }

    public void updatebook(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("KEY_NAME", "时间简史");
        contentValues.put("KEY_PRICE", 23.98);

        contentResolver.update(uri, contentValues, null, null);
    }

    public void getFile(View view) {

        try {
            InputStream in = getContentResolver().openInputStream(Uri.parse("content://com.gj.cpe.fileprovider/2"));
            Bitmap image = BitmapFactory.decodeStream(in);

            img_view.setImageBitmap(image);

            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
