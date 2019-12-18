package network.api;

import java.util.List;
import java.util.Map;

import app.Constants;
import network.model.ConfirmSignUpForm;
import network.model.Hospital;
import network.model.Medicine;
import network.model.PatientID;
import network.model.SignInForm;
import network.model.Token;
import network.model.User;
import network.model.UserProfile;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNIN)
    Call<Token> signIn(@HeaderMap Map<String, String> headers,
                       @Body SignInForm signInForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_USER_PROFILE)
    Call<List<UserProfile>> getUser(@HeaderMap Map<String, String> headers,
                                    @Body PatientID patientID);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP)
    Call<ResponseBody> signUp(@HeaderMap Map<String, String> headers,
                              @Body User user);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP_CONFIRM)
    Call<ResponseBody> signUpConfirm(@HeaderMap Map<String, String> headers,
                                     @Body ConfirmSignUpForm confirmSignUpForm);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_HOSPITAL)
    Call<List<Hospital>> getHospital(@HeaderMap Map<String, String> headers);


    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_MEDICINE)
    Call<List<Medicine>> getMedicine(@HeaderMap Map<String, String> headers);
}