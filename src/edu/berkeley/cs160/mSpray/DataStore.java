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
    public static Boolean ddtUsed1 = false;
    public static int ddtSprayedRooms1 = -1;
    public static int ddtSprayedShelters1 = -1;
    public static Boolean ddtRefill1 = false;

    public static Boolean pyrethroidUsed1 = false;
    public static int pyrethroidSprayedRooms1 = -1;
    public static int pyrethroidSprayedShelters1 = -1;
    public static Boolean pyrethroidRefill1 = false;

    public static Boolean ddtUsed2 = false;
    public static int ddtSprayedRooms2 = -1;
    public static int ddtSprayedShelters2 = -1;
    public static Boolean ddtRefill2 = false;

    public static Boolean pyrethroidUsed2 = false;
    public static int pyrethroidSprayedRooms2 = -1;
    public static int pyrethroidSprayedShelters2 = -1;
    public static Boolean pyrethroidRefill2 = false;

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

        pyrethroidUsed1 = false;
        pyrethroidSprayedRooms1 = -1;
        pyrethroidSprayedShelters1 = -1;
        pyrethroidRefill1 = false;

        ddtUsed2 = false;
        ddtSprayedRooms2 = -1;
        ddtSprayedShelters2 = -1;
        ddtRefill2 = false;

        pyrethroidUsed2 = false;
        pyrethroidSprayedRooms2 = -1;
        pyrethroidSprayedShelters2 = -1;
        pyrethroidRefill2 = false;
    }

}
