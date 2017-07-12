package pl.lborowy.storewithflowers.services;

import java.util.List;

import io.reactivex.Observable;
import pl.lborowy.storewithflowers.models.Flower;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by RENT on 2017-07-12.
 */

// #3
public interface FlowersApi {

    // retrofit
    @GET("/feeds/flowers.json")
    Call<List<Flower>> getAllFlowers();

    // rxJava
    @GET("/feeds/flowers.json")
    Observable<List<Flower>> getAllFlowersRxJava();
}
