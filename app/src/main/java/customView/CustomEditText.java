package customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import static app.Constants.FONT_GOTHAM_BOOK;

@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {

    public CustomEditText(Context context) {
        super(context);
        init();
    }
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public void init(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), FONT_GOTHAM_BOOK);
        setTypeface(font);
    }
}
