package edu.berkeley.cs160.spreadsheetUploader;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;
import bouncedemo.network.BuildRESTRequest;
import bouncedemo.util.Constants;
import bouncedemo.util.State;

import com.example.bouncedemo.MaaPVieew;
import com.example.bouncedemo.MaaPVieew.AsyncTaskAPI;
import com.example.bouncedemo.MaaPVieew.MyOverlay;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

import edu.berkeley.cs160.mSpray.DataStore;
import edu.berkeley.cs160.mSpray.FinishedActivity;

/**
 * A class that provides the interface for uploading data onto a Google
 * Spreadsheet after being provided with a username and password
 * 
 * @author Lemuel Daniel Wu
 */
public class GoogleSpreadsheetUploader {
	private static final String SPREADSHEET_FEED = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	private Context contxt;
	private SpreadsheetService service;
	private SpreadsheetEntry spreadsheet;
	private WorksheetEntry worksheet;

	/**
	 * Constructor for the Google Spreadsheet Uploader. Sets up the service and
	 * saves important variables for faster future uploads.
	 * 
	 * @param username
	 *            - The username for logging in to Google Docs.
	 * @param password
	 *            - The password for logging in to Google Docs.
	 * @param spreadsheetTitle
	 *            - The title of Google Doc spreadsheet.
	 * @param worksheetTitle
	 *            - The title of the worksheet in the Google Doc. spreadsheet.
	 * 
	 * @throws IOException
	 * @throws ServiceException
	 */
	public GoogleSpreadsheetUploader(String username, String password,
			String spreadsheetTitle, String worksheetTitle) throws IOException,
			ServiceException {
		/** Setting up the login */
		service = new SpreadsheetService("GoogleSpreadsheetUploader");
		this.service.setUserCredentials(username, password);

		this.spreadsheet = getSpreadsheet(service, SPREADSHEET_FEED,
				spreadsheetTitle);
		this.worksheet = getWorksheet(service, spreadsheet, worksheetTitle);
	}

	/**
	 * Not sure if this should be here - parses a string of key-value pairs
	 * (connected by a '=') delimited by "||"s and converts that into a HashMap.
	 * 
	 * @param dataString
	 *            - The string to convert into a HashMap.
	 * @return The HashMap of key-value pairs.
	 */
	public HashMap<String, String> parse(String dataString) {
		// TODO migrate this method to a more fitting class
		HashMap<String, String> parseResults = new HashMap<String, String>();
		String[] splitData = dataString.split("\\|\\|");
		for (String dataPair : splitData) {
			String[] splitDataPair = dataPair.split("=", 2);
			parseResults.put(splitDataPair[0], splitDataPair[1]);
		}
		return parseResults;
	}

