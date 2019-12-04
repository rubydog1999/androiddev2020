package vn.edu.usth.usth_weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {
    MediaPlayer music;
    final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            // This method is executed in main thread
            String content = msg.getData().getString("server_response");
            Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i("onCreate", "weather activity is being created");
        PagerAdapter adapter = new HomeFragmentPagerAdapter(
                getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(pager);

        // Copy music file to sdcard0
        copyFileToExternalStorage(R.raw.nhac, "nhac.mp3");

        // Play music in the app
        music = MediaPlayer.create(WeatherActivity.this, R.raw.nhac);
        music.start();
        music.setLooping(true);
        // labwork 15: fetch image from server
        new GetRequestImage().execute("https://ictlab.usth.edu.vn/wp-content/uploads/logos/usth.png");
    }
    private class GetRequestImage extends AsyncTask<String, Void, Bitmap> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(WeatherActivity.this,
                    "Updating weather...",
                    "Wait for 5 seconds!");
        }
        @Override

        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
        }
            return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressDialog.dismiss();
            ImageView imageView = (ImageView) findViewById(R.id.logo);
            imageView.setImageBitmap(bitmap);
        }
    }

    /* particle_work_13
    final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            // This method is executed in main thread
            String content = msg.getData().getString("server_response");
            Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
        }
    };
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            // this method is run in a worker thread
            try {
                // wait for 5 seconds to simulate a long network access
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Assume that we got our data from server
            Bundle bundle = new Bundle();
            bundle.putString("server_response", "some sample json here");
            // notify main thread
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    });

        t.start();

}*/




        // labwork 14:
        private class AsyncTaskRunner extends AsyncTask<String, String, String> {
            private String resp;
            ProgressDialog progressDialog;

            @Override
            protected String doInBackground(String...params) {
                try {


                     Thread.sleep(5000);
                    resp = "Sleep for 5 seconds";}
                     catch (InterruptedException e) {
                    e.printStackTrace();
                    resp = e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                     resp = e.getMessage();
                }
                return resp;
            }
            @Override
            protected void onPostExecute(String result) {
                // execution of result of Long time consuming operation
                progressDialog.dismiss();
                // Assume that we got our data from server
                Bundle bundle = new Bundle();
                bundle.putString("server_response","some sample json hereeeeeee");

                // notify main thread
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(WeatherActivity.this,
                        "Updating weather...",
                        "Wait for 5 seconds!");
            }

            @Override
            protected void onProgressUpdate(String... text) {
                // Do something here
            }
        }






    private void copyFileToExternalStorage(int resourceId, String resourceName){
        String pathSDCard = Environment.getExternalStorageDirectory()
                + "/Android/data/vn.edu.usth.weather/" + resourceName;
        try{
            InputStream in = getResources().openRawResource(resourceId);
            FileOutputStream out = null;
            out = new FileOutputStream(pathSDCard);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    // praticle_work_12_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute("5000");
                music.seekTo(5);

                music.start();
// do something when search is pressed here
                return true;
            case R.id.item_1:
                Intent intent = new Intent(WeatherActivity.this, PreActivity.class);
                startActivity(intent);
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }




    /*private void setAppLocale(String localeCode){
        Locale myLocale = new Locale(localeCode);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent();
        finish();

        startActivity(refresh);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        Log.i ("onStart", "weather activity is starting");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i ("onStart", "weather activity is stopping");
        music.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i ("onDestroy", "weather activity is destroyed");
        music.release();
        music = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i ("onResume", "weather activity is resuming");
        music.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i( "onPause", "weather activity is pausing");
        music.pause();
    }


    // praticle 7
    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 3;
        private String titles[] = new String[] { "Hanoi", "Paris", "Toulouse" };
        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return PAGE_COUNT; // number of pages for a ViewPager
        }
        @Override
        public Fragment getItem(int page) {
// returns an instance of Fragment corresponding to the specified page
            switch (page) {
                case 0: return new WeatherAndForecastFragment();
                case 1: return new WeatherAndForecastFragment();
                case 2: return new WeatherAndForecastFragment();

            }
            return new Fragment(); // failsafe
        }
        @Override
        public CharSequence getPageTitle(int page) {
// returns a tab title corresponding to the specified page
            return titles[page];
        }
    }
}

