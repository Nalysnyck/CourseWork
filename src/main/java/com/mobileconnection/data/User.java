package com.mobileconnection.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String login;
    private String password;
    private boolean isAdministrator;
    private String isAdministratorString;
    private boolean hasEditPermission;
    private String hasEditPermissionString;
    private String dateRegistration;
    private String dateLastSeen;
    private int avatarId;

    public User(String login, String password, boolean isAdministrator, boolean hasEditPermission, String dateRegistration, String dateLastSeen, int avatarId) {
        setLogin(login);
        setPassword(password);
        setAdministrator(isAdministrator);
        setHasEditPermission(hasEditPermission);
        setDateRegistration(dateRegistration);
        setDateLastSeen(dateLastSeen);
        setAvatarId(avatarId);
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public boolean isAdministrator() { return isAdministrator; }
    public boolean hasEditPermission() { return hasEditPermission; }

    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
        isAdministratorString = isAdministrator ? "Так" : "Ні";
    }
    public void setHasEditPermission(boolean hasEditPermission1) {
        hasEditPermission = hasEditPermission1;
        hasEditPermissionString = hasEditPermission ? "Так" : "Ні";
    }

    public String getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(String dateRegistration) {this.dateRegistration = dateRegistration;}

    public String getDateLastSeen() {
        return dateLastSeen;
    }

    public String getDateLastSeenDifference() {
        if (dateLastSeen.equals("")) return "У мережі";
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date1 = null, date2 = null;

        try {
            date1 = formatter.parse(dateLastSeen);
            date2 = new Date();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date1 == null || date2 == null) {

            return "Немає даних...";
        }
        long difference = (date2.getTime() - date1.getTime()) / 1000;
        long seconds = difference % 60;
        long minutes = difference / 60 % 60;
        long hours = difference / (60 * 60) % 24;
        long days = difference / (24 * 60 * 60);

        if (days > 0)
            return validateDateString(days, "день", "дні", "днів");
        if (hours > 0)
            return validateDateString(hours, "година", "години", "годин");
        if (minutes > 0)
            return validateDateString(minutes, "хвилина", "хвилини", "хвилин");
        return validateDateString(seconds, "секунда", "секунди", "секунд");
    }

    private String validateDateString(long value, String firstForm, String secondForm, String thirdForm) {
        if (value % 10 == 1 && value / 10 != 1)
            return "У мережі " + value + " " + firstForm + " тому";
        else if (value % 10 > 1 && value % 10 < 5 && value / 10 != 1)
            return "У мережі " + value + " " + secondForm + " тому";
        else
            return "У мережі " + value + " " + thirdForm + " тому";
    }

    public void setDateLastSeen(String dateLastSeen) {
        this.dateLastSeen = dateLastSeen;
    }

    public String getIsAdministratorString() {
        return isAdministratorString;
    }

    public void setIsAdministratorString(String isAdministratorString) {
        this.isAdministratorString = isAdministratorString;
    }

    public String getHasEditPermissionString() {
        return hasEditPermissionString;
    }

    public void setHasEditPermissionString(String hasEditPermissionString) {
        this.hasEditPermissionString = hasEditPermissionString;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }
}
