package model;



public class User {

    private String username;
    private String firstname;
    private String lastname;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)


    }

    public User(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname=firstname;
        this.lastname=lastname;
    }

}
