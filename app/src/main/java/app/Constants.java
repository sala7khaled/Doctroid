package app;

import android.Manifest;

public class Constants {

    /**
     * -
     * - API
     * -
     */
    public static final String BASE_URL = "https://lms-doctoroid.herokuapp.com/";

    /**
     * GET
     */
    public static final String SERVICES_GET_HOSPITAL = "hospitalinfo";
    public static final String SERVICES_GET_MEDICAL_CATEGORY = "listcategories";
    public static final String SERVICES_GET_MEDICINE = "getmedicines";
    public static final String SERVICES_GET_REQUESTS = "getreq";
    public static final String SERVICES_GET_PRECAUTIONS = "getprecautions";

    /**
     * POST
     */
    public static final String SERVICES_POST_SIGNIN = "signin";
    public static final String SERVICES_POST_SIGNUP = "signup";
    public static final String SERVICES_POST_SIGNUP_CONFIRM = "confirmsignup";
    public static final String SERVICES_POST_USER_PROFILE = "getpatient";
    public static final String SERVICES_POST_REQUEST = "addreq";
    public static final String SERVICES_POST_PATIENT_REQUEST = "patientreq";
    public static final String SERVICES_POST_DELETE_REQUEST = "delrequest";

    /**
     * -
     * - KEY
     * -
     */
    public static final String KEY_TOKEN = "keyToken";
    public static final String KEY_CONFIRM = "keyConfirm";
    public static final String KEY_P_ID = "keyP_id";

    public static final String INTENT_ID = "intentId";
    public static final String INTENT_KEY = "intentKey";
    public static final String INTENT_NAME = "intentName";
    public static final String INTENT_LOCALE = "intentLocale";
    public static final String INTENT_OBJECT = "intentObject";

    /**
     * -
     * - PERMISSION
     * -
     */
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    /**
     * -
     * - FONT
     * -
     */
    public static final String FONT_GOTHAM_BOOK = "gotham_rounded_book.otf";
    public static final String FONT_GOTHAM_LIGHT = "gotham_rounded_light.otf";
    public static final String FONT_GOTHAM_MEDIUM = "gotham_rounded_medium.otf";
    public static final String FONT_GOTHAM_BOOK_ITALIC = "gotham_rounded_book_italic.otf";
    public static final String FONT_BREESERIF_REGULAR = "breeserif_regular.otf";
    public static final String FONT_PACIFICO_REGULAR = "pacifico_regular.otf";

    /**
     * -
     * - TIMING
     * -
     */
    public static final int SPLASH_TIME_OUT = 3000;

}
