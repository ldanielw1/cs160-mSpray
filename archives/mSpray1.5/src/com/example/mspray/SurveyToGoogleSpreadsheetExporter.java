package com.example.mspray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

/**
 * @author Daniel Wu A class that provides the interface for uploading data onto
 *         a Google Spreadsheet after being provided with a username and
 *         password
 */
public class SurveyToGoogleSpreadsheetExporter {
    private String spreadsheetFeed;
    private String username;
    private String password;
    private String spreadsheetTitle;
    private String[] uploadFieldsArray;
    private List<String> uploadFields;

    SurveyToGoogleSpreadsheetExporter(String spreadsheetFeed, String username,
            String password, String spreadsheetTitle, String[] uploadFieldsArray) {
        this.spreadsheetFeed = spreadsheetFeed;
        this.username = username;
        this.password = password;
        this.spreadsheetTitle = spreadsheetTitle;
        this.uploadFieldsArray = uploadFieldsArray;
        this.uploadFields = toArrayList(this.uploadFieldsArray);
    }

    public void uploadAfterParsingString(String uploadContents) {
        String[] contentsArray = uploadContents.split("\\|\\|");
        sendParsedStringToExcel(toArrayList(contentsArray));
    }

    private void sendParsedStringToExcel(List<String> uploadList) {
        try {
            /** Setting up the login */
            SpreadsheetService service = new SpreadsheetService(
                    "MySpreadsheetIntegration-v1");
            service.setUserCredentials(username, password);

            SpreadsheetEntry spreadsheet = getSpreadsheet(service);
            WorksheetEntry worksheet = getWorksheet(service, spreadsheet);

            /**
             * Pre-process the worksheet to make sure inserting is down
             * correctly.
             */
            worksheet.setRowCount(worksheet.getRowCount() + 1);
            worksheet.update();

            /** Create the new row locally */
            ListEntry row = new ListEntry();
            for (int i = 0; i < uploadFields.size(); i++) {
                if (i == uploadList.size())
                    break;
                row.getCustomElements().setValueLocal(uploadFields.get(i),
                        uploadList.get(i));
            }

            /** Push row to server */
            row = service.insert(worksheet.getListFeedUrl(), row);
        } catch (AuthenticationException ae) {
            System.err.println(ae.getMessage());
        } catch (MalformedURLException mue) {
            System.err.println(mue.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (ServiceException se) {
            System.err.println(se.getMessage());
        }
    }

    private static List<String> toArrayList(String[] array) {
        return new ArrayList<String>(Arrays.asList(array));
    }

    private SpreadsheetEntry getSpreadsheet(SpreadsheetService service)
            throws IOException, ServiceException {
        /** Make a request to the API and get all spreadsheets. */
        // Define the URL to request. This should never change.
        SpreadsheetFeed feed = service.getFeed(new URL(spreadsheetFeed),
                SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        SpreadsheetEntry spreadsheetEntry = null;
        for (SpreadsheetEntry spreadsheet : spreadsheets) {
            if (spreadsheet.getTitle().getPlainText().equals(spreadsheetTitle))
                spreadsheetEntry = spreadsheet;
        }

        if (spreadsheets.size() == 0)
            throw new IllegalStateException("No spreadsheets to upload to.");
        else if (spreadsheetEntry == null)
            throw new IllegalStateException("No spreadsheet for name: "
                    + spreadsheetTitle);

        return spreadsheetEntry;
    }

    private static WorksheetEntry getWorksheet(SpreadsheetService service,
            SpreadsheetEntry spreadsheetEntry) throws IOException,
            ServiceException {
        WorksheetFeed worksheetFeed = service.getFeed(
                spreadsheetEntry.getWorksheetFeedUrl(), WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        return worksheets.get(0);
    }

}
