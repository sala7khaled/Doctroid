package customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import static app.Constants.FONT_GOTHAM_BOOK;

@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        init();
    }
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), FONT_GOTHAM_BOOK);
        setTypeface(font);
    }
}
