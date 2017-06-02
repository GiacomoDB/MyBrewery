package brewery.giacomo.com.mybrewery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import brewery.giacomo.com.mybrewery.Adapters.MySQLiteHelper;
import brewery.giacomo.com.mybrewery.model.Available;
import brewery.giacomo.com.mybrewery.model.Beer;

public class BeerDisplay extends AppCompatActivity {
    private TextView name;
    private ImageView medium;
    private TextView description;
    private TextView abv;
    private TextView ibu;
    private TextView srm;
    private TextView og;
    private TextView available;
    private MySQLiteHelper db;
    private int fav;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        db = new MySQLiteHelper(getApplicationContext());
        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        abv = (TextView) findViewById(R.id.abv);
        ibu = (TextView) findViewById(R.id.ibu);
        srm = (TextView) findViewById(R.id.srm);
        og = (TextView) findViewById(R.id.og);
        available = (TextView) findViewById(R.id.available);
        medium = (ImageView) findViewById(R.id.pic);
        Intent intent = getIntent();
        final int beerId = intent.getIntExtra("beer_id",0);
        Beer beer = db.getBeer(beerId);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fav = db.checkFavourite(beerId);
        if (fav!=1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_remove, this.getTheme()));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_remove));
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_add, this.getTheme()));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_add));
            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav != 1){
                    db.setAsFavourite(beerId,0);
                    Snackbar.make(view, "You removed this beer from your favourites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fav = 1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_add, BeerDisplay.this.getTheme()));
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_add));
                    }
                }else{
                    db.setAsFavourite(beerId,1);
                    Snackbar.make(view, "You added this beer in your favourites, good choice :)", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fav = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_remove, BeerDisplay.this.getTheme()));
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.favourite_remove));
                    }
                }

            }
        });
        if (beer.getName()!=null){
            name.setText(beer.getName());
        }
        if (beer.getDescription()!=null){
            description.setText(beer.getDescription());
        }
        if (beer.getAbv()!=null){
            abv.setText("ABV: "+String.valueOf(beer.getAbv()));
        }
        if (beer.getIBU()==0){
            ibu.setText("IBU: "+String.valueOf(beer.getIBU()));
        }
        if (beer.getStyled()!=0){
            srm.setText("SRM: "+String.valueOf(beer.getStyled()));
        }
        if (beer.getIsOrganic()!=null){
            og.setText("Organic: "+beer.getIsOrganic());
        }
        if (beer.getAvailableDesc().equalsIgnoreCase("null")){
        }else{
            available.setText(beer.getAvailableDesc());
        }

        if(beer.getMediumPath().equalsIgnoreCase("null")) {
            medium.setImageResource(R.drawable.pic);
        }else{
            Bitmap iconBt = BitmapFactory.decodeFile(beer.getMediumPath());
            BitmapDrawable iconSr = new BitmapDrawable(getResources(), iconBt);
            medium.setBackgroundDrawable(iconSr);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
