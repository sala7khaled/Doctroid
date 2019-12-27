package presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.List;

import network.model.Medicine;
import presenter.holder.MedicineHolder;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> {

    private List<Medicine> medicineList;
    private Context context;
    private ItemClick itemClick;
    private MedicineType medicineType;


    public MedicineAdapter(List<Medicine> items, Context context, ItemClick itemClick, MedicineType medicineType) {
        this.medicineList = items;
        this.context = context;
        this.itemClick = itemClick;
        this.medicineType = medicineType;
    }

    public MedicineAdapter() {

    }

    public void addItem(Medicine medicine) {
        medicineList.add(medicine);
        notifyDataSetChanged();
    }

    public boolean checkExistMedicine(Medicine medicine) {
        return medicineList.contains(medicine);
    }

    public void deleteItem(int position) {
        medicineList.remove(position);
        notifyDataSetChanged();
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = 0;
        switch (medicineType) {
            case LIST:
                layoutId = R.layout.item_medicine;
                break;
            case AUTO_COMPELETE:
                layoutId = R.layout.item_medicine_list;
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new MedicineHolder(view, medicineType);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        switch (medicineType) {
            case LIST:
                holder.medicineName.setText(medicine.getName());
                holder.medicinePrice.setText(medicine.getPrice());
                holder.itemView.setOnClickListener( v-> itemClick.onClick(position));
                break;
            case AUTO_COMPELETE:
                holder.nameMedicineTextView.setText(medicine.getName());
                holder.deleteMedicineImageView.setOnClickListener(v -> itemClick.onClick(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public interface ItemClick {

        void onClick(int position);
    }
}