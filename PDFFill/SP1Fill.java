import java.io.IOException;

import com.itextpdf.text.DocumentException;

public class SP1Fill extends PDFFill {
    public static final String SPRAYED_ROOMS_LABEL = "sprayedRooms";
    public static final String SPRAYED_SHELTERS_LABEL = "sprayedShelters";
    public static final String UNSPRAYED_ROOMS_LABEL = "unsprayedRooms";
    public static final String REFILLS_LABEL = "refills";
    public static final String NUM_SPRAYED_ROOMS_LABEL = "sprayedRooms";
    public static final String NUM_SPRAYED_SHELTERS_LABEL = "sprayedShelters";
    public static final String NUM_UNSPRAYED_ROOMS_LABEL = "unsprayedRooms";
    public static final String NUM_CAN_REFILLS_LABEL = "refills";

    public static final String DDT_USED_LABEL = "ddtUsed";
    public static final String BAYTHROID_USED_LABEL = "baythroidUsed";
    public static final String KOTHRINE_USED_LABEL = "kothrineUsed";

    public static final String DISTRICT_LABEL = "district";
    public static final String LOCALITY_LABEL = "locality";
    public static final String DATE_LABEL = "date";
    public static final String SPRAYMAN_LABEL = "sprayman";
    public static final String FOREMAN_LABEL = "foreman";
    public static final String FIELD_OFFICER_LABEL = "fieldOfficer";

    public static final int SPRAYED_ROOMS_LIMIT = 84;
    public static final int SPRAYED_SHELTERS_LIMIT = 84;
    public static final int UNSPRAYED_ROOMS_LIMIT = 63;
    public static final int CAN_REFILLS_LIMIT = 42;

    public static final String containingFolder = "sp1";

    public static void main(String[] args) {
        selfTest();
    }

    public SP1Fill(String destinationFile) throws IOException, DocumentException {
        super("Form-SP1.pdf", destinationFile);
    }

    public static void selfTest() {
        try {
            SP1Fill formFill = new SP1Fill("temp1.pdf");

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
