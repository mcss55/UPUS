package com.mcss.upus.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemChangeListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcss.upus.Core.SlidePicturesRepository;
import com.mcss.upus.Core.ImageDownloader;
import com.mcss.upus.Model.SlidePicture;
import com.mcss.upus.R;
import com.mcss.upus.Util.CommonActivityStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SlideShow extends AppCompatActivity {
    private String TAG = "slideshow";
    private final String BASE_URL = "https://flysistem.flyex.az/api/";
    ArrayList<SlideModel> slideModelArrayList;
    ArrayList<String> fileNames;
    SlidePicturesRepository slidePicturesRepository;

    ArrayList<SlidePicture> slidePictures;
    ImageDownloader imageDownloader;
    Retrofit retrofit;
    ImageSlider imageSlider;

    @SuppressLint({"ResourceType", "ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CommonActivityStyle.set(this);


        imageDownloader = new ImageDownloader(this);

        slidePicturesRepository = retrofit.create(SlidePicturesRepository.class);

        imageSlider = findViewById(R.id.imageSlider);
        slideModelArrayList = new ArrayList<>();

//        fetchData(slideModels -> {
//            slideModelArrayList = slideModels;
//        });

        slideModelArrayList.add(new SlideModel(R.drawable.image_1,ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.image_2,ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.image_3,ScaleTypes.FIT));


        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setTouchListener(view -> {
            Log.d(TAG, "onCreate: clicked");
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        // Disable the back button while in SlideShow
        // Uncomment the next line if you want to prevent going back to MainActivity using the back button
        // super.onBackPressed();
    }

    private void fetchData(final FetchDataCallback callback) {

        Call<List<SlidePicture>> call = slidePicturesRepository.getPictures();

        call.enqueue(new Callback<List<SlidePicture>>() {
            @Override
            public void onResponse(Call<List<SlidePicture>> call, Response<List<SlidePicture>> response) {
                if (response.isSuccessful()) {
                    List<SlidePicture> retrievedList = response.body();
                    processWithData(retrievedList);
                    if (retrievedList != null) {

                        for (SlidePicture slidePicture : retrievedList)
                            slideModelArrayList.add(new SlideModel(slidePicture.getUrl(), ScaleTypes.CENTER_INSIDE));

                        callback.onDataFetched(slideModelArrayList);
                    } else
                        Log.d(TAG, "processWithData: is null");
                } else {
                    // Handle the error
                    Log.d(TAG, "API call failed");
                }
            }

            @Override
            public void onFailure(Call<List<SlidePicture>> call, Throwable t) {
                // Handle the error
                t.printStackTrace();
            }
        });
    }

    private void processWithData(List<SlidePicture> retrievedList) {


    }
}
interface FetchDataCallback {
    void onDataFetched(ArrayList<SlideModel> slideModels);
}
