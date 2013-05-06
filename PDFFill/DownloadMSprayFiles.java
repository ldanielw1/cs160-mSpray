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
    HashMap<String, List<ListEntry>> sp1Map;
    HashMap<String, HashMap<String, List<ListEntry>>> sp2Map;

    public static void main(String[] args) {
        DownloadMSprayFiles downloader = new DownloadMSprayFiles();
        try {
            GoogleSpreadsheet spreadsheet = new GoogleSpreadsheet("mSprayApp", "mSprayApp1.0",
                    "mSpray 2.0 Results", "Logged Data");

            downloader.downloadSprayerData(spreadsheet);
            System.out.println();

            File sp1Folder = new File(SP1Fill.containingFolder);
            if (!sp1Folder.exists())
                sp1Folder.mkdirs();
            File sp2Folder = new File(SP2Fill.containingFolder);
            if (!sp2Folder.exists())
                sp2Folder.mkdirs();
            File sp3Folder = new File(SP3Fill.containingFolder);
            if (!sp3Folder.exists())
                sp3Folder.mkdirs();

            downloader.generateSP1Forms();
            downloader.generateSP2Forms();

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
        System.out.println("finished!");
    }

    public void generateSP1Forms() throws IOException, DocumentException {
        System.out.println("Generating SP1 files...");
        for (String fileName : sp1Map.keySet()) {
            String resFileName = SP1Fill.containingFolder + "/" + fileName;
            List<ListEntry> rowList = sp1Map.get(fileName);
            SP1Fill formFill = new SP1Fill(resFileName + ".pdf");

            String dateString = fileName.split("_")[1];
            formFill.setDate(dateString);

            SprayData data = new SprayData();

            String recordedSprayer = fileName.split("_")[2];
            HashSet<String> foremen = new HashSet<String>();
            for (ListEntry row : rowList) {
                String foremanName = getValue(row, "foreman");
                foremen.add(foremanName);

                String sprayerID = getValue(row, "sprayerID");
                if (sprayerID != null && sprayerID.equals(recordedSprayer)) {
                    data.updateSprayedRooms(getIntValue(row, "sprayedRooms1"));
                    data.updateSprayedShelters(getIntValue(row, "sprayedShelters1"));
                    if (getBooleanValue(row, "canRefill1"))
                        data.updateCansRefilled(1);
                } else {
                    data.updateSprayedRooms(getIntValue(row, "sprayedRooms2"));
                    data.updateSprayedShelters(getIntValue(row, "sprayedShelters2"));
                    if (getBooleanValue(row, "canRefill2"))
                        data.updateCansRefilled(1);
                }
                data.updateUnsprayedRooms(getIntValue(row, "unsprayedRooms"));
            }

            final StringBuilder foremenListing = new StringBuilder();
            boolean firstForeman = true;
            for (String foreman : foremen) {
                if (firstForeman)
                    firstForeman = false;
                else
                    foremenListing.append(", ");
                foremenListing.append(foreman);
            }
            String chemical = fileName.split("_")[3];

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
        System.out.println("Generating SP2 files...");
        for (String fileName : sp2Map.keySet()) {
            String resFileName = SP2Fill.containingFolder + "/" + fileName;
            SP2Fill formFill = new SP2Fill(resFileName + ".pdf");
            HashMap<String, List<ListEntry>> sprayerMap = sp2Map.get(fileName);

            String dateString = fileName.split("_")[1];
            formFill.setDate(dateString);

            SprayData totalDDTData = new SprayData();
            SprayData totalOtherData = new SprayData();
            int sprayerNumber = 1;
            for (String sprayer : sprayerMap.keySet()) {
                List<ListEntry> rowList = sprayerMap.get(sprayer);

                SprayData sprayerDDTData = new SprayData();
                SprayData sprayerOtherData = new SprayData();

                for (ListEntry row : rowList) {
                    SprayData dataToUpdate = sprayerDDTData;

                    String sprayerID = getValue(row, "sprayerID");
                    if (sprayerID != null && sprayerID.equals(sprayer)) {
                        if (!getValue(row, "chemicalUsed1").equals("DDT"))
                            dataToUpdate = sprayerOtherData;

                        dataToUpdate.updateSprayedRooms(getIntValue(row, "sprayedRooms1"));
                        dataToUpdate.updateSprayedShelters(getIntValue(row, "sprayedShelters1"));
                        if (getBooleanValue(row, "canRefill1"))
                            dataToUpdate.updateCansRefilled(1);
                    } else {
                        if (!getValue(row, "chemicalUsed2").equals("DDT"))
                            dataToUpdate = sprayerOtherData;

                        dataToUpdate.updateSprayedRooms(getIntValue(row, "sprayedRooms2"));
                        dataToUpdate.updateSprayedShelters(getIntValue(row, "sprayedShelters2"));
                        if (getBooleanValue(row, "canRefill2"))
                            dataToUpdate.updateCansRefilled(1);
                    }
                    dataToUpdate.updateUnsprayedRooms(getIntValue(row, "unsprayedRooms"));
                }

                formFill.setSprayman(sprayerNumber, sprayer);
                formFill.setDDTData(sprayerNumber, sprayerDDTData);
                formFill.setOtherData(sprayerNumber, sprayerOtherData);
                totalDDTData.update(sprayerDDTData);
                totalOtherData.update(sprayerOtherData);

                sprayerNumber += 1;
            }

            // TODO: figure out if team should be labeled by foreman
            String foreman = fileName.split("_")[2];
            formFill.setTeam(foreman);

            formFill.setDDTData(Constants.TOTAL, totalDDTData);
            formFill.setOtherData(Constants.TOTAL, totalOtherData);

            // TODO: Figure out where the following information should come from
            formFill.setDistrict("Vhembe");
            formFill.setLocality("");
            formFill.setRegion("Limpopo");
            formFill.setLocality("South Africa");

            formFill.close();
            break;
        }

    }

    public void downloadSprayerData(GoogleSpreadsheet spreadsheet) {
        List<ListEntry> rows = spreadsheet.getData();
        downloadSP1(rows);
        downloadSP2(rows);
        downloadSP3(rows);
    }

    public void downloadSP1(List<ListEntry> rows) {
        System.out.println("Downloading SP1 form data from server...");
        sp1Map = new HashMap<String, List<ListEntry>>();
        for (ListEntry row : rows) {
            String timeStamp = row.getCustomElements().getValue("timeStamp");
            String dateString = timeStamp.split(" ")[0];
            dateString = dateString.replaceAll("/", "-");

            String chemicalUsed1 = row.getCustomElements().getValue("chemicalUsed1");
            if (chemicalUsed1 != null) {
                String sprayerID = row.getCustomElements().getValue("sprayerID");
                List<ListEntry> currentEntriesForUser = null;
                final StringBuilder userInformationBuilder = new StringBuilder();
                userInformationBuilder.append("sp1_");
                userInformationBuilder.append(dateString);
                userInformationBuilder.append("_");
                userInformationBuilder.append(sprayerID);
                userInformationBuilder.append("_");
                userInformationBuilder.append(chemicalUsed1);
                String userInformation = userInformationBuilder.toString();
                if (sp1Map.containsKey(userInformation))
                    currentEntriesForUser = sp1Map.get(userInformation);
                else
                    currentEntriesForUser = new ArrayList<ListEntry>();
                currentEntriesForUser.add(row);
                sp1Map.put(userInformation, currentEntriesForUser);
            }
            String chemicalUsed2 = row.getCustomElements().getValue("chemicalUsed2");
            if (chemicalUsed2 != null) {
                String sprayer2ID = row.getCustomElements().getValue("sprayer2ID");
                List<ListEntry> currentEntriesForUser = null;
                final StringBuilder userInformationBuilder = new StringBuilder();
                userInformationBuilder.append("sp1_");
                userInformationBuilder.append(dateString);
                userInformationBuilder.append("_");
                userInformationBuilder.append(sprayer2ID);
                userInformationBuilder.append("_");
                userInformationBuilder.append(chemicalUsed2);
                String userInformation = userInformationBuilder.toString();
                if (sp1Map.containsKey(userInformation))
                    currentEntriesForUser = sp1Map.get(userInformation);
                else
                    currentEntriesForUser = new ArrayList<ListEntry>();
                currentEntriesForUser.add(row);
                sp1Map.put(userInformation, currentEntriesForUser);
            }
        }
    }

    public void downloadSP2(List<ListEntry> rows) {
        System.out.println("Downloading SP2 form data from server...");
        sp2Map = new HashMap<String, HashMap<String, List<ListEntry>>>();

        for (ListEntry row : rows) {
            String timeStamp = row.getCustomElements().getValue("timeStamp");
            String dateString = timeStamp.split(" ")[0];
            dateString = dateString.replaceAll("/", "-");

            // We only need to check for the first chemical because there is a
            // spraying if and only if there is a 1st chemical
            String chemicalUsed1 = row.getCustomElements().getValue("chemicalUsed1");
            if (chemicalUsed1 != null) {
                String foreman = row.getCustomElements().getValue("foreman");
                final StringBuilder userInformationBuilder = new StringBuilder();
                userInformationBuilder.append("sp2_");
                userInformationBuilder.append(dateString);
                userInformationBuilder.append("_");
                userInformationBuilder.append(foreman);
                String userInformation = userInformationBuilder.toString();

                HashMap<String, List<ListEntry>> currentEntriesForSprayer1 = null;
                if (sp2Map.containsKey(userInformation))
                    currentEntriesForSprayer1 = sp2Map.get(userInformation);
                else
                    currentEntriesForSprayer1 = new HashMap<String, List<ListEntry>>();

                String sprayerID = row.getCustomElements().getValue("sprayerID");
                List<ListEntry> sprayerEntries = null;
                if (!currentEntriesForSprayer1.containsKey(sprayerID))
                    sprayerEntries = new ArrayList<ListEntry>();
                else
                    sprayerEntries = currentEntriesForSprayer1.get(sprayerID);
                sprayerEntries.add(row);
                currentEntriesForSprayer1.put(sprayerID, sprayerEntries);

                sp2Map.put(userInformation, currentEntriesForSprayer1);

                String chemicalUsed2 = row.getCustomElements().getValue("chemicalUsed2");
                if (chemicalUsed2 != null) {
                    HashMap<String, List<ListEntry>> currentEntriesForSprayer2 = sp2Map
                            .get(userInformation);

                    String sprayer2ID = row.getCustomElements().getValue("sprayer2ID");
                    List<ListEntry> sprayer2Entries = null;
                    if (!currentEntriesForSprayer2.containsKey(sprayer2ID))
                        sprayer2Entries = new ArrayList<ListEntry>();
                    else
                        sprayer2Entries = currentEntriesForSprayer2.get(sprayer2ID);
                    sprayer2Entries.add(row);
                    currentEntriesForSprayer2.put(sprayer2ID, sprayer2Entries);

                    sp2Map.put(userInformation, currentEntriesForSprayer2);
                }
            }
        }
    }

    public void downloadSP3(List<ListEntry> rows) {
        System.out.println("Downloading SP3 form data from server...");

    }

    public String getValue(ListEntry row, String field) {
        return row.getCustomElements().getValue(field);
    }

    public Integer getIntValue(ListEntry row, String field) {
        String stringValue = getValue(row, field);
        if (stringValue == null)
            return null;
        return Integer.parseInt(stringValue);
    }

    public Boolean getBooleanValue(ListEntry row, String field) {
        String stringValue = getValue(row, field);
        if (stringValue == null)
            return null;
        if (stringValue.toUpperCase().equals("TRUE"))
            return true;
        else if (stringValue.toUpperCase().equals("FALSE"))
            return false;
        throw new IllegalArgumentException("Cannot parse boolean value for value: " + stringValue);
    }

}
