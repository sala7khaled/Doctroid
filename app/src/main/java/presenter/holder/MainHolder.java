package presenter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

public class MainHolder extends RecyclerView.ViewHolder {

    public ImageView categoryImageView;
    public TextView categoryTextView;
    public CardView categoryCardView;

    public MainHolder(View view) {
        super(view);
        categoryImageView = view.findViewById(R.id.item_category_imageView);
        categoryTextView = view.findViewById(R.id.item_category_textView);
        categoryCardView = view.findViewById(R.id.item_category_cardView);
    }
}