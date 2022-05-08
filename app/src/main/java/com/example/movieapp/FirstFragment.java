package com.example.movieapp;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.movieapp.Adapter.RecyclerViewAdapter;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.ViewModel.DataViewModel;
import com.example.movieapp.VolleySingleton.VolleySingleton;
import com.example.movieapp.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    List<Movie> movieList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
        DataViewModel dataViewModel=new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        dataViewModel.getItems().observe(requireActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> items) {
                movieList=items;
                //put data to adapter
                LinearLayoutManager lm=new LinearLayoutManager(getContext());
                binding.recyclerViewFrag1.setLayoutManager(lm);

                //put adapter to recyclerView to show
                binding.recyclerViewFrag1.setAdapter(new RecyclerViewAdapter(movieList, getContext()));

                DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),lm.getOrientation()){
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.left=20;
                        outRect.top=10;
                        outRect.right=20;
                        outRect.bottom=10;
                    }
                };
                binding.recyclerViewFrag1.addItemDecoration(dividerItemDecoration);
            }
        });
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                String details="";
                int total=0;
                for(int i=0;i<movieList.size();i++){
                    if(movieList.get(i).getQuantity()>0){
                        details+=movieList.get(i).getTitle()+": "+movieList.get(i).getQuantity()+"\n";
                        total+=movieList.get(i).getQuantity();
                    }
                }
                bundle.putString("DETAILS",details);
                bundle.putInt("TOTAL",total);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
            }
        });
    }

    private void loadData() {
        movieList=new ArrayList<>();
        InputStream inputStream=getResources().openRawResource(R.raw.movies);
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        try{
            String CSVLine;
            CSVLine=reader.readLine();
            while((CSVLine=reader.readLine())!=null){
                String[] dataFields=CSVLine.split(";");
                String imgUrl=dataFields[0];
                String title=dataFields[1];
                Double rating=Double.parseDouble(dataFields[2]);
                String overview=dataFields[3];

                Movie movie = new Movie(imgUrl,title, rating, overview);
                movieList.add(movie);
            }
        }catch (Exception ex){
            Log.d("DEMO",ex.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}