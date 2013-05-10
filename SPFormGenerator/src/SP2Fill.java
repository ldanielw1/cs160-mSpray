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

    public static String TOTAL = "Total";
    public static String MOPUP = "Mopup";
    public static String FINAL = "Final";
    public static String FINAL2 = "Final2";

    public static final int ROW_LIMIT = 15;
    public static final String containingFolder = "sp2";

    public SP2Fill(String destinationFile) throws IOException, DocumentException {
        super("Form-SP2.pdf", destinationFile);
    }

    public static void selfTest() {
        try {
            SP2Fill formFill = new SP2Fill("temp2.pdf");
            formFill.setRegion("Limpopo");
            formFill.setSector("Vhembe");
            formFill.setDate("5/6/13");
            formFill.setDistrict("Vhembe");
            formFill.setLocality("No Name");
            formFill.setTeam("Team 1");
            for (int i = 1; i <= ROW_LIMIT + 4; i += 1) {
                String tempValue = Integer.toString(i);
                if (i == 16)
                    tempValue = TOTAL;
                else if (i == 17)
                    tempValue = MOPUP;
                else if (i == 18)
                    tempValue = FINAL;
                else if (i == 19)
                    tempValue = FINAL2;
                formFill.setSprayman(tempValue, "man" + tempValue);
                formFill.setDDTRooms(tempValue, DDT_ROOMS_LABEL + tempValue);
                formFill.setOtherRooms(tempValue, OTHER_ROOMS_LABEL + tempValue);
                formFill.setDDTShelters(tempValue, DDT_SHELTERS_LABEL + tempValue);
                formFill.setOtherShelters(tempValue, OTHER_SHELTERS_LABEL + tempValue);
                formFill.setDDTUnsprayed(tempValue, DDT_UNSPRAYED_LABEL + tempValue);
                formFill.setOtherUnsprayed(tempValue, OTHER_UNSPRAYED_LABEL + tempValue);
                formFill.setDDTRefills(tempValue, DDT_REFILLS_LABEL + tempValue);
                formFill.setOtherRefills(tempValue, OTHER_REFILLS_LABEL + tempValue);
            }
            formFill.setDDTUsed(10);
            formFill.setBaythroidUsed(12);
            formFill.setKOthrineUsed(14);
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

    public void setDate(String date) throws IOException, DocumentException {
        setField(DATE_LABEL, date);
    }

    public void setSector(String sector) throws IOException, DocumentException {
        setField(SECTOR_LABEL, sector);
    }

    public void setRegion(String region) throws IOException, DocumentException {
        setField(REGION_LABEL, region);
    }

    public void setDistrict(String district) throws IOException, DocumentException {
        setField(DISTRICT_LABEL, district);
    }

    public void setLocality(String locality) throws IOException, DocumentException {
        setField(LOCALITY_LABEL, locality);
    }

    public void setTeam(String team) throws IOException, DocumentException {
        setField(TEAM_LABEL, team);
    }

    public void setSprayman(String index, String name) throws IOException, DocumentException {
        setField(SPRAYMAN_LABEL + index, name);
    }

    public void setSprayman(int index, String name) throws IOException, DocumentException {
        setField(SPRAYMAN_LABEL + index, name);
    }

    public void setDDTRooms(String index, int amount) throws IOException, DocumentException {
        setField(DDT_ROOMS_LABEL + index, amount);
    }

    public void setDDTRooms(String index, String amount) throws IOException, DocumentException {
        setField(DDT_ROOMS_LABEL + index, amount);
    }

    public void setDDTRooms(int index, int amount) throws IOException, DocumentException {
        setField(DDT_ROOMS_LABEL + index, amount);
    }

    public void setDDTRooms(int index, String amount) throws IOException, DocumentException {
        setField(DDT_ROOMS_LABEL + index, amount);
    }

    public void setOtherRooms(String index, int amount) throws IOException, DocumentException {
        setField(OTHER_ROOMS_LABEL + index, amount);
    }

    public void setOtherRooms(String index, String amount) throws IOException, DocumentException {
        setField(OTHER_ROOMS_LABEL + index, amount);
    }

    public void setOtherRooms(int index, int amount) throws IOException, DocumentException {
        setField(OTHER_ROOMS_LABEL + index, amount);
    }

    public void setOtherRooms(int index, String amount) throws IOException, DocumentException {
        setField(OTHER_ROOMS_LABEL + index, amount);
    }

    public void setDDTShelters(String index, int amount) throws IOException, DocumentException {
        setField(DDT_SHELTERS_LABEL + index, amount);
    }

    public void setDDTShelters(String index, String amount) throws IOException, DocumentException {
        setField(DDT_SHELTERS_LABEL + index, amount);
    }

    public void setDDTShelters(int index, int amount) throws IOException, DocumentException {
        setField(DDT_SHELTERS_LABEL + index, amount);
    }

    public void setDDTShelters(int index, String amount) throws IOException, DocumentException {
        setField(DDT_SHELTERS_LABEL + index, amount);
    }

    public void setOtherShelters(String index, int amount) throws IOException, DocumentException {
        setField(OTHER_SHELTERS_LABEL + index, amount);
    }

    public void setOtherShelters(String index, String amount) throws IOException, DocumentException {
        setField(OTHER_SHELTERS_LABEL + index, amount);
    }

    public void setOtherShelters(int index, int amount) throws IOException, DocumentException {
        setField(OTHER_SHELTERS_LABEL + index, amount);
    }

    public void setOtherShelters(int index, String amount) throws IOException, DocumentException {
        setField(OTHER_SHELTERS_LABEL + index, amount);
    }

    public void setDDTUnsprayed(String index, int amount) throws IOException, DocumentException {
        setField(DDT_UNSPRAYED_LABEL + index, amount);
    }

    public void setDDTUnsprayed(String index, String amount) throws IOException, DocumentException {
        setField(DDT_UNSPRAYED_LABEL + index, amount);
    }

    public void setDDTUnsprayed(int index, int amount) throws IOException, DocumentException {
        setField(DDT_UNSPRAYED_LABEL + index, amount);
    }

    public void setDDTUnsprayed(int index, String amount) throws IOException, DocumentException {
        setField(DDT_UNSPRAYED_LABEL + index, amount);
    }

    public void setOtherUnsprayed(String index, int amount) throws IOException, DocumentException {
        setField(OTHER_UNSPRAYED_LABEL + index, amount);
    }

    public void setOtherUnsprayed(String index, String amount) throws IOException,
            DocumentException {
        setField(OTHER_UNSPRAYED_LABEL + index, amount);
    }

    public void setOtherUnsprayed(int index, int amount) throws IOException, DocumentException {
        setField(OTHER_UNSPRAYED_LABEL + index, amount);
    }

    public void setOtherUnsprayed(int index, String amount) throws IOException, DocumentException {
        setField(OTHER_UNSPRAYED_LABEL + index, amount);
    }

    public void setDDTRefills(String index, int amount) throws IOException, DocumentException {
        setField(DDT_REFILLS_LABEL + index, amount);
    }

    public void setDDTRefills(String index, String amount) throws IOException, DocumentException {
        setField(DDT_REFILLS_LABEL + index, amount);
    }

    public void setDDTRefills(int index, int amount) throws IOException, DocumentException {
        setField(DDT_REFILLS_LABEL + index, amount);
    }

    public void setDDTRefills(int index, String amount) throws IOException, DocumentException {
        setField(DDT_REFILLS_LABEL + index, amount);
    }

    public void setOtherRefills(String index, int amount) throws IOException, DocumentException {
        setField(OTHER_REFILLS_LABEL + index, amount);
    }

    public void setOtherRefills(String index, String amount) throws IOException, DocumentException {
        setField(OTHER_REFILLS_LABEL + index, amount);
    }

    public void setOtherRefills(int index, int amount) throws IOException, DocumentException {
        setField(OTHER_REFILLS_LABEL + index, amount);
    }

    public void setOtherRefills(int index, String amount) throws IOException, DocumentException {
        setField(OTHER_REFILLS_LABEL + index, amount);
    }

    public void setDDTUsed(int amount) throws IOException, DocumentException {
        setField(DDT_USED_LABEL, amount);
    }

    public void setBaythroidUsed(int amount) throws IOException, DocumentException {
        setField(BAYTHROID_USED_LABEL, amount);
    }

    public void setKOthrineUsed(int amount) throws IOException, DocumentException {
        setField(KOTHRINE_USED_LABEL, amount);
    }

    public void setDDTData(int index, SprayData data) throws IOException, DocumentException {
        setDDTRooms(index, data.getSprayedRooms());
        setDDTShelters(index, data.getSprayedShelters());
        setDDTUnsprayed(index, data.getUnsprayedRooms());
        setDDTRefills(index, data.getCansRefilled());
    }

    public void setDDTData(String index, SprayData data) throws IOException, DocumentException {
        setDDTRooms(index, data.getSprayedRooms());
        setDDTShelters(index, data.getSprayedShelters());
        setDDTUnsprayed(index, data.getUnsprayedRooms());
        setDDTRefills(index, data.getCansRefilled());
    }

    public void setOtherData(int index, SprayData data) throws IOException, DocumentException {
        setOtherRooms(index, data.getSprayedRooms());
        setOtherShelters(index, data.getSprayedShelters());
        setOtherUnsprayed(index, data.getUnsprayedRooms());
        setOtherRefills(index, data.getCansRefilled());
    }

    public void setOtherData(String index, SprayData data) throws IOException, DocumentException {
        setOtherRooms(index, data.getSprayedRooms());
        setOtherShelters(index, data.getSprayedShelters());
        setOtherUnsprayed(index, data.getUnsprayedRooms());
        setOtherRefills(index, data.getCansRefilled());
    }

}
