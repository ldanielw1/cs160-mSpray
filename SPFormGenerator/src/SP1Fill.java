import java.io.IOException;

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

    public static final String containingFolder = "sp1";

    public SP1Fill(String destinationFile) throws IOException, DocumentException {
        super("Form-SP1.pdf", destinationFile);
    }

    public static void selfTest() {
        try {
            SP1Fill formFill = new SP1Fill("temp1.pdf");

            formFill.setSprayedRooms(SPRAYED_ROOMS_LIMIT);
            formFill.setSprayedShelters(SPRAYED_SHELTERS_LIMIT);
            formFill.setUnsprayedRooms(UNSPRAYED_ROOMS_LIMIT);
            formFill.setCanRefills(CAN_REFILLS_LIMIT);

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

    public void setSprayedRooms(int roomsSprayed) throws IOException, DocumentException {
        checkBoxes(SPRAYED_ROOMS_LABEL, roomsSprayed);
        setField(NUM_SPRAYED_ROOMS_LABEL, roomsSprayed);
    }

    public void setSprayedShelters(int sheltersSprayed) throws IOException, DocumentException {
        checkBoxes(SPRAYED_SHELTERS_LABEL, sheltersSprayed);
        setField(NUM_SPRAYED_SHELTERS_LABEL, sheltersSprayed);
    }

    public void setUnsprayedRooms(int roomsUnsprayed) throws IOException, DocumentException {
        checkBoxes(UNSPRAYED_ROOMS_LABEL, roomsUnsprayed);
        setField(NUM_UNSPRAYED_ROOMS_LABEL, roomsUnsprayed);
    }

    public void setCanRefills(int canRefills) throws IOException, DocumentException {
        checkBoxes(REFILLS_LABEL, canRefills);
        setField(NUM_CAN_REFILLS_LABEL, canRefills);
    }

    public void setChemical(String chemical) throws IOException, DocumentException {
        uncheckField(DDT_USED_LABEL);
        uncheckField(BAYTHROID_USED_LABEL);
        uncheckField(KOTHRINE_USED_LABEL);
        if (chemical.toUpperCase().equals("DDT"))
            checkField(SP1Fill.DDT_USED_LABEL);
        else
            checkField(SP1Fill.KOTHRINE_USED_LABEL);
    }

    public void setSprayer(String sprayer) throws IOException, DocumentException {
        setField(SPRAYMAN_LABEL, sprayer);
    }

    public void setForeman(String foreman) throws IOException, DocumentException {
        setField(FOREMAN_LABEL, foreman);
    }

    public void setFieldOfficer(String officer) throws IOException, DocumentException {
        setField(FIELD_OFFICER_LABEL, officer);
    }

    public void setDistrict(String district) throws IOException, DocumentException {
        setField(DISTRICT_LABEL, district);
    }

    public void setLocality(String locality) throws IOException, DocumentException {
        setField(LOCALITY_LABEL, locality);
    }

    public void setDate(String date) throws IOException, DocumentException {
        setField(DATE_LABEL, date);
    }

    public void setData(SprayData data) throws IOException, DocumentException {
        setSprayedRooms(data.getSprayedRooms());
        setSprayedShelters(data.getSprayedShelters());
        setUnsprayedRooms(data.getUnsprayedRooms());
        setCanRefills(data.getCansRefilled());
    }

    public void checkBoxes(String genericField, int boxes) throws IOException, DocumentException {
        for (int i = 1; i <= boxes; i += 1)
            checkField(genericField + i);
    }

}
