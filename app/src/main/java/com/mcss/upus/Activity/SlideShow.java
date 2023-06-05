package com.mcss.upus.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcss.upus.Core.API;
import com.mcss.upus.Model.SlidePicture;
import com.mcss.upus.R;
import com.mcss.upus.Util.CommonActivityStyle;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlideShow extends AppCompatActivity {


    private List<SlidePicture> retrievedList;
    private final String TAG = "slideshow";
    private final String BASE_URL = "https://63a2c969ba35b96522fe9f81.mockapi.io/";
    ArrayList<SlideModel> slideModelArrayList;
    ArrayList<SlidePicture> slidePictures;
    Retrofit retrofit;

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CommonActivityStyle.set(this);
        ImageSlider imageSlider = findViewById(R.id.imageSlider);

        slideModelArrayList = new ArrayList<>();
        //        slideModelArrayList.add(new SlideModel(R.drawable.image_1, ScaleTypes.FIT));
        //        slideModelArrayList.add(new SlideModel(R.drawable.image_2, ScaleTypes.FIT));
        //        slideModelArrayList.add(new SlideModel(R.drawable.image_3, ScaleTypes.FIT));
        //        slideModelArrayList.add(new SlideModel(R.drawable.image_4, ScaleTypes.FIT));

        ImageView imageView1 = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        ImageView imageView3 = new ImageView(this);
        ArrayList<ImageView> imageList= new ArrayList<>();
        imageList.add(imageView1);
        imageList.add(imageView2);
        imageList.add(imageView3);



        loadPictures();

        if (retrievedList != null) {
            for (int i = 0; i < retrievedList.size(); i++) {
                Picasso.get().load(retrievedList.get(i).getUrl()).into(imageList.get(i));
            }
        }else {
            Log.d(TAG, "onCreate: else");
        }

        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setOnClickListener(view -> {
            Intent intent = new Intent(SlideShow.this, MainActivity.class);
            this.startActivity(intent);
            finish();
        });


    }

    private Uri getUriFromDrawable(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        // Save the bitmap to a file
        File file = new File(getApplicationContext().getCacheDir(), "image.jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the file's URI
        return FileProvider.getUriForFile(getApplicationContext(), "your_file_provider_authority", file);
    }

    private void loadPictures() {
        API api = retrofit.create(API.class);
        Call<List<SlidePicture>> call = api.getPicturesUrl();
        call.enqueue(new Callback<List<SlidePicture>>() {
            @Override
            public void onResponse(Call<List<SlidePicture>> call, Response<List<SlidePicture>> response) {
                if(response.isSuccessful()){
                    List<SlidePicture> responseList = response.body();
                    retrievedList = new ArrayList<>(responseList);
                    for (SlidePicture slidePicture : slidePictures) {
                        Log.d(TAG, "onResponse: "+ slidePicture.getUrl());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<SlidePicture>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



    @Override
    public void onBackPressed() {
        // Disable the back button while in SlideShow
        // Uncomment the next line if you want to prevent going back to MainActivity using the back button
        // super.onBackPressed();
    }

}