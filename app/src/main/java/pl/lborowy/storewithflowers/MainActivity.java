package pl.lborowy.storewithflowers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.lborowy.storewithflowers.adapters.FlowerArrayAdapter;
import pl.lborowy.storewithflowers.models.Flower;
import pl.lborowy.storewithflowers.services.FlowersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements FlowerArrayAdapter.OnItemClicked{

    public static final String BASE_URL = "http://services.hanselandpetal.com";
    private Retrofit retrofit;
    private FlowersApi flowersApi;
    private List<Flower> flowersList;
    private FlowerArrayAdapter flowerArrayAdapter;

    // #7
    @BindView(R.id.listView_mainActivity)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        prepareRetrofit();
//        getFlowersRetrofit();
        prepareAdapter();
        getFlowersRxJava();
    }

    private void prepareAdapter() {
        // #6
        flowersList = new ArrayList<>(); // tworzymy liste
        // #9
        flowerArrayAdapter = new FlowerArrayAdapter(this, flowersList, this); // tworzymy adapter
        listView.setAdapter(flowerArrayAdapter);
    }

//    private void getFlowersRetrofit() {
//        // retrofit - pobieranie kwiatków
//        flowersApi.getAllFlowers().enqueue(new Callback<List<Flower>>() {
//            @Override
//            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
//                showToast("response");
//            }
//
//            @Override
//            public void onFailure(Call<List<Flower>> call, Throwable t) {
//                showToast("fail");
//            }
//        });
//    }

    private void getFlowersRxJava() {
        // rxJaxa - pobieranie kwiatków
        flowersApi.getAllFlowersRxJava()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // wraca na wątek graficzny na koniec / View
                .subscribe(new Observer<List<Flower>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<Flower> flowers) {
//                        MainActivity.this.flowersList = flowers;
                        // #6
                        flowersList.addAll(flowers);
                        // po stworzeniu arrayadaptera
                        flowerArrayAdapter.notifyDataSetChanged();
                        Log.d("RxJava", "New flowers!" + flowers.size());
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("rxJava", "DONE!");
                        showToast("DONE!");
                    }
                });
    }

    private void prepareRetrofit() {
        // #1 Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                // #5
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // dopiero teraz możemy zwracać Observable
                .build();

        //  /feeds/flowers.json

        // #4
        flowersApi = retrofit.create(FlowersApi.class);
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

    @Override
    public void onRowClicked(Flower flower) {
        showToast("Kliknałeś: " + flower.getName());
    }

    @Override
    public void onPiceClicked(Flower flower) {
        showToast(flower.getName() + " jest za drogi!");
    }
}
