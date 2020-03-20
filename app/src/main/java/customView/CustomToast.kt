package customView

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.s7k.doctroid.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToast.Companion.GRAVITY_BOTTOM
import www.sanju.motiontoast.MotionToast.Companion.LONG_DURATION
import www.sanju.motiontoast.MotionToast.Companion.TOAST_SUCCESS

class CustomToast {

    companion object {
        fun dark(context: Context?, message: String?) {
            MotionToast.darkColorToast(context as Activity, "msg",
                    TOAST_SUCCESS,
                    GRAVITY_BOTTOM,
                    LONG_DURATION,
                    ResourcesCompat.getFont(context, R.font.gotham_rounded_medium))
        }
    }

}