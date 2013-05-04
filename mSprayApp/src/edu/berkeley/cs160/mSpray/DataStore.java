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

    public static String chemicalUsed1 = null;
    public static String chemicalUsed2 = null;

    public static int sprayedRooms1 = -1;
    public static int sprayedShelters1 = -1;
    public static Boolean canRefill1 = false;

    public static int sprayedRooms2 = -1;
    public static int sprayedShelters2 = -1;
    public static Boolean canRefill2 = false;

    public static int roomsUnsprayed = -1;
    public static int sheltersUnsprayed = -1;
    public static String reasonUnsprayed = "";
    
    public static boolean scannedFirstSprayer = false;
    public static boolean secondTimeThrough = false;

	public static int numSprayer = 0;
	public static int formNumber = 0;
	
	public static boolean doneForDay = false;
	

    /**
     * Instance from house to house
     */
    public static void startNewStoreSession() {
        lat = null;
        latNS = null;
        lng = null;
        lngEW = null;
        accuracy = null;

        sprayChemical = null;

        sprayer1ID = null;
        sprayer2ID = null;

        chemicalUsed1 = null;
        sprayedRooms1 = -1;
        sprayedShelters1 = -1;
        canRefill1 = false;

        chemicalUsed2 = null;
        sprayedRooms2 = -1;
        sprayedShelters2 = -1;
        canRefill2 = false;
        
        scannedFirstSprayer = false;
    }
    
    /**
     * instance of use between day to day so there might be a different foreman
     */
    public static void destroyAllData(){
    	startNewStoreSession();
    	foremanID = null;
    	scannedFirstSprayer = false;
    	secondTimeThrough = false;
    }

    public static void setGPS(double la, double ln, String laNS, String lnEW, String acc) {
        lat = la + "";
        lng = ln + "";
        latNS = laNS + "";
        lngEW = lnEW + "";
        accuracy = acc;
    }
}
