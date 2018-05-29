package com.sacombank.consumers.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.stb.api.bo.consumer.L2.CategoryObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by DUY on 9/2/2017.
 */

public class ImageUtil {

    public static void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }

    public static Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream = context.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void downloadImageAndSave(Context context, String imageUrl) {
        Bitmap theBitmap = null;
        try {
            theBitmap = Glide.
                    with(context).
                    load(imageUrl).asBitmap().
                    into(-1, -1).
                    get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        saveToInternalStorage(theBitmap, context, "your preferred image name");
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, Context context, String name){


        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir

        String name_="foldername"; //Folder name in device android/data/
        File directory = cw.getDir(name_, Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("absolutepath ", directory.getAbsolutePath());
        return directory.getAbsolutePath();
    }

    /** Method to retrieve image from your device **/

    public static Bitmap loadImageFromStorage(String path, String name)
    {
        Bitmap b;
        String name_="foldername";
        try {
            File f=new File(path, name_);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        private Context context;

        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        public DownloadImage(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
            saveImage(this.context, result, "my_image.png");
        }
    }

    public static String getFullPathByImageName(Context context, String imageName) {
        File file = context.getFileStreamPath(imageName);
        return file.getAbsolutePath();
    }

    public static boolean checkImageExist(Context context, String fileName) {
        File file = context.getFileStreamPath(fileName);
        return file.exists();
    }

    public static boolean deleteFile(Context context, String fileName) {
        File file = context.getFileStreamPath(fileName);
        return file.delete();
    }

    public static int[] getImageLocal(List<CategoryObject> list) {
        int[] resourceId = new int[]{};
        for (CategoryObject object : list) {
            if (object.ActiveIconID == 0) {

            }
        }

        return resourceId;
    }
}
