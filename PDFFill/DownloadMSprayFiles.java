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

    public static void main(String[] args) {
        DownloadMSprayFiles downloader = new DownloadMSprayFiles();
        try {
            GoogleSpreadsheet spreadsheet = new GoogleSpreadsheet("mSprayApp", "mSprayApp1.0",
                    "mSpray 2.0 Results", "Logged Data");

            downloader.downloadSprayerData(spreadsheet);

            File resFolder = new File(SP1Fill.containingFolder);
            if (!resFolder.exists())
                resFolder.mkdirs();

            System.out.println("Generating SP1 files...");
            for (String fileName : downloader.sp1Map.keySet()) {
                String resFileName = SP1Fill.containingFolder + "/" + fileName;
                List<ListEntry> rowList = downloader.sp1Map.get(fileName);
                SP1Fill formFill = new SP1Fill(resFileName + ".pdf");

                String dateString = fileName.split("_")[0];
                formFill.setField(SP1Fill.DATE_LABEL, dateString);

                int sprayedRooms = 0;
                int sprayedShelters = 0;
                int unsprayedRooms = 0;
                int refilledCans = 0;

                String recordedSprayer = fileName.split("_")[1];
                HashSet<String> foremen = new HashSet<String>();
                for (ListEntry row : rowList) {
                    String foremanName = row.getCustomElements().getValue("foreman");
                    foremen.add(foremanName);

                    String sprayerID = row.getCustomElements().getValue("sprayerID");
                    if (sprayerID != null && sprayerID.equals(recordedSprayer)) {
                        sprayedRooms += Integer.parseInt(row.getCustomElements().getValue(
                                "sprayedRooms1"));
                        sprayedShelters += Integer.parseInt(row.getCustomElements().getValue(
                                "sprayedShelters1"));
                        boolean refilledOneCan = row.getCustomElements().getValue("canRefill1")
                                .toUpperCase().equals("TRUE");
                        if (refilledOneCan)
                            refilledCans += 1;
                    } else {
                        sprayedRooms += Integer.parseInt(row.getCustomElements().getValue(
                                "sprayedRooms2"));
                        sprayedShelters += Integer.parseInt(row.getCustomElements().getValue(
                                "sprayedShelters2"));
                        boolean refilledOneCan = row.getCustomElements().getValue("canRefill2")
                                .toUpperCase().equals("TRUE");
                        if (refilledOneCan)
                            refilledCans += 1;
                    }
                    unsprayedRooms += Integer.parseInt(row.getCustomElements().getValue(
                            "unsprayedRooms"));
                }

                String chemical = fileName.split("_")[2];
                if (chemical.toUpperCase().equals("DDT"))
                    formFill.checkField(SP1Fill.DDT_USED_LABEL);
                else
                    formFill.checkField(SP1Fill.KOTHRINE_USED_LABEL);

                formFill.setField(SP1Fill.SPRAYMAN_LABEL, recordedSprayer);
                final StringBuilder foremenListing = new StringBuilder();
                boolean firstForeman = true;
                for (String foreman : foremen) {
                    if (firstForeman)
                        firstForeman = false;
                    else
                        foremenListing.append(", ");
                    foremenListing.append(foreman);
                }
                formFill.setField(SP1Fill.FOREMAN_LABEL, foremenListing.toString());

                formFill.checkBoxes(SP1Fill.SPRAYED_ROOMS_LABEL, sprayedRooms);
                formFill.checkBoxes(SP1Fill.SPRAYED_SHELTERS_LABEL, sprayedShelters);
                formFill.checkBoxes(SP1Fill.UNSPRAYED_ROOMS_LABEL, unsprayedRooms);
                formFill.checkBoxes(SP1Fill.REFILLS_LABEL, refilledCans);

                formFill.setField(SP1Fill.NUM_SPRAYED_ROOMS_LABEL, sprayedRooms);
                formFill.setField(SP1Fill.NUM_SPRAYED_SHELTERS_LABEL, sprayedShelters);
                formFill.setField(SP1Fill.NUM_UNSPRAYED_ROOMS_LABEL, unsprayedRooms);
                formFill.setField(SP1Fill.NUM_CAN_REFILLS_LABEL, refilledCans);

                formFill.setField(SP1Fill.FIELD_OFFICER_LABEL, "Brenda Eskenazi");
                formFill.setField(SP1Fill.DISTRICT_LABEL, "Vhembe");
                formFill.setField(SP1Fill.LOCALITY_LABEL, "Limpopo");

                formFill.close();
            }

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

    public void downloadSprayerData(GoogleSpreadsheet spreadsheet) {
        sp1Map = new HashMap<String, List<ListEntry>>();

        System.out.println("Downloading SP1 form data from server...");
        List<ListEntry> rows = spreadsheet.getData();
        for (ListEntry row : rows) {
            String timeStamp = row.getCustomElements().getValue("timeStamp");
            String dateString = timeStamp.split(" ")[0];
            dateString = dateString.replaceAll("/", "-");
            String chemicalUsed1 = row.getCustomElements().getValue("chemicalUsed1");
            if (chemicalUsed1 != null) {
                String sprayerID = row.getCustomElements().getValue("sprayerID");
                List<ListEntry> currentEntriesForUser = null;
                final StringBuilder userInformationBuilder = new StringBuilder();
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

}