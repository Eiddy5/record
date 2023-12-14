package mongo.entry;

public class Address {
    private String building;
    private String pincode;
    private String city;
    private String state;

    public Address(String building, String pincode, String city, String state) {
        this.building = building;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
    }

    public Address() {
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
