package presenter.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import network.model.Image;

public class AppointHolder extends RecyclerView.ViewHolder {

    public LinearLayout precautionsLinearLayout;
    public TextView appointTitle;
    public TextView precautions_en;
    public TextView precautions_ar;
    public TextView appointDate;
    public TextView appointTime;
    public TextView appointNote;
    public TextView comment;

    public ConstraintLayout constraint;
    public ImageView appointStatus;
    public ImageView appointDelete;

    public AppointHolder(View view) {
        super(view);

        appointTitle = view.findViewById(R.id.item_appoint_title);
        appointDate = view.findViewById(R.id.item_appoint_date);
        appointTime = view.findViewById(R.id.item_appoint_time);
        appointStatus = view.findViewById(R.id.item_appoint_icon);
        appointDelete = view.findViewById(R.id.item_appoint_delete);
        appointNote = view.findViewById(R.id.item_appoint_note);
        precautionsLinearLayout = view.findViewById(R.id.item_appoint_precautions_linearLayout);
        precautions_en = view.findViewById(R.id.item_appoint_en_precautions_textView);
        precautions_ar = view.findViewById(R.id.item_appoint_ar_precautions_textView);
        comment = view.findViewById(R.id.item_appoint_comment_textView);

        constraint = view.findViewById(R.id.item_appoint_constraint);
    }
}