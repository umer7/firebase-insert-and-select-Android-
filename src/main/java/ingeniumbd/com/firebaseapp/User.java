package ingeniumbd.com.firebaseapp;

public class User {

    private String name;
    private String Address;
    private double weight;
    private String email;


    public User(){}
    public User(String n, String c, double w, String e){
        this.name=n;
        this.Address=c;
        this.weight=w;
        this.email=e;

    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return Address;
    }

    public double getWeight() {
        return weight;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public String toString() {
        return name +" "+ Address +" " +weight+" " +email;
    }
}
