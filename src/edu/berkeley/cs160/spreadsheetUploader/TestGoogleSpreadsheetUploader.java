package edu.berkeley.cs160.spreadsheetUploader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;

import com.google.gdata.util.ServiceException;

/**
 * Testing harness for GoogleSpreadsheetUploader
 * 
 * @author Lemuel Daniel Wu
 */
public final class TestGoogleSpreadsheetUploader {
    /** Spreadsheet Authentication Data */
    private static final String USERNAME = "mSprayApp";
    private static final String PASSWORD = "mSprayApp1.0";
    private static final String SPREADSHEET_TITLE = "mSpray v1.5 Results";
    private static final String WORKSHEET_TITLE = "Sheet1";

    /** Spreadsheet Contents */
    private static final String uploadStringTimePrefix = "timeStamp=";
    private static final String uploadStringNoDate = "||imei=354352053090871||lat=37.88687||latNS=N||lng=122.297747||lngEW=W||accuracy=95000||homesteadSprayed=true||sprayerID=TESTGOOGLESPREADSHEETUPLOADER||DDTUsed1=true||DDTSprayedRooms1=2||DDTSprayedShelters1=3||DDTRefill1=true||pyrethroidUsed1=true||pyrethroidSprayedRooms1=2||pyrethroidSprayedShelters1=3||pyrethroidRefill1=true||sprayer2ID=TESTGOOGLESPREADSHEETUPLOAD||DDTUsed2=true||DDTSprayedRooms2=5||DDTSprayedShelters2=6||DDTRefill2=false||pyrethroidUsed2=true||pyrethroidSprayedRooms2=7||pyrethroidSprayedShelters2=8||pyrethroidRefill2=true||unsprayedRooms=9||unsprayedShelters=0||foreman=TESTGOOGLESPREADSHEETUPLOAD";
    private static final String replaceStringNoDate = "||imei=354352053090871||lat=37.88687||latNS=N||lng=122.297747||lngEW=W||accuracy=95000||homesteadSprayed=true||sprayerID=REPLACETESTGOOGLESPREADSHEETUPLOADER||DDTUsed1=true||DDTSprayedRooms1=2||DDTSprayedShelters1=3||DDTRefill1=true||pyrethroidUsed1=true||pyrethroidSprayedRooms1=2||pyrethroidSprayedShelters1=3||pyrethroidRefill1=true||sprayer2ID=REPLACETESTGOOGLESPREADSHEETUPLOADER||DDTUsed2=true||DDTSprayedRooms2=5||DDTSprayedShelters2=6||DDTRefill2=false||pyrethroidUsed2=true||pyrethroidSprayedRooms2=7||pyrethroidSprayedShelters2=8||pyrethroidRefill2=true||unsprayedRooms=9||unsprayedShelters=0||foreman=REPLACETESTGOOGLESPREADSHEETUPLOADER";

    /** For test edit */
    private String formattedDate = null;

    private TestGoogleSpreadsheetUploader() {
        // do nothing. This enforces static calls to test methods
    }

    public static void main(String[] args) {
        selfTest();
    }

    public static void selfTest() {
        System.out.println("===============================================");
        System.out.println("Starting test for GoogleSpreadsheetUploader");
        System.out.println("===============================================\n");
        TestGoogleSpreadsheetUploader test = new TestGoogleSpreadsheetUploader();
        test.testUpload();
        System.out.println();
        test.testContains();
        System.out.println();
        test.testEdit();
    }

    private void testUpload() {
        System.out.println("Uploading String to Google spreadsheet");
        GoogleSpreadsheetUploader exporter = null;
        try {
            exporter = new GoogleSpreadsheetUploader(USERNAME, PASSWORD, SPREADSHEET_TITLE,
                    WORKSHEET_TITLE);
            HashMap<String, String> parseResults = exporter.parse(generateUploadString());
            exporter.addRow(parseResults, null);

            System.out.println("Upload: Success!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void testContains() {
        System.out.println("Checking if upload is in the Google spreadsheet");
        GoogleSpreadsheetUploader exporter = null;
        try {
            exporter = new GoogleSpreadsheetUploader(USERNAME, PASSWORD, SPREADSHEET_TITLE,
                    WORKSHEET_TITLE);
            HashMap<String, String> parseResults = exporter.parse(generateEditString());

            List<String> requiredMatches = new ArrayList<String>();
            requiredMatches.add("timeStamp");
            requiredMatches.add("imei");

            boolean success = exporter.editRow(requiredMatches, parseResults);
            if (success)
                System.out.println("Contains: Success!");
            else
                System.out.println("Contains: Failure.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void testEdit() {
        System.out.println("Editting recent upload in the Google spreadsheet");
        GoogleSpreadsheetUploader exporter = null;
        try {
            exporter = new GoogleSpreadsheetUploader(USERNAME, PASSWORD, SPREADSHEET_TITLE,
                    WORKSHEET_TITLE);
            HashMap<String, String> parseResults = exporter.parse(generateEditString());

            List<String> requiredMatches = new ArrayList<String>();
            requiredMatches.add("timeStamp");
            requiredMatches.add("imei");

            boolean success = exporter.editRow(requiredMatches, parseResults);
            if (success)
                System.out.println("Edit: Success!");
            else
                System.out.println("Contains: Failure.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String generateUploadString() {
        formattedDate = formatDateTime();
        return uploadStringTimePrefix + formattedDate + uploadStringNoDate;
    }

    private String generateEditString() {
        return uploadStringTimePrefix + formattedDate + replaceStringNoDate;
    }

    @SuppressLint("SimpleDateFormat")
    private static String formatDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d = new Date(System.currentTimeMillis());
        String[] formattedDateArray = df.format(d).split(" ");

        String[] splitDate = formattedDateArray[0].split("/");
        int month = Integer.parseInt(splitDate[0]);
        int day = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        return month + "/" + day + "/" + year + " " + formattedDateArray[1];
    }
}
