package presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.List;

import app.App;
import network.model.Appoint;
import network.model.MedicalAnalysis;
import presenter.holder.AppointHolder;
import presenter.holder.MedicalAnalysisHolder;

public class AppointAdapter extends RecyclerView.Adapter<AppointHolder> implements Filterable {

    private List<Appoint> AppointList;
    private List<Appoint> searchList;
    private Context context;
    private ItemClick itemClick;

    public AppointAdapter(Context context, List<Appoint> items, ItemClick itemClick) {

        this.context = context;
        this.AppointList = items;
        this.itemClick = itemClick;
        searchList = new ArrayList<>(AppointList);
    }

    public void addItem(Appoint appoint) {
        AppointList.add(appoint);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        AppointList.remove(position);
        notifyDataSetChanged();
    }

    private Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public AppointHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appoint, parent, false);
        return new AppointHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointHolder holder, int position) {

        Appoint appoint = AppointList.get(position);

        holder.appointTitle.setText(appoint.getTitle());
        holder.precautions_en.setText(appoint.getPre_en());
        holder.precautions_ar.setText(appoint.getPre_ar());
        holder.appointDate.setText(appoint.getDate());
        holder.appointTime.setText(appoint.getTime());
        holder.comment.setText(appoint.getComment());

        if (appoint.getStatus().toLowerCase().equals("rejected")) {
            if (appoint.getNotes().toLowerCase().equals("empty")){
                holder.appointNote.setText(getContext().getResources().getString(R.string.Rejected));
            }
            else
            {
                holder.appointNote.setText(appoint.getNotes());
            }
            holder.appointStatus.setImageDrawable(getContext().getDrawable(R.drawable.icon_rejected));
            holder.constraint.setBackgroundColor(getContext().getResources().getColor(R.color.colorRed));
            holder.appointTime.setTextColor(getContext().getResources().getColor(R.color.colorRed));
            holder.appointDate.setTextColor(getContext().getResources().getColor(R.color.colorRed));
            holder.appointDelete.setVisibility(View.VISIBLE);
        } else if (appoint.getStatus().toLowerCase().equals("accepted")) {
            if (appoint.getNotes().toLowerCase().equals("empty")){
                holder.appointNote.setText(getContext().getResources().getString(R.string.Accepted));
            }
            else
            {
                holder.appointNote.setText(appoint.getNotes());
            }
            holder.appointStatus.setImageDrawable(getContext().getDrawable(R.drawable.icon_accepted));
            holder.constraint.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            holder.appointTime.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
            holder.appointDate.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
            holder.appointDelete.setVisibility(View.GONE);
        } else {
            if (appoint.getNotes().toLowerCase().equals("empty")){
                holder.appointNote.setText(getContext().getResources().getString(R.string.Pending));
            }
            else
            {
                holder.appointNote.setText(appoint.getNotes());
            }
            holder.appointStatus.setImageDrawable(getContext().getDrawable(R.drawable.icon_loading));
            holder.constraint.setBackgroundColor(getContext().getResources().getColor(R.color.colorGray));
            holder.appointTime.setTextColor(getContext().getResources().getColor(R.color.colorGray));
            holder.appointDate.setTextColor(getContext().getResources().getColor(R.color.colorGray));
            holder.appointDelete.setVisibility(View.VISIBLE);
        }

//        holder.questions_drop.setOnClickListener(view ->
//        {
//            if (holder.questions.getVisibility() == View.GONE) {
//                holder.questions.setVisibility(View.VISIBLE);
//                holder.drop.setImageDrawable(getContext().getDrawable(R.drawable.icon_arrow_drop_down));
//            } else {
//                holder.questions.setVisibility(View.GONE);
//                holder.drop.setImageDrawable(getContext().getDrawable(R.drawable.icon_arrow_drop_down_gray));
//            }
//        });
        holder.appointDelete.setOnClickListener(view -> itemClick.onClick(position));

    }

    @Override
    public int getItemCount() {
        return AppointList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Appoint> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchList);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (Appoint appoint : searchList) {
                    if (appoint.getTitle().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(appoint);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            AppointList.clear();
            AppointList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface ItemClick {

        void onClick(int position);
    }
}