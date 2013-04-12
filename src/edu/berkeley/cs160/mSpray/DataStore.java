package edu.berkeley.cs160.mSpray;

public class DataStore {
    public static String lat = null;
    public static String latNS = null;
    public static String lng = null;
    public static String lngEW = null;
    public static String accuracy = null;

    public static String sprayChemical = null;

    public static String foremanID = null;
    public static String sprayer1ID = null;
    public static String sprayer2ID = null;

    /** Constants representing upload values */
    public static Boolean DDTUsed1 = false;
    public static int DDT_SPRAYED_ROOMS_1 = -1;
    public static int DDT_SPRAYED_SHELTERS_1 = -1;
    public static Boolean DDT_Refill_1 = false;

    public static Boolean pyrethroidUsed1 = false;
    public static int pyrethroid_SPRAYED_ROOMS_1 = -1;
    public static int pyrethroid_SPRAYED_SHELTERS_1 = -1;
    public static Boolean pyrethroid_Refill_1 = false;

    public static void startNewStoreSession() {
        lat = null;
        latNS = null;
        lng = null;
        lngEW = null;
        accuracy = null;

        sprayChemical = null;

        foremanID = null;
        sprayer1ID = null;
        sprayer2ID = null;

        DDTUsed1 = false;
        DDT_SPRAYED_ROOMS_1 = -1;
        DDT_SPRAYED_SHELTERS_1 = -1;
        DDT_Refill_1 = false;

        pyrethroidUsed1 = false;
        pyrethroid_SPRAYED_ROOMS_1 = -1;
        pyrethroid_SPRAYED_SHELTERS_1 = -1;
        pyrethroid_Refill_1 = false;
    }

}