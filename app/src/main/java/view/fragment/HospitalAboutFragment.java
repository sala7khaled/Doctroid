package view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import app.App;
import customView.CustomToast;
import customView.CustomToastType;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Hospital;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.InternetUtilities;
import view.category.AppointmentActivity;
import view.category.ResultActivity;

import com.s7k.doctroid.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
        if (!InternetUtilities.isConnected(App.getApplication())) {
            CustomToast.Companion.darkColor(getContext(), CustomToastType.NO_INTERNET, getString(R.string.check_connection));
        } else {
            getHospital();
        }

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

        lineView.setVisibility(view.INVISIBLE);

        location_layout.setVisibility(view.INVISIBLE);
        phone_layout.setVisibility(view.INVISIBLE);
        website_layout.setVisibility(view.INVISIBLE);
        facebook_layout.setVisibility(view.INVISIBLE);
        email_layout.setVisibility(view.INVISIBLE);
    }

    private void getHospital() {

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Hospital> call = apiService.getHospital(headers);
        call.enqueue(new Callback<Hospital>() {
            @Override
            public void onResponse(@NonNull Call<Hospital> call,
                                   @NonNull Response<Hospital> response) {

                if (response.isSuccessful()) {

                    Hospital hospital = response.body();

                    if (hospital != null) {
                        hospitalName.setText(hospital.getHospital_name());
                        hospitalLocation.setText(hospital.getHospital_location());
                        hospitalPhone.setText(hospital.getHospital_phone());
                        hospitalWebsite.setText(hospital.getHospital_website());
                        hospitalFacebook.setText(hospital.getHospital_facebook());

                        progressBar.setVisibility(View.GONE);
                        lineView.setVisibility(View.VISIBLE);
                        location_layout.setVisibility(View.VISIBLE);
                        phone_layout.setVisibility(View.VISIBLE);
                        website_layout.setVisibility(View.VISIBLE);
                        facebook_layout.setVisibility(View.VISIBLE);
                        email_layout.setVisibility(View.VISIBLE);

                        String emails = "";
                        emails += "⦿ Email: " + hospital.getHospital_email() + "\n";
                        emails += "• General Manager: " + hospital.getHospital_generalManager() + "\n";
                        emails += "• Administration Manager: " + hospital.getHospital_adminstratonManager() + "\n";
                        emails += "• IT Manager: " + hospital.getHospital_itManager() + "\n";
                        emails += "• Marketing Manager: " + hospital.getHospital_MarketingManager() + "\n";
                        emails += "• Purchasing Manager: " + hospital.getHospital_PurchasingManager() + "\n";

                        hospitalEmail.append(emails);
                    }

                } else {
                    CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, getString(R.string.something_wrong));
                }

            }

            @Override
            public void onFailure(@NonNull Call<Hospital> call,
                                  @NonNull Throwable t) {
                CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

}
