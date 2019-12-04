package view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Hospital;
import network.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.activity.SignInActivity;

import com.s7k.doctroid.R;

import java.util.HashMap;
import java.util.List;

public class HospitalAboutFragment extends Fragment {

    public Context context;
    public TextView hospitalInfo;

    public HospitalAboutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hospital_about_fragment, container, false);

        context = getActivity().getApplicationContext();

        initUI(view);
        getHospital();

        return view;
    }

    private void initUI(View view) {
        hospitalInfo = view.findViewById(R.id.hospitalAboutFragment_hospital_info);
    }

    private void getHospital() {

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Hospital>> call = apiService.getHospital(headers);
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hospital>> call,
                                   @NonNull Response<List<Hospital>> response) {

                if (response.isSuccessful()) {

                    List<Hospital> hospital = response.body();

                    if (hospital != null) {
                        for (Hospital hos : hospital) {
                            String content = "";

                            content += "Name: " + hos.getHospital_name() + "\n";
                            content += "Location: " + hos.getHospital_location() + "\n";
                            content += "Phone: " + hos.getHospital_phone() + "\n";
                            content += "Website: " + hos.getHospital_website() + "\n";
                            content += "Facebook: " + hos.getHospital_facebook() + "\n";
                            content += "Email: " + hos.getHospital_email() + "\n";
                            content += "General Manager: " + hos.getHospital_generalManager() + "\n";
                            content += "Adminstraton Manager: " + hos.getHospital_adminstratonManager() + "\n";
                            content += "IT Manager: " + hos.getHospital_itManager() + "\n";
                            content += "MarketingManager: " + hos.getHospital_MarketingManage() + "\n";
                            content += "Purchasing Manager: " + hos.getHospital_PurchasingManager() + "\n";

                            hospitalInfo.append(content);
                        }

                        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(context, response.body() + " " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Hospital>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
