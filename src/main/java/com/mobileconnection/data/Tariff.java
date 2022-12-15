package com.mobileconnection.data;

public class Tariff {
    protected String name;
    protected double costPerMonth;
    protected int callMinutes;
    protected String callMinutesString;
    protected int callMinutesOnOtherNumbers;
    protected String callMinutesOnOtherNumbersString;
    protected int callMinutesInRoaming;
    protected String callMinutesInRoamingString;
    protected int traffic;
    protected String trafficString;
    protected int freeSmsNumber;
    protected String freeSmsNumberString;
    protected int discount;
    protected String appsWithoutTraffic;
    protected int usersNumber;

    public Tariff() {}
    public Tariff(String name, double costPerMonth, int callMinutes, int callMinutesOnOtherNumbers, int callMinutesInRoaming, int traffic, int freeSmsNumber, int discount, String appsWithoutTraffic, int usersNumber) {
        setName(name);
        setCostPerMonth(costPerMonth);
        setCallMinutes(callMinutes);
        callMinutesString = (callMinutes == -1 ? "Безлім" : String.valueOf(getCallMinutes()));
        setCallMinutesOnOtherNumbers(callMinutesOnOtherNumbers);
        callMinutesOnOtherNumbersString = (callMinutesOnOtherNumbers == -1 ? "Безлім" : String.valueOf(getCallMinutesOnOtherNumbers()));
        setCallMinutesInRoaming(callMinutesInRoaming);
        callMinutesInRoamingString = (callMinutesInRoaming == -1 ? "Безлім" : String.valueOf(getCallMinutesInRoaming()));
        setTraffic(traffic);
        trafficString = (traffic == -1 ? "Безлім" : String.valueOf(getTraffic()));
        setFreeSmsNumber(freeSmsNumber);
        freeSmsNumberString = (freeSmsNumber == -1 ? "Безлім" : String.valueOf(getFreeSmsNumber()));
        setDiscount(discount);
        setAppsWithoutTraffic(appsWithoutTraffic);
        setUsersNumber(usersNumber);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getCostPerMonth() {return costPerMonth;}
    public void setCostPerMonth(double costPerMonth) {this.costPerMonth = costPerMonth;}

    public int getCallMinutes() {return callMinutes;}
    public void setCallMinutes(int callMinutes) {this.callMinutes = callMinutes;}

    public int getCallMinutesOnOtherNumbers() {return callMinutesOnOtherNumbers;}
    public void setCallMinutesOnOtherNumbers(int callMinutesOnOtherNumbers) {this.callMinutesOnOtherNumbers = callMinutesOnOtherNumbers;}

    public int getCallMinutesInRoaming() {return callMinutesInRoaming;}
    public void setCallMinutesInRoaming(int callMinutesInRoaming) {this.callMinutesInRoaming = callMinutesInRoaming;}

    public int getTraffic() {return traffic;}
    public void setTraffic(int traffic) {this.traffic = traffic;}

    public int getFreeSmsNumber() {return freeSmsNumber;}
    public void setFreeSmsNumber(int freeSmsNumber) {this.freeSmsNumber = freeSmsNumber;}

    public int getDiscount() {return discount;}
    public void setDiscount(int discount) {this.discount = discount;}

    public String getAppsWithoutTraffic() {return appsWithoutTraffic;}
    public void setAppsWithoutTraffic(String appsWithoutTraffic) {this.appsWithoutTraffic = appsWithoutTraffic;}

    public int getUsersNumber() {return usersNumber;}
    public void setUsersNumber(int usersNumber) {this.usersNumber = usersNumber;}

    public String getCallMinutesString() {return callMinutesString;}
    public String getCallMinutesOnOtherNumbersString() {return callMinutesOnOtherNumbersString;}
    public String getCallMinutesInRoamingString() {return callMinutesInRoamingString;}
    public String getTrafficString() {return trafficString;}
    public String getFreeSmsNumberString() {return freeSmsNumberString;}
}
