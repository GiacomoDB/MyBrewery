package brewery.giacomo.com.mybrewery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import brewery.giacomo.com.mybrewery.Adapters.Api;
import brewery.giacomo.com.mybrewery.Adapters.MySQLiteHelper;
import brewery.giacomo.com.mybrewery.Adapters.listAdapter;
import brewery.giacomo.com.mybrewery.model.Available;
import brewery.giacomo.com.mybrewery.model.Beer;
import brewery.giacomo.com.mybrewery.model.Lables;
import brewery.giacomo.com.mybrewery.model.MainRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private MainRequest request;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar loading;
    private ArrayList<Beer> beersList;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private ListView listBeers;
    private MySQLiteHelper db;
    SharedPreferences mPrefs;
    SQLiteDatabase Db;
    private Context context;
    SharedPreferences prefs;
    private Boolean isFav=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = (ProgressBar)findViewById(R.id.loadingBar);
        listBeers = (ListView) findViewById(R.id.list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        db = new MySQLiteHelper(getApplicationContext());
        setSupportActionBar(toolbar);
        context = this;
        Log.i(TAG,String.valueOf(db.getTableRows(Db,"beer")));
        //Log.i(TAG,db.getTableAsString(Db,"beer"));
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            if(!prefs.getBoolean("firstTime", false))
            {
                fetchBeers();
            }else{
                populateList();
            }

        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
        //click listner  to display single beer activity
        listBeers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, BeerDisplay.class);
                Beer beer = beersList.get(position);
                myIntent.putExtra("beer_id", beer.getId()); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    //downloading first page of belgian beers and parsing them with Retrofit
    void fetchBeers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.brewerydb.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<MainRequest> serverTimeCall = api.getBeers();
        serverTimeCall.enqueue(new Callback<MainRequest>() {
            @Override
            public void onResponse(Call<MainRequest> call, Response<MainRequest> response) {
                request = response.body();
                beersList = request.getBeers();
                Beer singleBeer = beersList.get(18);
                Lables icon = singleBeer.getIcon();
                Available available = singleBeer.getAvailable();
                Log.i(TAG,"name"+ singleBeer.getName()+" icon:"+icon.getMedium()+" available "+available.getDescription());
                new getIcons().execute("");
            }

            @Override
            public void onFailure(Call<MainRequest> call, Throwable t) {
                Log.i(TAG,String.valueOf(t));
                Toast.makeText(getApplicationContext(), "Error while getting beers", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*saving icons and populating DB*/
    public class getIcons extends AsyncTask<Object, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Object... objects) {
            for(int i=0;i<beersList.size();i++) {
                URL url = null;
                URL urlMed = null;
                try {
                    Beer webBeer = beersList.get(i);
                    Lables webIcon = webBeer.getIcon();
                    if(webIcon!=null) {
                        String webPath = webIcon.getIcon();
                        url = new URL(webPath);
                        HttpURLConnection conn = null;
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        Bitmap bmImg = BitmapFactory.decodeStream(is);
                        webIcon.setPath(saveImage(bmImg, webPath));
                        String webPathMed = webIcon.getMedium();
                        urlMed = new URL(webPathMed);
                        conn = (HttpURLConnection) urlMed.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream isMed = conn.getInputStream();
                        Bitmap bmImgMed = BitmapFactory.decodeStream(isMed);
                        webIcon.setPathMed(saveImage(bmImgMed, webPathMed));
                        webBeer.setIconPath(webIcon.getPath());
                        webBeer.setMediumPath(webIcon.getPathMed());
                    }else{

                    }
                    if(webBeer.getAvailable()!=null){
                        Available ava = webBeer.getAvailable();
                        webBeer.setAvailableDesc(ava.getDescription());
                    }
                    webBeer.setId(i);
                    db.createBeer(webBeer);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            SharedPreferences prefss = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefss.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
            return "";
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            int count = beersList.size();
            populateList();
        }
    }
    //saving icons in public folder
    private String saveImage(Bitmap bmp, String webPath) {
        String path = null;
        String filename = null;
        try {
            filename=webPath.substring(webPath.lastIndexOf("/")+1);
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "brewery/icons";
            File outputDir = new File(path);
            if (!outputDir.exists()) outputDir.mkdirs();
            File newFile = new File(path + "/" + filename);
            FileOutputStream fos = new FileOutputStream(newFile);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception error) {
            error.printStackTrace();
        }
        return path + "/" + filename;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //rebuil lists to refresh fav list
    @Override
    public void onResume(){
        super.onResume();
        if (isFav){
            populateFavList();
            isFav=true;
        }else{
            populateList();
            isFav=false;
        }
    }
    //menu that allows user to display favourites or normal list
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (!isFav){
                populateFavList();
                item.setTitle("Complete");
                isFav=true;
            }else{
                populateList();
                item.setTitle("Favourites");
                isFav=false;
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateList(){
        beersList = db.getAllBeers();
        listAdapter adapter = new listAdapter(getApplicationContext(),R.layout.list_row, beersList);
        listBeers.setAdapter(adapter);
        loading.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }
    public void populateFavList(){
        beersList = db.getFavourites();
        listAdapter adapter = new listAdapter(getApplicationContext(),R.layout.list_row, beersList);
        listBeers.setAdapter(adapter);
        loading.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // fetching data only on first open
                    prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    if(!prefs.getBoolean("firstTime", false))
                    {
                        fetchBeers();
                    }else{
                        populateList();
                    }
                } else {
                    Toast.makeText(context, "You need to anable it!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
