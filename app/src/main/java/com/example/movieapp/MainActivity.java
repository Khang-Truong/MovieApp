package com.example.movieapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.movieapp.Adapter.RecyclerViewAdapter;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.ViewModel.DataViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.movieapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    List<Movie> ItemList = new ArrayList<>();
    List<Movie> ItemListPrevious = new ArrayList<>();
    public DataViewModel dataViewModel;
    private Boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        loadData();
        Log.i("Test", ItemList.toString());
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.loadItems(ItemList);

        //set color
        ConstraintLayout mainLayout = binding.mainContent.mainLayout;
        mainLayout.setBackgroundColor(Color.parseColor("#FAFAFA")); //sets the default solid color
        binding.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        binding.fab1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        binding.fab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));

        closeFABMenu();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        binding.fab1.setOnClickListener((View v1)->{
            Snackbar.make(v1, "Want to find more projects? ", Snackbar.LENGTH_LONG)
                    .setAction("Click here!", (View v2) -> {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Khang-Truong")));
                    }).show();
        });

        binding.fab2.setOnClickListener((View v3) -> {
            ColorDrawable colorDrawable = (ColorDrawable) mainLayout.getBackground();
            int colorId = colorDrawable.getColor(); //note that getColor() will not work unless you have solid color

            //toggle color of floating btn + mainLayout
            if (colorId != Color.LTGRAY) {
                mainLayout.setBackgroundColor(Color.LTGRAY);
                binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                binding.fab1.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                binding.fab2.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
            } else {
                mainLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
                binding.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                binding.fab1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                binding.fab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
            }
//action when we click to undo in snackbar. colorId van giu data cu, chua call getColor nen chua update -> can undo no
            Snackbar.make(v3, "How do you like this color?", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", (View v4) -> {
                        //the colorId value doesn't update the new one yet
                        if (colorId == Color.LTGRAY) {
                            mainLayout.setBackgroundColor(Color.LTGRAY);
                            binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            binding.fab1.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            binding.fab2.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                        } else {
                            mainLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
                            binding.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                            binding.fab1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                            binding.fab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                        }
                    }).show();
        });
    }

    private void closeFABMenu() {
        isFABOpen = false;
        binding.fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        binding.fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void showFABMenu() {
        isFABOpen = true;
        binding.fab1.animate().translationY(0);
        binding.fab2.animate().translationY(0);
    }

    private void loadData() {
        ItemList = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.movies);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String CSVLine;
            CSVLine = reader.readLine();
            while ((CSVLine = reader.readLine()) != null) {
                String[] dataFields = CSVLine.split(";");
                String imgUrl = dataFields[0];
                String title = dataFields[1];
                Double rating = Double.parseDouble(dataFields[2]);
                String overview = dataFields[3];

                Movie movie = new Movie(imgUrl, title, rating, overview);
                ItemList.add(movie);
            }
        } catch (Exception ex) {
            Log.d("DEMO", ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}