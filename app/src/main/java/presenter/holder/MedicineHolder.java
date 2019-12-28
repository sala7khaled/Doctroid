package presenter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import presenter.adapter.MedicineType;

public class MedicineHolder extends RecyclerView.ViewHolder {

    public ImageView deleteMedicineImageView;
    public TextView nameMedicineTextView;

    public ImageView medicineImageView, medicineUserImageView;
    public TextView medicineName;
    public TextView medicineQuantity;
    public TextView medicinePrice;


    public MedicineHolder(View view, MedicineType medicineType) {
        super(view);

        switch (medicineType) {
            case LIST:
                medicineImageView = view.findViewById(R.id.item_medicineList_photo);
                medicineUserImageView = view.findViewById(R.id.item_medicineList_user);
                medicineName = view.findViewById(R.id.item_medicineList_name);
                medicineQuantity = view.findViewById(R.id.item_medicineList_quantity);
                medicinePrice = view.findViewById(R.id.item_medicineList_price);
                break;
            case AUTO_COMPLETE:
                deleteMedicineImageView = view.findViewById(R.id.item_medicineAutoComplete_delete_imageView);
                nameMedicineTextView = view.findViewById(R.id.item_medicineAutoComplete_name_textView);
                break;
        }
    }
}