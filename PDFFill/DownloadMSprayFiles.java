import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;
import com.itextpdf.text.DocumentException;

public class DownloadMSprayFiles {
    /** Constants for extracting data from a ListEntry. */
    public static final String TIMESTAMP = "timeStamp";
    public static final String FOREMAN = "foreman";
    public static final String SPRAYER_ID = "sprayerID";
    public static final String SPRAYER_ID2 = "sprayer2ID";
    public static final String CHEMICAL_USED1 = "chemicalUsed1";
    public static final String CHEMICAL_USED2 = "chemicalUsed2";
    public static final String SPRAYED_ROOMS1 = "sprayedRooms1";
    public static final String SPRAYED_ROOMS2 = "sprayedRooms2";
    public static final String SPRAYED_SHELTERS1 = "sprayedShelters1";
    public static final String SPRAYED_SHELTERS2 = "sprayedShelters2";
    public static final String CAN_REFILLS1 = "canRefill1";
    public static final String CAN_REFILLS2 = "canRefill2";
    public static final String UNSPRAYED_ROOMS = "unsprayedRooms";
    public static final String DDT = "DDT";

    /** Maps for storing downloaded data. */
    HashMap<String, List<SprayData>> sp1Data;
    HashMap<String, HashMap<String, List<SprayData>>> sp2Data;

    public static void main(String[] args) {
        DownloadMSprayFiles downloader = new DownloadMSprayFiles();
        downloader.syncFiles();
    }

