package edu.berkeley.cs160.mSpray;

public class DataStore {
    public static String lat = null;
    public static String latNS = null;
    public static String lng = null;
    public static String lngEW = null;
    public static String accuracy = null;
    public static String sprayType = "";

    public static String sprayChemical = null;

    public static String foremanID = null;
    public static String sprayer1ID = null;
    public static String sprayer2ID = null;

    /** Constants representing upload values */
    public static Boolean homesteadSprayed = false;

    public static Boolean ddtUsed1 = false;
    public static int ddtSprayedRooms1 = -1;
    public static int ddtSprayedShelters1 = -1;
    public static Boolean ddtRefill1 = false;

    public static Boolean korthrineUsed1 = false;
    public static int korthrineSprayedRooms1 = -1;
    public static int korthrineSprayedShelters1 = -1;
    public static Boolean korthrineRefill1 = false;

    public static Boolean ddtUsed2 = false;
    public static int ddtSprayedRooms2 = -1;
    public static int ddtSprayedShelters2 = -1;
    public static Boolean ddtRefill2 = false;

    public static Boolean korthrineUsed2 = false;
    public static int korthrineSprayedRooms2 = -1;
    public static int korthrineSprayedShelters2 = -1;
    public static Boolean korthrineRefill2 = false;

    public static int roomsUnsprayed = -1;
    public static int sheltersUnsprayed = -1;

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

        ddtUsed1 = false;
        ddtSprayedRooms1 = -1;
        ddtSprayedShelters1 = -1;
        ddtRefill1 = false;

        korthrineUsed1 = false;
        korthrineSprayedRooms1 = -1;
        korthrineSprayedShelters1 = -1;
        korthrineRefill1 = false;

        ddtUsed2 = false;
        ddtSprayedRooms2 = -1;
        ddtSprayedShelters2 = -1;
        ddtRefill2 = false;

        korthrineUsed2 = false;
        korthrineSprayedRooms2 = -1;
        korthrineSprayedShelters2 = -1;
        korthrineRefill2 = false;
    }

    public static void setGPS(double la, double ln, String laNS, String lnEW, String acc) {
        lat = la + "";
        lng = ln + "";
        latNS = laNS + "";
        lngEW = lnEW + "";
        accuracy = acc;
    }
}
