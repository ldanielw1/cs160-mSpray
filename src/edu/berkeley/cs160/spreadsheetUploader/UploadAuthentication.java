package edu.berkeley.cs160.spreadsheetUploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UploadAuthentication {

    private static final String USERNAME_LABEL = "USERNAME";
    private static final String PASSWORD_LABEL = "PASSWORD";
    private static final String SPREADSHEET_TITLE_LABEL = "SPREADSHEET_TITLE";
    private static final String WORKSHEET_TITLE_LABEL = "WORKSHEET_TITLE";

    private String username;
    private String password;
    private String spreadsheetTitle;
    private String worksheetTitle;

    public static void main(String[] args) {
        UploadAuthentication a = new UploadAuthentication("assets/authentication.txt");
    }

    public UploadAuthentication(String authenticationFile) {
        try {
            File f = new File(authenticationFile);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String[] nextLine = s.nextLine().split("=");
                if (nextLine[0].equals(USERNAME_LABEL))
                    username = nextLine[1];
                else if (nextLine[0].equals(PASSWORD_LABEL))
                    password = nextLine[1];
                else if (nextLine[0].equals(SPREADSHEET_TITLE_LABEL))
                    spreadsheetTitle = nextLine[1];
                else if (nextLine[0].equals(WORKSHEET_TITLE_LABEL))
                    worksheetTitle = nextLine[1];
                else
                    throw new IllegalArgumentException("Invalid authentication file");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSpreadsheetTitle() {
        return spreadsheetTitle;
    }

    public String getWorksheetTitle() {
        return worksheetTitle;
    }
}