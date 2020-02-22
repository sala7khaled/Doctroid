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

    public TextView appointTitle;
    public TextView appointQuestion1_answer;
    public TextView appointQuestion2_answer;
    public TextView appointQuestion3_answer;
    public TextView appointDate;
    public TextView appointTime;
    public TextView appointNote;

    public LinearLayout questions_drop;
    public LinearLayout questions;
    public ConstraintLayout constraint;
    public ImageView appointStatus;

    public ImageView drop;

    public AppointHolder(View view) {
        super(view);

        appointTitle = view.findViewById(R.id.item_appoint_title);
        appointQuestion1_answer = view.findViewById(R.id.item_appoint_question1_answer);
        appointQuestion2_answer = view.findViewById(R.id.item_appoint_question2_answer);
        appointQuestion3_answer = view.findViewById(R.id.item_appoint_question3_answer);
        appointDate = view.findViewById(R.id.item_appoint_date);
        appointTime = view.findViewById(R.id.item_appoint_time);
        appointStatus = view.findViewById(R.id.item_appoint_icon);
        appointNote = view.findViewById(R.id.item_appoint_note);
        drop = view.findViewById(R.id.item_appoint_drop);

        questions_drop = view.findViewById(R.id.item_appoint_questions_drop);
        questions = view.findViewById(R.id.item_appoint_questions);
        constraint = view.findViewById(R.id.item_appoint_constraint);
    }
}