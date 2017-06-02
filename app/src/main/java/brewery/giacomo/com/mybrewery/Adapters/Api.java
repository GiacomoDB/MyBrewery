package brewery.giacomo.com.mybrewery.Adapters;

import brewery.giacomo.com.mybrewery.model.MainRequest;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Giacomo on 31/05/2017.
 */

public interface Api {
    @GET("search?key=d1dfd8e70cc09bc2a50bfd7ff7863334&q=Belgian-Style%20Quadrupel&type=beer")
    public Call<MainRequest> getBeers();
}
