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
    public static String DDT_SPRAYED_ROOMS_1 = "DDTSprayedRooms1";
    public static String DDT_SPRAYED_SHELTERS_1 = "DDTSprayedShelters1";
    public static String DDT_Refill_1 = "DDTRefill1";

    public static Boolean pyrethroidUsed1 = false;
    public static String pyrethroid_SPRAYED_ROOMS_1 = "pyrethroidSprayedRooms1";
    public static String pyrethroid_SPRAYED_SHELTERS_1 = "pyrethroidSprayedShelters1";
    public static String pyrethroid_Refill_1 = "pyrethroidRefill1";

    public static Boolean DDTUsed2 = false;
    public static String DDT_SPRAYED_ROOMS_2 = "DDTSprayedRooms2";
    public static String DDT_SPRAYED_SHELTERS_2 = "DDTSprayedShelters2";
    public static String DDT_Refill_2 = "DDTRefill2";

    public static Boolean pyrethroidUsed2 = false;
    public static String pyrethroid_SPRAYED_ROOMS_2 = "pyrethroidSprayedRooms2";
    public static String pyrethroid_SPRAYED_SHELTERS_2 = "pyrethroidSprayedShelters2";
    public static String pyrethroid_Refill_2 = "pyrethroidRefill2";

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
    }

    public static String getLat() {
        return lat;
    }

    public static String getLatNS() {
        return latNS;
    }

    public static String getLng() {
        return lng;
    }

    public static String getLngEW() {
        return lngEW;
    }

    public static String getAccuracy() {
        return accuracy;
    }

    public static String getSprayChemical() {
        return sprayChemical;
    }

    public static String getForemanID() {
        return foremanID;
    }

    public static String getSprayer1ID() {
        return sprayer1ID;
    }

    public static String getSprayer2ID() {
        return sprayer2ID;
    }
}