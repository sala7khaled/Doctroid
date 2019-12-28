package view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class MedicineInfoActivity extends BaseActivity {

    TextView name, quantity, price, description;

    public MedicineInfoActivity() {
        super(R.layout.activity_medicine_info, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarBackImageView.setVisibility(View.VISIBLE);

        initializeComponents();
        setListeners();

    }

    private void initializeComponents() {
        name = findViewById(R.id.medicineInfo_name);
        quantity = findViewById(R.id.medicineInfo_quantity);
        price = findViewById(R.id.medicineInfo_price);
        description = findViewById(R.id.medicineInfo_description);
    }

    private void setListeners() {
        Intent intent = getIntent();

        toolbarTextView.setText(intent.getStringExtra("medicineName"));

        name.setText(intent.getStringExtra("medicineName"));
        quantity.setText("Quantity: " + intent.getStringExtra("medicineQuantity"));
        price.setText(intent.getStringExtra("medicinePrice") + " LE");
        description.setText("Description: " + intent.getStringExtra("medicineDescription"));
    }

}
