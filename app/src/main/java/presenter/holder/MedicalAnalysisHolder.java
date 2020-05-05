package presenter.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

public class MedicalAnalysisHolder extends RecyclerView.ViewHolder {

    public TextView medicalAnalysisTitle, medicalAnalysisPrice, medicalAnalysisPeriod;

    public LinearLayout medicalAnalysisLinear;
    public ImageView medicalAnalysisDropArrow;
    public CardView medicalAnalysisCardView;
    public Button medicalAnalysisAppoint;
    public Group medicalAnalysisInfoGroup;


    public MedicalAnalysisHolder(View view) {
        super(view);
        medicalAnalysisTitle = view.findViewById(R.id.item_medicalAnalysis_title);
        medicalAnalysisPrice = view.findViewById(R.id.item_medicalAnalysis_price);
        medicalAnalysisPeriod = view.findViewById(R.id.item_medicalAnalysis_period);

        medicalAnalysisLinear = view.findViewById(R.id.item_medicalAnalysis_linearLayout);
        medicalAnalysisDropArrow = view.findViewById(R.id.item_medicalAnalysis_dropArrow);
        medicalAnalysisCardView = view.findViewById(R.id.item_medicalAnalysis_cardView);
        medicalAnalysisAppoint = view.findViewById(R.id.item_medicalAnalysis_appoint_button);
        medicalAnalysisInfoGroup = view.findViewById(R.id.item_medicalAnalysis_info_group);
    }
}