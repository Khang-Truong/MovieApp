package com.example.movieapp.ViewModel;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.Model.Movie;

import java.util.List;

public class DataViewModel extends ViewModel {
    MutableLiveData<List<Movie>> ItemList=new MutableLiveData<>();
    public LiveData<List<Movie>> getItems(){
        if(ItemList==null) ItemList=new MutableLiveData<>();
        return ItemList;
    }

    public void loadItems(List<Movie> anyList){
        Handler handler= new Handler();
        handler.postDelayed(()->{
            ItemList.setValue(anyList);
        },1000);
    }
}