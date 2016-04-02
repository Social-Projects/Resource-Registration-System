package org.registrator.community.dto;

import java.util.Date;

public class MandatToExtractDTO {

    private String destinationOfFile;
    private String firstNameOfUser;
    private String lastNameOfUser;
    private String middleNameOfUser;
    private String zipcodeOfUser;
    private String countryOfUser;
    private String cityOfUser;
    private String streetNameOfUser;
    private String streetNumberOfUser;
    private String homeNumberOfUser;
    private String currentYear;
    private String registratorFirtstName;
    private String registratorLastName;
    private String registratorMiddleName;
    private String registratorZipcode;
    private String registratorCountry;
    private String registratorCity;
    private String registratorStreetName;
    private String registratorStreetNumber;
    private String registratorHomeNumber;
    private String objectNumber;

    private Date dateCurrent=new Date();

    public String getHomeNumberOfUser() {
        return homeNumberOfUser;
    }
    public void setHomeNumberOfUser(String homeNumberOfUser) {
        this.homeNumberOfUser = homeNumberOfUser;
    }

    public String getDestinationOfFile() {
        return destinationOfFile;
    }
    public void setDestinationOfFile(String destinationOfFile) {
        this.destinationOfFile = destinationOfFile;
    }
    public String getFirstNameOfUser() {
        return firstNameOfUser;
    }
    public void setFirstNameOfUser(String firstNameOfUser) {
        this.firstNameOfUser = firstNameOfUser;
    }
    public String getLastNameOfUser() {
        return lastNameOfUser;
    }
    public void setLastNameOfUser(String lastNameOfUser) {
        this.lastNameOfUser = lastNameOfUser;
    }
    public String getMiddleNameOfUser() {
        return middleNameOfUser;
    }
    public void setMiddleNameOfUser(String middleNameOfUser) {
        this.middleNameOfUser = middleNameOfUser;
    }
    public String getZipcodeOfUser() {
        return zipcodeOfUser;
    }
    public void setZipcodeOfUser(String zipcodeOfUser) {
        this.zipcodeOfUser = zipcodeOfUser;
    }
    public String getCountryOfUser() {
        return countryOfUser;
    }
    public void setCountryOfUser(String countryOfUser) {
        this.countryOfUser = countryOfUser;
    }
    public String getCityOfUser() {
        return cityOfUser;
    }
    public void setCityOfUser(String cityOfUser) {
        this.cityOfUser = cityOfUser;
    }
    public String getStreetNameOfUser() {
        return streetNameOfUser;
    }
    public void setStreetNameOfUser(String streetNameOfUser) {
        this.streetNameOfUser = streetNameOfUser;
    }
    public String getStreetNumberOfUser() {
        return streetNumberOfUser;
    }
    public void setStreetNumberOfUser(String streetNumberOfUser) {
        this.streetNumberOfUser = streetNumberOfUser;
    }
    @SuppressWarnings("deprecation")
    public String getCurrentDay() {
        return String.valueOf(dateCurrent.getDate());
    }

    @SuppressWarnings("deprecation")
    public String getCurrentMonth() {
        return currentMonth(String.valueOf(dateCurrent.getMonth()));
    }

    @SuppressWarnings("deprecation")
    public String getCurrentYear() {
        String date = String.valueOf(dateCurrent.getYear());
        char parsedDate[] = date.toCharArray();
        currentYear= "" + parsedDate[1] + parsedDate[2];
        return currentYear;
    }




    public String getRegistratorFirtstName() {
        return registratorFirtstName;
    }
    public void setRegistratorFirtstName(String registratorFirtstName) {
        this.registratorFirtstName = registratorFirtstName;
    }
    public String getRegistratorLastName() {
        return registratorLastName;
    }
    public void setRegistratorLastName(String registratorLastName) {
        this.registratorLastName = registratorLastName;
    }
    public String getRegistratorMiddleName() {
        return registratorMiddleName;
    }
    public void setRegistratorMiddleName(String registratorMiddleName) {
        this.registratorMiddleName = registratorMiddleName;
    }
    public String getRegistratorZipcode() {
        return registratorZipcode;
    }
    public void setRegistratorZipcode(String registratorZipcode) {
        this.registratorZipcode = registratorZipcode;
    }
    public String getRegistratorCountry() {
        return registratorCountry;
    }
    public void setRegistratorCountry(String registratorCountry) {
        this.registratorCountry = registratorCountry;
    }
    public String getRegistratorCity() {
        return registratorCity;
    }
    public void setRegistratorCity(String registratorCity) {
        this.registratorCity = registratorCity;
    }
    public String getRegistratorStreetName() {
        return registratorStreetName;
    }
    public void setRegistratorStreetName(String registratorStreetName) {
        this.registratorStreetName = registratorStreetName;
    }
    public String getRegistratorStreetNumber() {
        return registratorStreetNumber;
    }
    public void setRegistratorStreetNumber(String registratorStreetNumber) {
        this.registratorStreetNumber = registratorStreetNumber;
    }
    public String getRegistratorHomeNumber() {
        return registratorHomeNumber;
    }
    public void setRegistratorHomeNumber(String registratorHomeNumber) {
        this.registratorHomeNumber = registratorHomeNumber;
    }
    public String getObjectNumber() {
        return objectNumber;
    }
    public void setObjectNumber(String objectNumber) {
        this.objectNumber = objectNumber;
    }


    private String currentMonth(String month) {

        if (month.equals("0"))
            return "січня";
        else if (month.equals("1"))
            return "лютого";
        else if (month.equals("2"))
            return "березня";
        else if (month.equals("3"))
            return "квітня";
        else if (month.equals("4"))
            return "травня";
        else if (month.equals("5"))
            return "червня";
        else if (month.equals("6"))
            return "липня";
        else if (month.equals("7"))
            return "серпня";
        else if (month.equals("8"))
            return "вересня";
        else if (month.equals("9"))
            return "жовтня";
        else if (month.equals("10"))
            return "листопада";
        else if (month.equals("11"))
            return "грудня";
        return "UNDEFINED";
    }




}
