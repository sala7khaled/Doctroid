package presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.List;

import network.model.Category;
import network.model.Medicine;
import presenter.holder.MainHolder;
import presenter.holder.MedicineHolder;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> {

    private List<Medicine> medicineList;
    private Context context;
    private ItemClick itemClick;

    public MedicineAdapter(List<Medicine> items, Context context, ItemClick itemClick) {
        this.medicineList = items;
        this.context = context;
        this.itemClick = itemClick;
    }

    public MedicineAdapter() {
    }

    public void addItem(Medicine medicine) {
        medicineList.add(medicine);
        notifyDataSetChanged();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {

        Medicine medicine = medicineList.get(position);

        holder.nameMedicineTextView.setText(medicine.getName());

        holder.deleteMedicineImageView.setOnClickListener(view -> itemClick.onClick(position));
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public interface ItemClick {

        void onClick(int position);
    }
}