package pl.lborowy.storewithflowers.services;

import java.util.List;

import pl.lborowy.storewithflowers.models.Flower;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by RENT on 2017-07-12.
 */

// #3
public interface FlowersApi {

    @GET("/feeds/flowers.json")
    Call<List<Flower>> getAllFlowers();
}
