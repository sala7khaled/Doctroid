package view.category;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Medicine;
import presenter.adapter.MedicineAdapter;
import presenter.adapter.MedicineType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.activity.AddMedicineActivity;
import view.activity.MedicineInfoActivity;
import view.base.BaseActivity;

import static es.dmoral.toasty.Toasty.LENGTH_LONG;

public class MedicineActivity extends BaseActivity {

    MedicineAdapter medicineAdapter;
    RecyclerView medicineRecyclerView;
    ProgressViewDialog progressViewDialog;
    List<Medicine> medicinesAPI = new ArrayList<>();

    public MedicineActivity() {
        super(R.layout.activity_medicine, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Medicine");
        toolbarBackImageView.setVisibility(View.VISIBLE);
        initializeComponents();
        getMedicines();

        setListeners();

    }

    private void initializeComponents() {

        medicineRecyclerView = findViewById(R.id.medicine_recyclerView);
        medicineRecyclerView.setLayoutManager(new GridLayoutManager(MedicineActivity.this, 2));
        medicineRecyclerView.setHasFixedSize(true);


    }

    private void setListeners() {
    }

    private void getMedicines() {
        progressViewDialog = new ProgressViewDialog(this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting medicines information");

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Medicine>> call = apiService.getMedicine(headers);
        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(@NonNull Call<List<Medicine>> call,
                                   @NonNull Response<List<Medicine>> response) {

                if (response.isSuccessful()) {

                    medicinesAPI = response.body();

                    if(medicinesAPI != null)
                    {
                        medicineAdapter = new MedicineAdapter(medicinesAPI, MedicineActivity.this,
                                position ->
                                {
                                    Intent intent = new Intent(MedicineActivity.this, MedicineInfoActivity.class);
                                    intent.putExtra("medicineName", medicinesAPI.get(position).getName());
                                    intent.putExtra("medicineQuantity", medicinesAPI.get(position).getQuantity());
                                    intent.putExtra("medicinePrice", medicinesAPI.get(position).getPrice());
                                    intent.putExtra("medicineDescription", medicinesAPI.get(position).getDescription());

                                    Pair[] pair = new Pair[4];
                                    pair[0] = new Pair<View, String>(findViewById(R.id.item_medicineList_photo), "transPhoto");
                                    pair[1] = new Pair<View, String>(findViewById(R.id.item_medicineList_name), "transName");
                                    pair[2] = new Pair<View, String>(findViewById(R.id.item_medicineList_quantity), "transQuantity");
                                    pair[3] = new Pair<View, String>(findViewById(R.id.item_medicineList_price), "transPrice");


                                    ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                                                    .makeSceneTransitionAnimation(MedicineActivity.this,pair);



                                    startActivity(intent, activityOptions.toBundle());
                                }
                                , MedicineType.LIST);

                        medicineRecyclerView.setAdapter(medicineAdapter);
                    }
                    else
                    {
                        Toasty.error(MedicineActivity.this, "There's no medicines").show();
                    }

                    progressViewDialog.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Medicine>> call,
                                  @NonNull Throwable t) {
                Toasty.error(MedicineActivity.this, t.getMessage(), LENGTH_LONG).show();
            }
        });
    }
}
