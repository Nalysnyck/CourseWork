package com.mobileconnection.data;

public class TariffPackage extends Tariff {
    private String daysOfUsing;

    public TariffPackage() {}

    public TariffPackage(String name, double costPerMonth, int callMinutes, int callMinutesOnOtherNumbers, int callMinutesInRoaming, int traffic, int freeSmsNumber, int discount, String appsWithoutTraffic, int usersNumber, String daysOfUsing) {
        super(name, costPerMonth, callMinutes, callMinutesOnOtherNumbers, callMinutesInRoaming, traffic, freeSmsNumber, discount, appsWithoutTraffic, usersNumber);
        this.daysOfUsing = daysOfUsing;
    }

    public String getDaysOfUsing() {return daysOfUsing;}
    public void setDaysOfUsing(String daysOfUsing) {this.daysOfUsing = daysOfUsing;}
}