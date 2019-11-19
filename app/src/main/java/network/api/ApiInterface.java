package network.api;

import java.util.Map;

import app.Constants;
import network.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP)
    Call<ResponseBody> doSignUpUser(@HeaderMap Map<String, String> headers,
                                    @Body User user);

//    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP)
//    Call<User> doSignUpUserConfirm(
//            @Body List<Medical> medical
//            @Body
//    );
//
//    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNIN)
//    Call<ResponseBody> doSignIpUser(
//            @Field("email") String email,
//            @Field("password") String password
//    );

}
