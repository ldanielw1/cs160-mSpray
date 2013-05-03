import java.io.IOException;

import com.itextpdf.text.DocumentException;

public class SP2Fill extends PDFFill {
    private static final String DDT_USED_LABEL = "ddtUsed";
    private static final String BAYTHROID_USED_LABEL = "baythroidUsed";
    private static final String KOTHRINE_USED_LABEL = "kothrineUsed";

    private static final String SPRAYMAN_LABEL = "sprayman";
    private static final String DDT_ROOMS_LABEL = "ddtRooms";
    private static final String OTHER_ROOMS_LABEL = "otherRooms";
    private static final String DDT_SHELTERS_LABEL = "ddtShelters";
    private static final String OTHER_SHELTERS_LABEL = "otherShelters";
    private static final String DDT_UNSPRAYED_LABEL = "ddtUnsprayed";
    private static final String OTHER_UNSPRAYED_LABEL = "otherUnsprayed";
    private static final String DDT_REFILLS_LABEL = "ddtRefills";
    private static final String OTHER_REFILLS_LABEL = "otherRefills";

    private static final String REGION_LABEL = "region";
    private static final String SECTOR_LABEL = "sector";
    private static final String DATE_LABEL = "date";
    private static final String DISTRICT_LABEL = "district";
    private static final String LOCALITY_LABEL = "locality";
    private static final String TEAM_LABEL = "team";

    private static final int DATA_ROWS = 15;

    public static void main(String[] args) {
        selfTest();
    }

    public SP2Fill() throws IOException, DocumentException {
        super("Form-SP2.pdf", "temp2.pdf");
    }

    public static void selfTest() {
        try {
            SP2Fill formFill = new SP2Fill();
            formFill.setField(REGION_LABEL, "Limpopo");
            formFill.setField(SECTOR_LABEL, "Vhembe");
            formFill.setField(DATE_LABEL, "3/28/13");
            formFill.setField(DISTRICT_LABEL, "Vhembe");
            formFill.setField(LOCALITY_LABEL, "No Name");
            formFill.setField(TEAM_LABEL, "Team 1");
            for (int i = 1; i <= DATA_ROWS + 4; i += 1) {
                String tempValue = Integer.toString(i);
                if (i == 16)
                    tempValue = "Total";
                else if (i == 17)
                    tempValue = "Mopup";
                else if (i == 18)
                    tempValue = "Final";
                else if (i == 19)
                    tempValue = "Final2";
                formFill.setField(SPRAYMAN_LABEL + tempValue, "man" + tempValue);
                formFill.setField(DDT_ROOMS_LABEL + tempValue, DDT_ROOMS_LABEL + tempValue);
                formFill.setField(OTHER_ROOMS_LABEL + tempValue, OTHER_ROOMS_LABEL + tempValue);
                formFill.setField(DDT_SHELTERS_LABEL + tempValue, DDT_SHELTERS_LABEL + tempValue);
                formFill.setField(OTHER_SHELTERS_LABEL + tempValue, OTHER_SHELTERS_LABEL
                        + tempValue);
                formFill.setField(DDT_UNSPRAYED_LABEL + tempValue, DDT_UNSPRAYED_LABEL + tempValue);
                formFill.setField(OTHER_UNSPRAYED_LABEL + tempValue, OTHER_UNSPRAYED_LABEL
                        + tempValue);
                formFill.setField(DDT_REFILLS_LABEL + tempValue, DDT_REFILLS_LABEL + tempValue);
                formFill.setField(OTHER_REFILLS_LABEL + tempValue, OTHER_REFILLS_LABEL + tempValue);
            }
            formFill.setField(DDT_USED_LABEL, 10);
            formFill.setField(BAYTHROID_USED_LABEL, 12);
            formFill.setField(KOTHRINE_USED_LABEL, 14);
            formFill.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Finished SP2!");
    }

}