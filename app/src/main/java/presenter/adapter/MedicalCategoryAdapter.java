package presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.List;

import network.model.Category;
import network.model.MedicalCategory;
import network.model.Medicine;
import presenter.holder.MedicalCategoryHolder;
import presenter.holder.MedicineHolder;

import static presenter.adapter.MedicineType.LIST;

public class MedicalCategoryAdapter extends RecyclerView.Adapter<MedicalCategoryHolder> implements Filterable {

    private List<MedicalCategory> medicalList;
    private List<MedicalCategory> searchList;
    private Context context;
    private ItemClick itemClick;

    public MedicalCategoryAdapter(Context context, List<MedicalCategory> items, ItemClick itemClick) {

        this.context = context;
        this.medicalList = items;
        this.itemClick = itemClick;
        searchList = new ArrayList<>(medicalList);
    }

    public void addItem(MedicalCategory medicalCategory) {
        medicalList.add(medicalCategory);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        medicalList.remove(position);
        notifyDataSetChanged();
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public MedicalCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_category, parent, false);
        return new MedicalCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalCategoryHolder holder, int position) {

        MedicalCategory medicalCategory = medicalList.get(position);

        holder.medicalText.setText(medicalCategory.getName());
        // TODO picasso
        // holder.medicalImage
        holder.medicalImage.setImageResource(R.drawable.icon_2_medical_analysis);
        holder.medicalCard.setOnClickListener(view -> itemClick.onClick(position));

    }

    @Override
    public int getItemCount() {
        return medicalList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MedicalCategory> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchList);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (MedicalCategory category : searchList) {
                    if (category.getName().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(category);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            medicalList.clear();
            medicalList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface ItemClick {

        void onClick(int position);
    }
}