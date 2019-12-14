package presenter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

public class MedicineHolder extends RecyclerView.ViewHolder {

    public ImageView deleteMedicineImageView;
    public TextView nameMedicineTextView;

    public MedicineHolder(View view) {
        super(view);
        deleteMedicineImageView = view.findViewById(R.id.medicine_delete_imageView);
        nameMedicineTextView = view.findViewById(R.id.medicine_name_textView);
    }
}