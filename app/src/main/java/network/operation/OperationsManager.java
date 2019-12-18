package network.operation;

import android.util.Log;

import com.s7k.doctroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import app.App;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.User;
import network.observer.CTHttpError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import utilities.Utilities;

public class OperationsManager {

    private static final String TAG = "OperationsManager";
    private static OperationsManager _instance = null;

    public static OperationsManager getInstance() {
        if (_instance == null)
            _instance = new OperationsManager();
        return _instance;
    }

    public ResponseBody doSignUpUser(User user) throws IOException {

        Log.v(TAG, "doSignUpUser");

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.signUp(headers, user);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    private void ensureHttpSuccess(Response response) throws IOException {
        if (!response.isSuccessful() && response.errorBody() != null) {
            ResponseBody errorBody = response.errorBody();
            String errorMSG = errorBody.string();
            int code = response.code();
            if (code == 504 && Utilities.isNullString(errorMSG))
                errorMSG = App.getContext().getString(R.string.request_error);
            else if (!Utilities.isNullString(errorMSG) && errorMSG.trim().startsWith("{")
                    && errorMSG.trim().endsWith("}")) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(errorMSG);
                    errorMSG = jsonObject.getString("message");
                    if (jsonObject.has("code"))
                        code = jsonObject.optInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            throw new CTHttpError(errorMSG, code);
        }
    }
}
