package com.mcss.upus.Core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ImageDownloader extends AsyncTask<List<String>, Void, Boolean> {

    private Context context;

    public ImageDownloader(Context context) {
        this.context = context;
    }

    @SafeVarargs
    @Override
    protected final Boolean doInBackground(List<String>... params) {
        List<String> imageUrls = params[0];

        try {
            for (String imageUrl : imageUrls) {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = new File(directory, imageUrl.replace("https://flysistem.flyex.az/storage/cargomat/slayt/",""));

                OutputStream output = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                output.flush();
                output.close();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            System.out.println("Images downloaded and saved.");
        } else {
            System.out.println("Failed to download images.");
        }
    }
}
