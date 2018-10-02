package edu.gatech.cs2340.donationtracker;

public class User {
    private String name;
    private String username;
    private String password;
    private AccountType accountType;

    public User(String username, String password) {
        this("No Name", username, password, AccountType.USER);
    }

    public User(String name, String username, String password, AccountType accountType) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() { return this.name;}

    public void setName(String name) { this.name = name;}

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false;}
        if (!(o instanceof User)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        return this.getUsername().equals(((User) o).getUsername());

    }

}
