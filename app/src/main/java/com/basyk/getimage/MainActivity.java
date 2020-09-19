package com.basyk.getimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.basyk.getimage.adapter.PhotoAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetImage{

    private Call<ResponseBody> call;

    private RecyclerView recyclerViewPhone;
    private PhotoAdapter adapter;
    private List<String> items = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPhone = findViewById(R.id.recyclerViewPhone);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImages();
            }
        });

        getImages();
    }
    private void getImages(){
        call = MyApp.apiService.getImages2();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    JSONArray array = new JSONArray(s);
                    if(array.length()>0 ){
                        if (items.isEmpty()){
                            for(int i=0;i<array.length(); i++){
                                items.add(array.get(i).toString());
                            }
                        }

                    }
                    adapter = new PhotoAdapter(items, MainActivity.this, MainActivity.this);

                    int count = getResources().getInteger(R.integer.num_column_count);
                    int add = getResources().getInteger(R.integer.add_column_count_by_orientation);
                    recyclerViewPhone.setLayoutManager(new GridLayoutManager(MainActivity.this, count + add));

                    recyclerViewPhone.setHasFixedSize(true);
                    recyclerViewPhone.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewPhone.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "----");
            }
        });
    }
    @Override
    public void onClikableItem(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("image", items.get(position));
        startActivity(intent);
    }
}