    /**
     * Driver method that first downloads all of the data from the online
     * spreadsheet, and then creates PDF documents out of that data.
     */
    public void syncFiles() {
        try {
            GoogleSpreadsheet spreadsheet = new GoogleSpreadsheet("mSprayApp", "mSprayApp1.0",
                    "mSpray 2.0 Results", "Logged Data");
            createResourceFiles();
            downloadSpreadsheetData(spreadsheet);
            generateForms();
        } catch (DocumentException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ServiceException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Sync Completed!");
    }

    public void createResourceFiles() {
        File sp1Folder = new File(SP1Fill.containingFolder);
        if (!sp1Folder.exists())
            sp1Folder.mkdirs();
        File sp2Folder = new File(SP2Fill.containingFolder);
        if (!sp2Folder.exists())
            sp2Folder.mkdirs();
        File sp3Folder = new File(SP3Fill.containingFolder);
        if (!sp3Folder.exists())
            sp3Folder.mkdirs();
    }

    public void downloadSpreadsheetData(GoogleSpreadsheet spreadsheet) {
        List<ListEntry> rows = spreadsheet.getData();
        System.out.println("Downloading SP1 form data from server...");
        downloadSP1(rows);
        System.out.println("Downloading SP2 form data from server...");
        downloadSP2(rows);
        System.out.println("Downloading SP3 form data from server...");
        downloadSP3(rows);
        System.out.println();
    }

    public void generateForms() throws IOException, DocumentException {
        System.out.println("Generating SP1 files...");
        generateSP1Forms();
        System.out.println("Generating SP2 files...");
        generateSP2Forms();
        System.out.println("Generating SP3 files...");
        generateSP3Forms();
        System.out.println();
    }

    public void generateSP1Forms() throws IOException, DocumentException {
        for (String fileName : sp1Data.keySet()) {
            String resFileName = SP1Fill.containingFolder + "/" + fileName;
            List<SprayData> rowList = sp1Data.get(fileName);
            SP1Fill formFill = new SP1Fill(resFileName + ".pdf");

            String recordedSprayer = fileName.split("_")[2];
            String chemical = fileName.split("_")[3];
            String dateString = fileName.split("_")[1];

            SprayData data = new SprayData();
            HashSet<String> foremen = new HashSet<String>();
            for (SprayData rowData : rowList) {
                String foremanName = rowData.getForeman();
                foremen.add(foremanName);
                data.update(rowData);
            }

            final StringBuilder foremenListing = new StringBuilder();
            boolean firstForeman = true;
            for (String foreman : foremen) {
                if (!firstForeman)
                    foremenListing.append(", ");
                else
                    firstForeman = false;
                foremenListing.append(foreman);
            }

            formFill.setDate(dateString);
            formFill.setForeman(foremenListing.toString());
            formFill.setChemical(chemical);
            formFill.setSprayer(recordedSprayer);
            formFill.setData(data);
            // TODO: Figure out where the following information comes from
            formFill.setFieldOfficer("Brenda Eskenazi");
            formFill.setDistrict("Vhembe");
            formFill.setLocality("Limpopo");
            formFill.close();
        }

    }

    public void generateSP2Forms() throws IOException, DocumentException {
        for (String fileName : sp2Data.keySet()) {
            String resFileName = SP2Fill.containingFolder + "/" + fileName;
            SP2Fill formFill = new SP2Fill(resFileName + ".pdf");
            HashMap<String, List<SprayData>> sprayerMap = sp2Data.get(fileName);

            SprayData totalDDTData = new SprayData();
            SprayData totalOtherData = new SprayData();
            int sprayerNumber = 1;
            for (String sprayer : sprayerMap.keySet()) {
                List<SprayData> rowList = sprayerMap.get(sprayer);
                SprayData sprayerDDTData = new SprayData();
                SprayData sprayerOtherData = new SprayData();

                for (SprayData rowData : rowList) {
                    SprayData dataToUpdate = sprayerDDTData;
                    if (!rowData.getChemical().equals(DDT))
                        dataToUpdate = sprayerOtherData;
                    dataToUpdate.update(rowData);
                }

                formFill.setSprayman(sprayerNumber, sprayer);
                formFill.setDDTData(sprayerNumber, sprayerDDTData);
                formFill.setOtherData(sprayerNumber, sprayerOtherData);
                totalDDTData.update(sprayerDDTData);
                totalOtherData.update(sprayerOtherData);

                sprayerNumber += 1;
            }

            String dateString = fileName.split("_")[1];
            formFill.setDate(dateString);
            // TODO: figure out if team should be labeled by foreman
            String foreman = fileName.split("_")[2];
            formFill.setTeam(foreman);

            formFill.setDDTData(SP2Fill.TOTAL, totalDDTData);
            formFill.setOtherData(SP2Fill.TOTAL, totalOtherData);

            // TODO: Figure out where the following information should come from
            formFill.setDistrict("Vhembe");
            formFill.setLocality("");
            formFill.setRegion("Limpopo");
            formFill.setLocality("South Africa");
            formFill.close();
        }
    }

    public void generateSP3Forms() throws IOException, DocumentException {
        System.out.println("Error: code not written for creating SP3 forms yet.");
    }

    public void downloadSP1(List<ListEntry> rows) {
        sp1Data = new HashMap<String, List<SprayData>>();
        for (ListEntry row : rows) {
            String timeStamp = stringValue(row, TIMESTAMP);
            String dateString = timeStamp.split(" ")[0];
            dateString = dateString.replaceAll("/", "-");

            String chemicalUsed1 = stringValue(row, CHEMICAL_USED1);
            if (chemicalUsed1 != null) {
                String sprayerID = stringValue(row, SPRAYER_ID);

                SprayData data = new SprayData();
                data.setForeman(stringValue(row, FOREMAN));
                data.updateSprayedRooms(intValue(row, SPRAYED_ROOMS1));
                data.updateSprayedShelters(intValue(row, SPRAYED_SHELTERS1));
                data.updateUnsprayedRooms(intValue(row, UNSPRAYED_ROOMS));
                if (getBooleanValue(row, CAN_REFILLS1))
                    data.updateCansRefilled(1);

                String userInformation = "sp1_" + dateString + "_" + sprayerID + "_"
                        + chemicalUsed1;
                List<SprayData> currentEntriesForUser = sp1Data.get(userInformation);
                if (currentEntriesForUser == null)
                    currentEntriesForUser = new ArrayList<SprayData>();
                currentEntriesForUser.add(data);
                sp1Data.put(userInformation, currentEntriesForUser);
            }
            String chemicalUsed2 = stringValue(row, CHEMICAL_USED2);
            if (chemicalUsed2 != null) {
                String sprayer2ID = stringValue(row, SPRAYER_ID2);

                SprayData data = new SprayData();
                data.setForeman(stringValue(row, FOREMAN));
                data.updateSprayedRooms(intValue(row, SPRAYED_ROOMS2));
                data.updateSprayedShelters(intValue(row, SPRAYED_SHELTERS2));
                data.updateUnsprayedRooms(intValue(row, UNSPRAYED_ROOMS));
                if (getBooleanValue(row, CAN_REFILLS2))
                    data.updateCansRefilled(1);

                String userInformation = "sp1_" + dateString + "_" + sprayer2ID + "_"
                        + chemicalUsed2;
                List<SprayData> currentEntriesForUser = sp1Data.get(userInformation);
                if (currentEntriesForUser == null)
                    currentEntriesForUser = new ArrayList<SprayData>();
                currentEntriesForUser.add(data);
                sp1Data.put(userInformation, currentEntriesForUser);
            }
        }
    }

    public void downloadSP2(List<ListEntry> rows) {
        sp2Data = new HashMap<String, HashMap<String, List<SprayData>>>();

        for (ListEntry row : rows) {
            String timeStamp = stringValue(row, TIMESTAMP);
            String dateString = timeStamp.split(" ")[0];
            dateString = dateString.replaceAll("/", "-");

            // We only need to check for the first chemical because there is a
            // spraying if and only if there is a 1st chemical
            String chemicalUsed1 = stringValue(row, CHEMICAL_USED1);
            if (chemicalUsed1 != null) {
                String foreman = stringValue(row, FOREMAN);
                String fileName = "sp2_" + dateString + "_" + foreman;
                String sprayerID = stringValue(row, SPRAYER_ID);

                HashMap<String, List<SprayData>> fileData = sp2Data.get(fileName);
                if (fileData == null)
                    fileData = new HashMap<String, List<SprayData>>();

                SprayData sprayerData1 = new SprayData();
                sprayerData1.setChemical(chemicalUsed1);
                sprayerData1.updateSprayedRooms(intValue(row, SPRAYED_ROOMS1));
                sprayerData1.updateSprayedShelters(intValue(row, SPRAYED_SHELTERS1));
                sprayerData1.updateUnsprayedRooms(intValue(row, UNSPRAYED_ROOMS));
                if (getBooleanValue(row, CAN_REFILLS1))
                    sprayerData1.updateCansRefilled(1);

                List<SprayData> sprayerDataList1 = fileData.get(sprayerID);
                if (sprayerDataList1 == null)
                    sprayerDataList1 = new ArrayList<SprayData>();
                sprayerDataList1.add(sprayerData1);
                fileData.put(sprayerID, sprayerDataList1);
                sp2Data.put(fileName, fileData);

                String chemicalUsed2 = stringValue(row, CHEMICAL_USED2);
                if (chemicalUsed2 != null) {
                    String sprayer2ID = stringValue(row, SPRAYER_ID2);

                    SprayData sprayerData2 = new SprayData();
                    sprayerData2.setChemical(chemicalUsed2);
                    sprayerData2.updateSprayedRooms(intValue(row, SPRAYED_ROOMS2));
                    sprayerData2.updateSprayedShelters(intValue(row, SPRAYED_SHELTERS2));
                    sprayerData2.updateUnsprayedRooms(intValue(row, UNSPRAYED_ROOMS));
                    if (getBooleanValue(row, CAN_REFILLS2))
                        sprayerData2.updateCansRefilled(1);

                    List<SprayData> sprayerDataList2 = fileData.get(sprayer2ID);
                    if (sprayerDataList2 == null)
                        sprayerDataList2 = new ArrayList<SprayData>();
                    sprayerDataList2.add(sprayerData2);
                    fileData.put(sprayer2ID, sprayerDataList2);

                    sp2Data.put(fileName, fileData);
                }
            }
        }
    }

    public void downloadSP3(List<ListEntry> rows) {

    }

    public String stringValue(ListEntry row, String field) {
        return row.getCustomElements().getValue(field);
    }

    public Integer intValue(ListEntry row, String field) {
        String stringValue = stringValue(row, field);
        if (stringValue == null)
            return null;
        return Integer.parseInt(stringValue);
    }

    public Boolean getBooleanValue(ListEntry row, String field) {
        String stringValue = stringValue(row, field);
        if (stringValue == null)
            return null;
        if (stringValue.toUpperCase().equals("TRUE"))
            return true;
        else if (stringValue.toUpperCase().equals("FALSE"))
            return false;
        throw new IllegalArgumentException("Cannot parse boolean value for value: " + stringValue);
    }

}
