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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import network.model.MedicalAnalysis;
import presenter.holder.MedicalAnalysisHolder;
import presenter.holder.MedicalCategoryHolder;

public class MedicalAnalysisAdapter extends RecyclerView.Adapter<MedicalAnalysisHolder> implements Filterable {

    private List<MedicalAnalysis> medicalList;
    private List<MedicalAnalysis> searchList;
    private Context context;
    private ItemClick itemClick;

    public MedicalAnalysisAdapter(Context context, List<MedicalAnalysis> items, ItemClick itemClick) {

        this.context = context;
        this.medicalList = items;
        this.itemClick = itemClick;
        searchList = new ArrayList<>(medicalList);
    }

    public void addItem(MedicalAnalysis medicalAnalysis) {
        medicalList.add(medicalAnalysis);
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
    public MedicalAnalysisHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_analysis, parent, false);
        return new MedicalAnalysisHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalAnalysisHolder holder, int position) {

        MedicalAnalysis medicalAnalysis = medicalList.get(position);

        holder.medicalAnalysisTitle.setText(medicalAnalysis.getTitle());
        holder.medicalAnalysisPrice.setText("Price: " + medicalAnalysis.getPrice() + " LE");
        holder.medicalAnalysisPeriod.setText("Period: " + medicalAnalysis.getPeriod() + " Days");

        holder.medicalAnalysisAppoint.setOnClickListener(view ->
                itemClick.onClick(position));

        holder.medicalAnalysisLinear.setOnClickListener(view ->
        {
            if (holder.medicalAnalysisInfoGroup.getVisibility() == View.GONE)
            {
                holder.medicalAnalysisInfoGroup.setVisibility(View.VISIBLE);
                holder.medicalAnalysisDropArrow.setImageDrawable(getContext().getDrawable(R.drawable.icon_arrow_drop_down));
            }
            else
            {
                holder.medicalAnalysisInfoGroup.setVisibility(View.GONE);
                holder.medicalAnalysisDropArrow.setImageDrawable(getContext().getDrawable(R.drawable.icon_arrow_drop_down_gray));
            }
        });

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
            List<MedicalAnalysis> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchList);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (MedicalAnalysis medicalAnalysis : searchList) {
                    if (medicalAnalysis.getTitle().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(medicalAnalysis);
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