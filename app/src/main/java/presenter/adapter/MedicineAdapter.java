package presenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import network.model.Medicine;
import presenter.holder.MedicineHolder;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> implements Filterable {

    private List<Medicine> medicineList;
    private List<Medicine> searchList;
    private Context context;
    private ItemClick itemClick;
    private MedicineType medicineType;
    private String [] userMedicines;

    public MedicineAdapter(List<Medicine> items,String[] userMedicines, Context context, ItemClick itemClick, MedicineType medicineType) {
        this.medicineList = items;
        this.context = context;
        this.itemClick = itemClick;
        this.medicineType = medicineType;
        this.userMedicines = userMedicines;
        searchList = new ArrayList<>(medicineList);
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
                layoutId = R.layout.item_medicine_list;
                break;
            case AUTO_COMPLETE:
                layoutId = R.layout.item_medicine_auto_complete;
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

                for (String userMedicine : userMedicines) {
                    if (userMedicine.equals(medicine.getId())) {
                        holder.medicineUserImageView.setVisibility(View.VISIBLE);
                    }
                }

                holder.medicineImageView.setVisibility(View.VISIBLE);
                holder.medicineName.setText(medicine.getName());

                if (Integer.parseInt(medicine.getQuantity()) < 10)
                {
                    holder.medicineQuantity.setTextColor(context.getResources().getColor(R.color.colorRed));
                }
                if (Integer.parseInt(medicine.getQuantity()) == 0)
                {
                    holder.medicineQuantity.setTextColor(context.getResources().getColor(R.color.colorGray));
                }

                holder.medicineQuantity.setText("QNT: "+medicine.getQuantity());
                holder.medicinePrice.setText(medicine.getPrice()+" LE");
                holder.itemView.setOnClickListener( v-> itemClick.onClick(position));

                Picasso.get()
                        .load(medicine.getImage())
                        .fit()
                        .error(R.drawable.icon_no_connection)
                        .into(holder.medicineImageView);

                break;
            case AUTO_COMPLETE:
                holder.nameMedicineTextView.setText(medicine.getName());
                holder.deleteMedicineImageView.setOnClickListener(v -> itemClick.onClick(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Medicine> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(searchList);
            }
            else
            {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (Medicine medicine : searchList)
                {
                    if (medicine.getName().toLowerCase().contains(filteredPattern))
                    {
                        filteredList.add(medicine);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            medicineList.clear();
            medicineList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface ItemClick {

        void onClick(int position);
    }
}