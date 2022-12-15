package com.mobileconnection.data;

public class MobileCompany {
    private String name;
    private int usersNumber;
    public MobileCompany(String name) {
        setName(name);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getUsersNumber() { return usersNumber; }
    public void setUsersNumber(int usersNumber) { this.usersNumber = usersNumber; }
}