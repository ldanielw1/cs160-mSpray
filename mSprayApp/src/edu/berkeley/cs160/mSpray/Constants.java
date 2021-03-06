package edu.berkeley.cs160.mSpray;

import android.graphics.Typeface;

public final class Constants {
    /**
     * Fonts - TYPEFACE can't be final, since it has to get assigned on runtime.
     */
    public static final String FONT_PATH = "fonts/life.ttf";
    public static Typeface TYPEFACE;

    /** Constants for Mop-Up Spraying. */
    public static final String MOPUP_SPRAY_TITLE_PREFIX = "Mop-Up Spray - ";

    /** Constants for GPS. */
    public static final int GPS_FOUND = 0;
    public static final int GPS_NOT_FOUND = 1;
    public static final float GPS_ACCURACY_LIMIT = 50f;
    public static final int GPS_NOT_ACCURATE_ENOUGH = 6;
    public static final int GPS_RETRY = 7;
    public static final float GPS_BUMB_DISTANCE = 10f;

    /** Constants for Upload Status. */
    public static final String UPLOAD_STATUS = "uploadStatus";
    public static final int UPLOAD_SUCCESSFUL = 2;
    public static final int UPLOAD_UNSUCCESSFUL = 3;

    /** Constants for Time Bomb. */
    public static final int TIME_BOMB_SWITH = 4;

    /** Constants for how many forms to fill out. */
    public static final String NUM_SPRAYERS = "numberOfSprayers";
    public static final String FORM_NUMBER = "formNumber";

    /** Constants for which forms to fill out. */
    public static final String SPRAY_TYPE = "sprayType";
    public static final String DDT = "DDT";
    public static final String FENDONA = "Fendona";
    public static final String KORTHRINE = "K-Orthrine";
    public static final String NO_SPRAY = "No Spray";

    /** Constants for passing information into verification page. */
    public static final String ROOMS_SPRAYED = "roomsSprayed";
    public static final String SHELTERS_SPRAYED = "sheltersSprayed";
    public static final String ROOMS_UNSPRAYED = "roomsUnsprayed";
    public static final String SHELTERS_UNSPRAYED = "sheltersUnsprayed";
    public static final String CAN_REFILLED = "canRefilled";
    public static final String REASON_UNSPRAYED = "reasonUnsprayed";

    /**
     * Constant for refreshing the application. Calcuation:
     * hours*minutes*seconds*millis;
     */
    public static final double SESSION_TIMEOUT = 6 * 60 * 60 * 1000;

    public static final String RFID_NAME = "rfid name";
    public static final String DOESNT_HAVE_RFID = "not identified";

    public static final String SCAN_SPRAYER = "Scan Sprayer";

    public static final String RESCAN_FORMAN = "Rescan Foreman";

    /** Constant for authorizing Google Spreadsheet uploading. */
    public static final String AUTHENTICATION_FILE = "assets/authentication.txt";

}
