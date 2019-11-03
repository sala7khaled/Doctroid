package customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


import static app.Constants.FONT_GOTHAM_BOOK;

@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);
        init();
    }
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), FONT_GOTHAM_BOOK);
        setTypeface(font);

    }
}
