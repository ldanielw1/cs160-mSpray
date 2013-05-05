import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;
import com.itextpdf.text.DocumentException;

public class SP1Fill extends PDFFill {
    private static final String SPRAYED_ROOMS_LABEL = "sprayedRooms";
    private static final String SPRAYED_SHELTERS_LABEL = "sprayedShelters";
    private static final String UNSPRAYED_ROOMS_LABEL = "unsprayedRooms";
    private static final String REFILLS_LABEL = "refills";
    private static final String NUM_SPRAYED_ROOMS_LABEL = "sprayedRooms";
    private static final String NUM_SPRAYED_SHELTERS_LABEL = "sprayedShelters";
    private static final String NUM_UNSPRAYED_ROOMS_LABEL = "unsprayedRooms";
    private static final String NUM_CAN_REFILLS_LABEL = "refills";

    private static final String DDT_USED_LABEL = "ddtUsed";
    private static final String BAYTHROID_USED_LABEL = "baythroidUsed";
    private static final String KOTHRINE_USED_LABEL = "kothrineUsed";

    private static final String DISTRICT_LABEL = "district";
    private static final String LOCALITY_LABEL = "locality";
    private static final String DATE_LABEL = "date";
    private static final String SPRAYMAN_LABEL = "sprayman";
    private static final String FOREMAN_LABEL = "foreman";
    private static final String FIELD_OFFICER_LABEL = "fieldOfficer";

    private static final int SPRAYED_ROOMS_LIMIT = 84;
    private static final int SPRAYED_SHELTERS_LIMIT = 84;
    private static final int UNSPRAYED_ROOMS_LIMIT = 63;
    private static final int CAN_REFILLS_LIMIT = 42;

    private static final String containingFolder = "sp1";

    public static void main(String[] args) {
        try {
            GoogleSpreadsheet spreadsheet = new GoogleSpreadsheet("mSprayApp", "mSprayApp1.0",
                    "mSpray 2.0 Results", "Logged Data");

            HashMap<String, List<ListEntry>> userEntries = new HashMap<String, List<ListEntry>>();

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
                    if (userEntries.containsKey(userInformation))
                        currentEntriesForUser = userEntries.get(userInformation);
                    else
                        currentEntriesForUser = new ArrayList<ListEntry>();
                    currentEntriesForUser.add(row);
                    userEntries.put(userInformation, currentEntriesForUser);
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
                    if (userEntries.containsKey(userInformation))
                        currentEntriesForUser = userEntries.get(userInformation);
                    else
                        currentEntriesForUser = new ArrayList<ListEntry>();
                    currentEntriesForUser.add(row);
                    userEntries.put(userInformation, currentEntriesForUser);
                }
            }

            File resFolder = new File(containingFolder);
            if (!resFolder.exists()) {
                resFolder.mkdirs();
            }

            System.out.println("Generating SP1 files...");
            for (String fileName : userEntries.keySet()) {
                String resFileName = containingFolder + "/" + fileName;
                List<ListEntry> rowList = userEntries.get(fileName);
                SP1Fill formFill = new SP1Fill(resFileName + ".pdf");

                String dateString = fileName.split("_")[0];
                String sprayer = fileName.split("_")[1];
                String chemical = fileName.split("_")[2];
                formFill.setField(DATE_LABEL, dateString);

                HashSet<String> foremen = new HashSet<String>();

                int sprayedRooms = 0;
                int sprayedShelters = 0;
                int unsprayedRooms = 0;
                int refilledCans = 0;

                for (ListEntry row : rowList) {
                    String foremanName = row.getCustomElements().getValue("foreman");
                    foremen.add(foremanName);

                    String sprayerID = row.getCustomElements().getValue("sprayerID");
                    if (sprayerID != null && sprayerID.equals(sprayer)) {
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

                if (chemical.toUpperCase().equals("DDT"))
                    formFill.checkField(DDT_USED_LABEL);
                else
                    formFill.checkField(KOTHRINE_USED_LABEL);

                formFill.setField(SPRAYMAN_LABEL, sprayer);
                final StringBuilder foremenListing = new StringBuilder();
                boolean firstForeman = true;
                for (String foreman : foremen) {
                    if (firstForeman)
                        firstForeman = false;
                    else
                        foremenListing.append(", ");
                    foremenListing.append(foreman);
                }
                formFill.setField(FOREMAN_LABEL, foremenListing.toString());

                formFill.checkBoxes(SPRAYED_ROOMS_LABEL, sprayedRooms);
                formFill.checkBoxes(SPRAYED_SHELTERS_LABEL, sprayedShelters);
                formFill.checkBoxes(UNSPRAYED_ROOMS_LABEL, unsprayedRooms);
                formFill.checkBoxes(REFILLS_LABEL, refilledCans);

                formFill.setField(NUM_SPRAYED_ROOMS_LABEL, sprayedRooms);
                formFill.setField(NUM_SPRAYED_SHELTERS_LABEL, sprayedShelters);
                formFill.setField(NUM_UNSPRAYED_ROOMS_LABEL, unsprayedRooms);
                formFill.setField(NUM_CAN_REFILLS_LABEL, refilledCans);

                formFill.setField(FIELD_OFFICER_LABEL, "Brenda Eskenazi");
                formFill.setField(DISTRICT_LABEL, "Vhembe");
                formFill.setField(LOCALITY_LABEL, "Limpopo");

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
        // selfTest();
    }

    public SP1Fill() throws IOException, DocumentException {
        super("Form-SP1.pdf", "temp1.pdf");
    }

    public SP1Fill(String destinationFile) throws IOException, DocumentException {
        super("Form-SP1.pdf", destinationFile);
    }

    public static void selfTest() {
        try {
            SP1Fill formFill = new SP1Fill();

            formFill.checkBoxes(SPRAYED_ROOMS_LABEL, SPRAYED_ROOMS_LIMIT);
            formFill.checkBoxes(SPRAYED_SHELTERS_LABEL, SPRAYED_SHELTERS_LIMIT);
            formFill.checkBoxes(UNSPRAYED_ROOMS_LABEL, UNSPRAYED_ROOMS_LIMIT);
            formFill.checkBoxes(REFILLS_LABEL, CAN_REFILLS_LIMIT);

            formFill.setField(NUM_SPRAYED_ROOMS_LABEL, SPRAYED_ROOMS_LIMIT);
            formFill.setField(NUM_SPRAYED_SHELTERS_LABEL, SPRAYED_SHELTERS_LIMIT);
            formFill.setField(NUM_UNSPRAYED_ROOMS_LABEL, UNSPRAYED_ROOMS_LIMIT);
            formFill.setField(NUM_CAN_REFILLS_LABEL, CAN_REFILLS_LIMIT);

            formFill.checkField(DDT_USED_LABEL);
            formFill.checkField(BAYTHROID_USED_LABEL);
            formFill.checkField(KOTHRINE_USED_LABEL);
            formFill.setField(DISTRICT_LABEL, "Vhembe");
            formFill.setField(LOCALITY_LABEL, "Limpopo");
            formFill.setField(DATE_LABEL, "3/28/13");
            formFill.setField(SPRAYMAN_LABEL, "Chayke Ho");
            formFill.setField(FOREMAN_LABEL, "Ravuluma P");
            formFill.setField(FIELD_OFFICER_LABEL, "Brenda Eskenazi");
            formFill.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Finished SP1!");
    }

    public void checkBoxes(String genericField, int boxes) throws IOException, DocumentException {
        for (int i = 1; i <= boxes; i += 1)
            checkField(genericField + i);
    }

}
