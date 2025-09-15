package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.*;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface SpendApi {

  @POST("internal/spends/add")
  Call<SpendJson> addSpend(@Body SpendJson spending);

  @PATCH("internal/spends/edit")
  Call<SpendJson> editSpend(@Body SpendJson spend);

  @GET("internal/spends/{id}")
  Call<SpendJson> getSpend(@Path("id") String id);

  @GET("internal/spends/all")
  Call<List<SpendJson>> getAllSpends(@Query("username") String username,
                                  @Nullable @Query("filterCurrency") CurrencyValues filterCurrency,
                                  @Nullable @Query("from") String from,
                                  @Nullable @Query("to") String to);

  @DELETE("internal/spends/remove")
  Call<Void> removeSpend(@Query("ids") List<String> ids);

  @POST("internal/categories/add")
  Call<CategoryJson> addCategory(@Body CategoryJson category);

  @PATCH("internal/categories/update")
  Call<CategoryJson> updateCategory(@Body CategoryJson category);

  @GET("internal/categories/all")
  Call<List<CategoryJson>> getAllCategories(@Query("username") String username);
}
