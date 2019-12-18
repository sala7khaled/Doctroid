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

import com.s7k.doctroid.R;

import java.util.HashMap;
import java.util.List;

import dialog.ProgressViewDialog;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.P_ID;
import network.model.UserProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.PrefManager;

public class UserFragment extends Fragment {

    public Context context;
    public TextView tv;
    ProgressViewDialog progressViewDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        context = getActivity().getApplicationContext();

        initializeComponents(view);
        setListeners();
        getUser();

        return view;
    }

    private void initializeComponents(View view) {
        tv = view.findViewById(R.id.userFragment_tv);
    }

    private void setListeners() {
    }

    private void getUser() {

        progressViewDialog = new ProgressViewDialog(getContext());
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting user information");

        P_ID p_id = new P_ID(PrefManager.getP_id(getContext()));

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UserProfile>> call = apiService.getUser(headers, p_id);
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserProfile>> call,
                                   @NonNull Response<List<UserProfile>> response) {

                if (response.isSuccessful()) {

                    List<UserProfile> users = response.body();

                    if (users != null) {
                        for (UserProfile user : users) {


                            tv.setText(user.getFirstName());
                            progressViewDialog.hideDialog();

                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<UserProfile>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
