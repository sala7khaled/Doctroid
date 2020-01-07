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
import presenter.holder.MainHolder;

public class MainAdapter extends RecyclerView.Adapter<MainHolder> {

    private ArrayList<Category> categoryList;
    private Context context;
    private ItemClick itemClick;

    public MainAdapter(ArrayList<Category> items, Context context, ItemClick itemClick) {
        this.categoryList = items;
        this.context = context;
        this.itemClick = itemClick;
    }
    public void addItem(Category category) {
        categoryList.add(category);
    }
    private Context getContext() {
        return context;
    }
    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.categoryTextView.setText(category.getName());
        holder.categoryImageView.setImageResource(category.getIcon());

        holder.categoryCardView.setOnClickListener(view -> itemClick.onClick(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface ItemClick {

        void onClick(int position);
    }
}