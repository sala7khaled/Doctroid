package presenter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import presenter.adapter.MedicineType;

public class MedicalCategoryHolder extends RecyclerView.ViewHolder {

    public ImageView medicalImage;
    public TextView medicalText;
    public CardView medicalCard;

    public MedicalCategoryHolder(View view) {
        super(view);

        medicalImage = view.findViewById(R.id.medicalCategory_imageView);
        medicalText = view.findViewById(R.id.medicalCategory_textView);
        medicalCard = view.findViewById(R.id.medicalCategory_cardView);
    }
}