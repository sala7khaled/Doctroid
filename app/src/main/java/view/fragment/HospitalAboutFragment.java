package view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Hospital;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.activity.AddMedicineActivity;

import com.s7k.doctroid.R;

import java.util.HashMap;
import java.util.List;

import static es.dmoral.toasty.Toasty.LENGTH_LONG;

public class HospitalAboutFragment extends Fragment {

    public Context context;
    private TextView hospitalName, hospitalLocation,
            hospitalPhone, hospitalWebsite, hospitalFacebook, hospitalEmail;

    private ProgressBar progressBar;

    private LinearLayout location_layout, phone_layout, website_layout, facebook_layout, email_layout;

    private View lineView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hospital_about_fragment, container, false);

        context = getActivity().getApplicationContext();

        initUI(view);
        getHospital();

        return view;
    }

    private void initUI(View view) {
        lineView = view.findViewById(R.id.hospitalAboutFragment_view);
        progressBar = view.findViewById(R.id.hospitalAboutFragment_progressBar);

        hospitalName = view.findViewById(R.id.hospitalAboutFragment_hospitalName_TV);
        hospitalLocation = view.findViewById(R.id.hospitalAboutFragment_hospitalLocation_TV);
        hospitalPhone = view.findViewById(R.id.hospitalAboutFragment_hospitalPhone_TV);
        hospitalWebsite = view.findViewById(R.id.hospitalAboutFragment_hospitalWebsite_TV);
        hospitalFacebook = view.findViewById(R.id.hospitalAboutFragment_hospitalFacebook_TV);
        hospitalEmail = view.findViewById(R.id.hospitalAboutFragment_hospitalEmail_TV);

        location_layout = view.findViewById(R.id.location_layout);
        phone_layout = view.findViewById(R.id.phone_layout);
        website_layout = view.findViewById(R.id.website_layout);
        facebook_layout = view.findViewById(R.id.facebook_layout);
        email_layout = view.findViewById(R.id.emails_layout);

        lineView.setVisibility(View.INVISIBLE);

        location_layout.setVisibility(View.INVISIBLE);
        phone_layout.setVisibility(View.INVISIBLE);
        website_layout.setVisibility(View.INVISIBLE);
        facebook_layout.setVisibility(View.INVISIBLE);
        email_layout.setVisibility(View.INVISIBLE);
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

                            hospitalName.setText(hos.getHospital_name());
                            hospitalLocation.setText(hos.getHospital_location());
                            hospitalPhone.setText(hos.getHospital_phone());
                            hospitalWebsite.setText(hos.getHospital_website());
                            hospitalFacebook.setText(hos.getHospital_facebook());

                            progressBar.setVisibility(View.GONE);

                            lineView.setVisibility(View.VISIBLE);

                            location_layout.setVisibility(View.VISIBLE);
                            phone_layout.setVisibility(View.VISIBLE);
                            website_layout.setVisibility(View.VISIBLE);
                            facebook_layout.setVisibility(View.VISIBLE);
                            email_layout.setVisibility(View.VISIBLE);

                            String content = "";
                            content += "⦿ Email: " + hos.getHospital_email() + "\n";
                            content += "• General Manager: " + hos.getHospital_generalManager() + "\n";
                            content += "• Administration Manager: " + hos.getHospital_adminstratonManager() + "\n";
                            content += "• IT Manager: " + hos.getHospital_itManager() + "\n";
                            content += "• Marketing Manager: " + hos.getHospital_MarketingManager() + "\n";
                            content += "• Purchasing Manager: " + hos.getHospital_PurchasingManager() + "\n";

                            hospitalEmail.append(content);
                        }

                    }

                } else {
                    Toasty.error(context, R.string.something_wrong, LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Hospital>> call,
                                  @NonNull Throwable t) {
                Toasty.error(context, t.getMessage(), LENGTH_LONG).show();
            }
        });
    }

}
