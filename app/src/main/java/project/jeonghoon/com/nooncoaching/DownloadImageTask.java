package project.jeonghoon.com.nooncoaching;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by jeonghoon on 2016-02-02.
 */
// Too slow to using this Task especially in NullUrlItem
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    public ImageView miv;

    public DownloadImageTask(ImageView iv){
        miv = iv;
    }

    protected Bitmap doInBackground(String... str) {
        String urldisplay = str[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {

        miv.setImageBitmap(result);
    }
}
