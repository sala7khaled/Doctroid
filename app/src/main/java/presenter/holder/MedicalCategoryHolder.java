package presenter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import presenter.adapter.MedicineType;

public class MedicalCategoryHolder extends RecyclerView.ViewHolder {

    public ImageView medicalImage;
    public TextView medicalText, medicalDesc;
    public CardView medicalCard;
    public LinearLayout medicalInfo;
    public ConstraintLayout medicalConstraint;

    public MedicalCategoryHolder(View view) {
        super(view);

        medicalImage = view.findViewById(R.id.medicalCategory_imageView);
        medicalText = view.findViewById(R.id.medicalCategory_textView);
        medicalCard = view.findViewById(R.id.medicalCategory_cardView);
        medicalInfo = view.findViewById(R.id.medicalCategory_info_icon);
        medicalDesc = view.findViewById(R.id.medicalCategory_desc_textView);
        medicalConstraint = view.findViewById(R.id.medicalCategory_desc_constraintLayout);
    }
}