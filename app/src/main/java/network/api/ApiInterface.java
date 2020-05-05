package network.api;

import java.util.List;
import java.util.Map;

import app.Constants;
import network.model.AppointRequest;
import network.model.ConfirmSignUpForm;
import network.model.DeleteRequestForm;
import network.model.Hospital;
import network.model.MedicalCategory;
import network.model.Medicine;
import network.model.PatientID;
import network.model.Precautions;
import network.model.RequestIDs;
import network.model.SignInForm;
import network.model.Token;
import network.model.User;
import network.model.UserProfile;
import network.model.UsersRequests;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNIN)
    Call<Token> signIn(@HeaderMap Map<String, String> headers,
                       @Body SignInForm signInForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_USER_PROFILE)
    Call<UserProfile> getUser(@HeaderMap Map<String, String> headers,
                              @Body PatientID patientID);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP)
    Call<ResponseBody> signUp(@HeaderMap Map<String, String> headers,
                              @Body User user);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_SIGNUP_CONFIRM)
    Call<ResponseBody> signUpConfirm(@HeaderMap Map<String, String> headers,
                                     @Body ConfirmSignUpForm confirmSignUpForm);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_HOSPITAL)
    Call<Hospital> getHospital(@HeaderMap Map<String, String> headers);


    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_MEDICINE)
    Call<List<Medicine>> getMedicine(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_MEDICAL_CATEGORY)
    Call<List<MedicalCategory>> getMedicalCategory(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_REQUESTS)
    Call<List<UsersRequests>> getRequests(@HeaderMap Map<String, String> headers);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_GET_PRECAUTIONS + "/{id}")
    Call<Precautions> getPrecautions(@HeaderMap Map<String, String> headers,
                                     @Body Map<String, String> tId,
                                     @Path("id") String id);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_PATIENT_REQUEST)
    Call<RequestIDs> getPatientRequests(@HeaderMap Map<String, String> headers,
                                        @Body PatientID patientID);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_REQUEST)
    Call<ResponseBody> requestAppoint(@HeaderMap Map<String, String> headers,
                                      @Body AppointRequest appointRequest);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_POST_DELETE_REQUEST)
    Call<ResponseBody> deleteRequest(@HeaderMap Map<String, String> headers,
                                     @Body DeleteRequestForm deleteRequestForm);

}