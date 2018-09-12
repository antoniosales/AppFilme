package com.example.root.filmes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.InputStream;
import java.net.URL;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        final ImageButton btnLink = (ImageButton) findViewById(R.id.BtnLink);
        btnLink.setOnClickListener(this);

        if(getIntent()!=null && getIntent().getParcelableExtra("filme")!=null){
            FilmeBean filme = getIntent().getParcelableExtra("filme");
            tvDescription.setText(Html.fromHtml(filme.toString()));
            ImageView ivImg = (ImageView) findViewById(R.id.ivImg);
            Display display = getWindowManager().getDefaultDisplay();

            new DownloadImageTask(ivImg).execute(filme.getPathImg());

            int width = 250;//display.getWidth(); // ((display.getWidth()*20)/100)
            int height = 290;//display.getHeight();// ((display.getHeight()*30)/100)
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            ivImg.setLayoutParams(parms);

        }

    }

    public void onClick(View view) {
        Uri uri = Uri.parse("http://www.google.com.br");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView ivImg;
        public DownloadImageTask(ImageView bmImage) {
            this.ivImg = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            ivImg.setImageBitmap(result);
        }
    }
}