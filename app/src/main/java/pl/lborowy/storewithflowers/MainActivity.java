package pl.lborowy.storewithflowers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import pl.lborowy.storewithflowers.models.Flower;
import pl.lborowy.storewithflowers.services.FlowersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private FlowersApi flowersApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // #1 Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://services.hanselandpetal.com")
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();

        //  /feeds/flowers.json

        // #4
        flowersApi = retrofit.create(FlowersApi.class);

        flowersApi.getAllFlowers().enqueue(new Callback<List<Flower>>() {
            @Override
            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                showToast("response");
            }

            @Override
            public void onFailure(Call<List<Flower>> call, Throwable t) {
                showToast("fail");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // #1
    @NonNull
    private Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();
    }
}
