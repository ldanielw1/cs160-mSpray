import java.io.IOException;

import com.itextpdf.text.DocumentException;

public class SP3Fill extends PDFFill {
    private static final String DDT_USED_LABEL = "ddtUsed";
    private static final String BAYTHROID_USED_LABEL = "baythroidUsed";
    private static final String KOTHRINE_USED_LABEL = "kothrineUsed";

    private static final String DDT_ROOMS_LABEL = "ddtRooms";
    private static final String DDT_SHELTERS_LABEL = "ddtShelters";
    private static final String OTHER_ROOMS_LABEL = "otherRooms";
    private static final String OTHER_SHELTERS_LABEL = "otherShelters";
    private static final String DDT_UNSPRAYED_LABEL = "ddtUnsprayed";
    private static final String OTHER_UNSPRAYED_LABEL = "otherUnsprayed";
    private static final String DDT_REFILLS_LABEL = "ddtRefills";
    private static final String OTHER_REFILLS_LABEL = "otherRefills";
    private static final String SPRAYMAN_DAYS_LABEL = "spraymanDays";

    private static final String REGION_LABEL = "region";
    private static final String SECTOR_LABEL = "sector";
    private static final String DATE_FROM_LABEL = "dateFrom";
    private static final String DATE_TO_LABEL = "dateTo";
    private static final String DISTRICT_LABEL = "district";
    private static final String LOCALITY_LABEL = "locality";
    private static final String TEAM_LABEL = "team";

    private static final int DATA_ROWS = 15;

    public static void main(String[] args) {
        selfTest();
    }

    public SP3Fill() throws IOException, DocumentException {
        super("Form-SP3.pdf", "temp3.pdf");
    }

    public static void selfTest() {
        try {
            SP3Fill formFill = new SP3Fill();
            formFill.setField(REGION_LABEL, "Limpopo");
            formFill.setField(TEAM_LABEL, "team1");
            formFill.setField(DISTRICT_LABEL, "Vhembe");
            formFill.setField(SECTOR_LABEL, "No Name Sector");
            formFill.setField(DATE_FROM_LABEL, "3/28/13");
            formFill.setField(DATE_TO_LABEL, "3/29/13");
            for (int i = 1; i <= DATA_ROWS + 1; i += 1) {
                String tempValue = Integer.toString(i);
                if (i == DATA_ROWS + 1)
                    tempValue = "Final";
                formFill.setField(LOCALITY_LABEL + tempValue, "locality" + tempValue);
                formFill.setField(DDT_ROOMS_LABEL + tempValue, tempValue);
                formFill.setField(DDT_SHELTERS_LABEL + tempValue, tempValue + i);
                formFill.setField(OTHER_ROOMS_LABEL + tempValue, tempValue);
                formFill.setField(OTHER_SHELTERS_LABEL + tempValue, tempValue + i);
                formFill.setField(DDT_UNSPRAYED_LABEL + tempValue, tempValue + i);
                formFill.setField(OTHER_UNSPRAYED_LABEL + tempValue, tempValue + 2);
                formFill.setField(DDT_REFILLS_LABEL + tempValue, tempValue + 3);
                formFill.setField(OTHER_REFILLS_LABEL + tempValue, tempValue + 4);
                formFill.setField(SPRAYMAN_DAYS_LABEL + tempValue, tempValue + 5);
            }
            formFill.setField(DDT_USED_LABEL, "13");
            formFill.setField(BAYTHROID_USED_LABEL, "14");
            formFill.setField(KOTHRINE_USED_LABEL, "15");
            formFill.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Finished SP3!");
    }
}