package network.api;

import java.util.Map;

import app.Constants;
import network.model.SignInForm;
import network.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNIN)
    Call<User> signIn(@HeaderMap Map<String, String> headers,
                              @Body SignInForm signInForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP)
    Call<ResponseBody> signUp(@HeaderMap Map<String, String> headers,
                              @Body User user);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP_CONFIRM)
    Call<ResponseBody> signUpConfirm(@HeaderMap Map<String, String> headers,
                                     @Body String date,
                                     @Body String location,
                                     @Body boolean confirm);
}