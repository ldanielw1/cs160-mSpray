package edu.berkeley.cs160.mSpray;

import android.graphics.Typeface;

public final class Constants {
    public static final String FONT_PATH = "fonts/life.ttf";
    public static Typeface TYPEFACE; // Can't be final, since it has to get
                                     // assigned on runtime

    /** Constants for Time Bomb. */
    public static final int TIME_BOMB_SWITH = 4;
    public static final int GPS_FOUND = 5;
    public static final int GPS_NOT_FOUND = 6;

    /** Constants for Upload Status. */
    public static final int UPLOAD_SUCCESSFUL = 0;
    public static final int UPLOAD_UNSUCCESSFUL = 1;

    /** Constants for how many forms to fill out. */
    public static final String NUM_SPRAYERS = "numberOfSprayers";
    public static final String FORM_NUMBER = "formNumber";

    /** Constants for which forms to fill out. */
    public static final String SPRAY_TYPE = "sprayType";
    public static final String DDT = "DDT";
    public static final String PYRETHROID = "Pyrethroid";
    public static final String NO_SPRAY = "No Spray";

    /** Constants for passing information into verification page. */
    public static final String ROOMS_SPRAYED = "roomsSprayed";
    public static final String SHELTERS_SPRAYED = "sheltersSprayed";
    public static final String ROOMS_UNSPRAYED = "roomsUnsprayed";
    public static final String SHELTERS_UNSPRAYED = "sheltersUnsprayed";
    public static final String CAN_REFILLED = "canRefilled";

    /** Constant for authorizing Google Spreadsheet uploading. */
    public static final String AUTHENTICATION_FILE = "assets/authentication.txt";

}
