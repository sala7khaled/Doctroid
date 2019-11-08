package engine;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.Constants;
import helpers.AppTypefaceSpan;

public class UIEngine {

    public static void initialize(Context context) {
        Fonts.APP_FONT_BOOK = Typeface.createFromAsset(context.getAssets(), Constants.FONT_GOTHAM_BOOK);
        Fonts.APP_FONT_LIGHT = Typeface.createFromAsset(context.getAssets(), Constants.FONT_GOTHAM_LIGHT);
        Fonts.APP_FONT_MEDIUM = Typeface.createFromAsset(context.getAssets(), Constants.FONT_GOTHAM_MEDIUM);
        Fonts.APP_FONT_BOOK_ITALIC = Typeface.createFromAsset(context.getAssets(), Constants.FONT_GOTHAM_BOOK_ITALIC);
        Fonts.FONT_BREESERIF_REGULAR = Typeface.createFromAsset(context.getAssets(),Constants.FONT_BREESERIF_REGULAR);
        Fonts.FONT_PACIFICO_REGULAR = Typeface.createFromAsset(context.getAssets(),Constants.FONT_PACIFICO_REGULAR);
    }

    public static class Fonts {

        public static Typeface APP_FONT_BOOK;
        public static Typeface APP_FONT_LIGHT;
        public static Typeface APP_FONT_MEDIUM;
        public static Typeface APP_FONT_BOOK_ITALIC;
        public static Typeface FONT_BREESERIF_REGULAR;
        public static Typeface FONT_PACIFICO_REGULAR;
    }

    public static void applyCustomFont(View topView, Typeface typeface) {
        if (topView instanceof ViewGroup) {
            final int len = ((ViewGroup) topView).getChildCount();
            processViewGroup(((ViewGroup) topView), len, typeface);
        } else if (topView instanceof TextView) {
            applyCustomFont(topView, typeface);
        }
    }

    private static void processViewGroup(ViewGroup v, final int len, Typeface typeface) {
        for (int i = 0; i < len; i++) {
            final View c = v.getChildAt(i);
            if (c instanceof TextView) {
                applyCustomFont(c, typeface);
            } else if (c instanceof ViewGroup) {
                applyCustomFont(c, typeface);
            }
        }
    }

    public static SpannableString createSpannableString(String text, Typeface typeface) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AppTypefaceSpan("", typeface), 0, spannableString.length(), 0);
        return spannableString;
    }
}