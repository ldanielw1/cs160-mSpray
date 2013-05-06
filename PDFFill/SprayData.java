public class SprayData {
    private int sprayedRooms;
    private int sprayedShelters;
    private int unsprayedRooms;
    private int cansRefilled;

    SprayData() {
        sprayedRooms = 0;
        sprayedShelters = 0;
        unsprayedRooms = 0;
        cansRefilled = 0;
    }

    public int getSprayedRooms() {
        return sprayedRooms;
    }

    public int getSprayedShelters() {
        return sprayedShelters;
    }

    public int getUnsprayedRooms() {
        return unsprayedRooms;
    }

    public int getCansRefilled() {
        return cansRefilled;
    }

    public void updateSprayedRooms(int updateRooms) {
        this.sprayedRooms += updateRooms;
    }

    public void updateSprayedShelters(int updateShelters) {
        this.sprayedShelters += updateShelters;
    }

    public void updateUnsprayedRooms(int updateRooms) {
        this.unsprayedRooms += updateRooms;
    }

    public void updateCansRefilled(int updateCans) {
        this.cansRefilled += updateCans;
    }

    public void update(SprayData data) {
        this.updateSprayedRooms(data.getSprayedRooms());
        this.updateSprayedShelters(data.getSprayedShelters());
        this.updateUnsprayedRooms(data.getUnsprayedRooms());
        this.updateCansRefilled(data.getCansRefilled());
    }
}
