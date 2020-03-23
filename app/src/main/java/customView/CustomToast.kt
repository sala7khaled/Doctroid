package customView

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.s7k.doctroid.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToast.Companion.GRAVITY_BOTTOM
import www.sanju.motiontoast.MotionToast.Companion.LONG_DURATION

class CustomToast {
    companion object {

        const val font = R.font.gotham_rounded_medium

        fun light(context: Context?, type: CustomToastType, message: String) {
            MotionToast.createToast(context as Activity, message,
                    type.value,
                    GRAVITY_BOTTOM,
                    LONG_DURATION,
                    ResourcesCompat.getFont(context, font))
        }
        fun lightColor(context: Context?, type: CustomToastType, message: String) {
            MotionToast.createColorToast(context as Activity, message,
                    type.value,
                    GRAVITY_BOTTOM,
                    LONG_DURATION,
                    ResourcesCompat.getFont(context, font))
        }
        fun dark(context: Context?, type: CustomToastType, message: String) {
            MotionToast.darkToast(context as Activity, message,
                    type.value,
                    GRAVITY_BOTTOM,
                    LONG_DURATION,
                    ResourcesCompat.getFont(context, font))
        }
        fun darkColor(context: Context?, type: CustomToastType, message: String) {
            MotionToast.darkColorToast(context as Activity, message,
                    type.value,
                    GRAVITY_BOTTOM,
                    LONG_DURATION,
                    ResourcesCompat.getFont(context, font))
        }
    }

}