	/**
	 * Take the data for one row, and add it to the bottom of the spreadsheet.
	 * 
	 * @param uploadData
	 *            - the HashMap of data to go inside the new row.
	 */
	public void addRow(HashMap<String, String> uploadData) {
		try {
			/** Pre-process the worksheet to make sure inserting works. */
			worksheet.setRowCount(worksheet.getRowCount() + 1);
			worksheet.update();

			// Create the new row locally
			ListEntry row = new ListEntry();
			// error out only if dataLabels size doesn't match dataValues -

			for (String key : uploadData.keySet())
				row.getCustomElements().setValueLocal(key, uploadData.get(key));

			addRow(row);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes a ListEntry object (a row in a Google Spreadsheet), and stick it on
	 * the bottom of the spreadsheet.
	 * 
	 * @param row
	 *            - The row to be added.
	 * @throws IOException
	 * @throws ServiceException
	 */
	private void addRow(ListEntry row) throws IOException, ServiceException {
		row = service.insert(worksheet.getListFeedUrl(), row);
	}

	/**
	 * Edits (replaces) the first row whose fields in the requiredMatches list
	 * have values matching the values for the same fields in the uploadData
	 * HashMap.
	 * 
	 * @param requiredMatches
	 *            - the fields whose data should match uploadData's.
	 * @param uploadData
	 *            - the data we want to appear in the editted row.
	 */
	public boolean containsRow(List<String> requiredMatches,
			HashMap<String, String> uploadData) {
		try {
			URL listFeedUrl = worksheet.getListFeedUrl();
			ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

			for (ListEntry nextRow : listFeed.getEntries()) {
				boolean match = true;
				for (String label : requiredMatches)
					match &= uploadData.get(label).equals(
							nextRow.getCustomElements().getValue(label));
				if (match) {
					return true;
				}
			}

			return false;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Edits (replaces) the first row whose fields in the requiredMatches list
	 * have values matching the values for the same fields in the uploadData
	 * HashMap.
	 * 
	 * @param requiredMatches
	 *            - The fields whose data should match uploadData's.
	 * @param uploadData
	 *            - The data we want to appear in the editted row.
	 */
	public boolean editRow(List<String> requiredMatches,
			HashMap<String, String> uploadData) {
		try {
			URL listFeedUrl = worksheet.getListFeedUrl();
			ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

			ListEntry row = null;
			for (ListEntry nextRow : listFeed.getEntries()) {
				boolean match = true;
				for (String label : requiredMatches)
					match &= uploadData.get(label).equals(
							nextRow.getCustomElements().getValue(label));
				if (match) {
					row = nextRow;
					for (String key : uploadData.keySet())
						row.getCustomElements().setValueLocal(key,
								uploadData.get(key));
					break;
				}
			}

			/** Push row to server */
			if (row == null)
				return false;
			row.update();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Gets the spreadsheet associated with a certain title.
	 * 
	 * @param spreadsheetService
	 *            - The service for manipulating a spreadsheet.
	 * @param spreadsheetFeed
	 *            - The permissions used on the spreadsheet.
	 * @param spreadsheetTitle
	 *            - The title of the spreadsheet
	 * 
	 * @return The object representing the designated spreadsheet.
	 * 
	 * @throws IOException
	 * @throws ServiceException
	 */
	private static SpreadsheetEntry getSpreadsheet(
			SpreadsheetService spreadsheetService, String spreadsheetFeed,
			String spreadsheetTitle) throws IOException, ServiceException {
		/** Make a request to the API and get all spreadsheets. */
		// Define the URL to request. This should never change.
		SpreadsheetFeed feed = spreadsheetService.getFeed(new URL(
				spreadsheetFeed), SpreadsheetFeed.class);
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

	/**
	 * Gets the worksheet associated with a certain title from a predetermined
	 * spreadsheet.
	 * 
	 * @param spreadsheetService
	 *            - The service for manipulating a spreadsheet.
	 * @param spreadsheetEntry
	 *            - The object representing the spreadsheet.
	 * @param worksheetTitle
	 *            - The title of the worksheet.
	 * 
	 * @return The object representing the designated worksheet.
	 * 
	 * @throws IOException
	 * @throws ServiceException
	 */
	private static WorksheetEntry getWorksheet(SpreadsheetService service,
			SpreadsheetEntry spreadsheetEntry, String worksheetTitle)
			throws IOException, ServiceException {
		WorksheetFeed worksheetFeed = service.getFeed(
				spreadsheetEntry.getWorksheetFeedUrl(), WorksheetFeed.class);
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		for (WorksheetEntry worksheet : worksheets) {
			if (worksheet.getTitle().getPlainText().equals(worksheetTitle))
				return worksheet;
		}
		return null;
	}

	private void pushData(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final HashMap<String, String> uploadData = new HashMap<String, String>();
		uploadData.put("timeStamp", formatDateTime());
		uploadData.put("imei", tm.getDeviceId());
		uploadData.put("lat", DataStore.lat);
		uploadData.put("latNS", DataStore.latNS);
		uploadData.put("lng", DataStore.lng);
		uploadData.put("lngEW", DataStore.lngEW);
		uploadData.put("accuracy", DataStore.accuracy);
		uploadData.put("homesteadSprayed", Boolean.toString(true));
		uploadData.put("sprayerID", "TESTGOOGLESPREADSHEETUPLOADER");
		if (DataStore.ddtUsed1) {
			uploadData.put("pyrethroidUsed1", Boolean.toString(false));
			uploadData.put("pyrethroidUsed2", Boolean.toString(false));
			uploadData.put("DDTUsed1", Boolean.toString(true));
			uploadData.put("DDTSprayedRooms1",
					Integer.toString(DataStore.ddtSprayedRooms1));
			uploadData.put("DDTSprayedShelters1",
					Integer.toString(DataStore.ddtSprayedShelters1));
			uploadData
					.put("DDTRefill1", Boolean.toString(DataStore.ddtRefill1));
			uploadData.put("DDTUsed2", Boolean.toString(DataStore.ddtUsed2));
			if (DataStore.ddtUsed2) {
				uploadData.put("sprayer2ID", "TESTGOOGLESPREADSHEETUPLOADER");
				uploadData.put("DDTSprayedRooms2",
						Integer.toString(DataStore.ddtSprayedRooms2));
				uploadData.put("DDTSprayedShelters2",
						Integer.toString(DataStore.ddtSprayedShelters2));
				uploadData.put("DDTRefill2",
						Boolean.toString(DataStore.ddtRefill2));
			}
		} else if (DataStore.pyrethroidUsed1) {
			uploadData.put("DDTUsed1", Boolean.toString(false));
			uploadData.put("DDTUsed2", Boolean.toString(false));
			uploadData.put("pyrethroidUsed1", Boolean.toString(true));
			uploadData.put("pyrethroidSprayedRooms1",
					Integer.toString(DataStore.pyrethroidSprayedRooms1));
			uploadData.put("pyrethroidSprayedShelters1",
					Integer.toString(DataStore.pyrethroidSprayedShelters1));
			uploadData.put("pyrethroidRefill1",
					Boolean.toString(DataStore.pyrethroidRefill1));
			uploadData.put("pyrethroidUsed2",
					Boolean.toString(DataStore.pyrethroidUsed2));
			if (DataStore.pyrethroidUsed2) {
				uploadData.put("sprayer2ID", "TESTGOOGLESPREADSHEETUPLOADER");
				uploadData.put("pyrethroidSprayedRooms2",
						Integer.toString(DataStore.pyrethroidSprayedRooms2));
				uploadData.put("pyrethroidSprayedShelters2",
						Integer.toString(DataStore.pyrethroidSprayedShelters2));
				uploadData.put("pyrethroidRefill2",
						Boolean.toString(DataStore.pyrethroidRefill2));
			}
		}
		uploadData.put("unsprayedRooms", Integer.toString(DataStore.roomsUnsprayed));
		uploadData
				.put("unsprayedShelters", Integer.toString(DataStore.sheltersUnsprayed));
		uploadData.put("foreman", "TESTGOOGLESPREADSHEETUPLOADER");

		new AsyncTaskAPI().execute(); {
		

	}
	
	private class AsyncTaskAPI extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
//			String username = null;
//			String password = null;
//			String spreadsheetTitle = null;
//			String worksheetTitle = null;
			//Scanner s;
			// s = new Scanner(getAssets().open("authentication.txt"));
			//
			// while (s.hasNextLine()) {
			// String[] nextLine = s.nextLine().split("=");
			// if (nextLine[0].equals(USERNAME_LABEL))
			// username = nextLine[1];
			// else if (nextLine[0].equals(PASSWORD_LABEL))
			// password = nextLine[1];
			// else if (nextLine[0].equals(SPREADSHEET_TITLE_LABEL))
			// spreadsheetTitle = nextLine[1];
			// else if (nextLine[0].equals(WORKSHEET_TITLE_LABEL))
			// worksheetTitle = nextLine[1];
			// else
			// throw new IllegalArgumentException(
			// "Invalid authentication file");
			// }

			// GoogleSpreadsheetUploader uploader = new
			// GoogleSpreadsheetUploader(
			// username, password, spreadsheetTitle, worksheetTitle);
			GoogleSpreadsheetUploader uploader = new GoogleSpreadsheetUploader(
					Authentication.USERNAME, Authentication.PASSWORD,
					Authentication.SPREADSHEET_TITLE,
					Authentication.WORKSHEET_TITLE);

			uploader.addRow(uploadData);

			Intent intent = new Intent(contxt,
					FinishedActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} catch (IOException e) {
			Toast.makeText(contxt,
					"Error: Could not upload data correctly",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ServiceException e) {
			Toast.makeText(contxt,
					"Service Error: Could not upload data correctly",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return null;
		}
	};
		
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
