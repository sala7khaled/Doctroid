package view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s7k.doctroid.R;

import java.util.HashMap;
import java.util.Objects;

import app.App;
import customView.CustomToast;
import customView.CustomToastType;
import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.PatientID;
import network.model.UserProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.InternetUtilities;
import utilities.PrefManager;
import view.activity.SignInActivity;
import view.category.AppointmentActivity;

import static es.dmoral.toasty.Toasty.LENGTH_LONG;

public class UserFragment extends Fragment {

    public Context context;
    private TextView username, email, location;
    private ImageView locationIcon;
    private ProgressViewDialog progressViewDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        context = getActivity().getApplicationContext();

        initializeComponents(view);
        setListeners();
        if (!InternetUtilities.isConnected(App.getApplication())) {
            CustomToast.Companion.darkColor(getContext(), CustomToastType.NO_INTERNET, getString(R.string.check_connection));
        } else {
            getUser();
        }

        return view;
    }

    private void initializeComponents(View view) {
        username = view.findViewById(R.id.userFragment_userName);
        email = view.findViewById(R.id.userFragment_email);
        location = view.findViewById(R.id.userFragment_location);

        locationIcon = view.findViewById(R.id.userFragment_location_imageView);
    }

    private void setListeners() {
    }

    private void getUser() {

        progressViewDialog = new ProgressViewDialog(getContext());
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting user information");

        if (PrefManager.getP_id(getContext()) != null) {
            PatientID patientID = new PatientID(PrefManager.getP_id(getContext()));

            HashMap<String, String> headers = ApiClient.getHeaders();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserProfile> call = apiService.getUser(headers, patientID);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(@NonNull Call<UserProfile> call,
                                       @NonNull Response<UserProfile> response) {

                    if (response.isSuccessful()) {

                        UserProfile user = Objects.requireNonNull(response.body());

                        if (user != null) {
                            username.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
                            email.setText(user.getEmail());
                            location.setText(user.getLocation());

                            locationIcon.setVisibility(View.VISIBLE);
                        }
                    } else {
                        CustomToast.Companion.darkColor(getContext(), CustomToastType.NO_INTERNET, "Error getting user data!");
                    }
                    progressViewDialog.hideDialog();

                }

                @Override
                public void onFailure(@NonNull Call<UserProfile> call,
                                      @NonNull Throwable t) {
                    CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
                }
            });
        } else {
            CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, "Something went wrong, Please Login again!");
        }

    }

}
