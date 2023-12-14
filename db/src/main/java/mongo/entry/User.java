package mongo.entry;

public class User {
    private String name;
    private String contact;
    private String dob;
    private 

    class Address{


    }

    public User(String name, String contact, String dob) {
        this.name = name;
        this.contact = contact;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{" +
               "name='" + name + '\'' +
               ", contact='" + contact + '\'' +
               ", dob='" + dob + '\'' +
               '}';
    }
}
