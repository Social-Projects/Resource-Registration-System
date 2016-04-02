package org.registrator.community.dto;

import java.util.Date;
import java.util.List;

public class ExtractDTO {
    private int resourceId;
    private String destination;
    private String objectName;
    private String objectClass;
    private String subObjectClass;
    private List<Double> totalCoordinates;
    private String linearObjectSize="відомості відсутні";
    private String totalSquareOfObject="відомості відсутні";
    private String objectPerimetr="відомості відсутні";
    private String weight="відомості відсутні";
    private String objectCapacity="відомості відсутні";;
    private String reasonForInclusion;
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
    private String requestDay;
    private String requestMonthNumber;
    private String requestMonth;
    private String requestYear;
    private Date dateCurrent=new Date();




    @SuppressWarnings("deprecation")
    public String getCurrentDay() {
         int date =dateCurrent.getDate();
        if(date<10)return "0"+date;
        return String.valueOf(date);
    }

    @SuppressWarnings("deprecation")
    public String getCurrentMonth() {
        return currentMonth(String.valueOf(dateCurrent.getMonth()));
    }

    @SuppressWarnings("deprecation")
    public String getCurrentYear() {
        String date = String.valueOf(dateCurrent.getYear());
        char parsedDate[] = date.toCharArray();
        date = "" + parsedDate[1] + parsedDate[2];
        return date;
    }

    public String getCurrentMonthNumber(){
        String date=new String();
        int number=dateCurrent.getMonth()+1;
        if(number<10){
            return date="0"+number;
        }

        return date=""+number;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getObjectName() {
        return objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    public String getObjectClass() {
        return objectClass;
    }
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }
    public String getSubObjectClass() {
        return subObjectClass;
    }
    public void setSubObjectClass(String subObjectClass) {
        this.subObjectClass = subObjectClass;
    }
    public List<Double> getTotalCoordinates() {
        return totalCoordinates;
    }
    public void setTotalCoordinates(List<Double> totalCoordinates) {
        this.totalCoordinates = totalCoordinates;
    }
    public String getLinearObjectSize() {
        return linearObjectSize;
    }
    public void setLinearObjectSize(String linearObjectSize) {
        this.linearObjectSize = linearObjectSize;
    }
    public String getTotalSquareOfObject() {
        return totalSquareOfObject;
    }
    public void setTotalSquareOfObject(String totalSquareOfObject) {
        this.totalSquareOfObject = totalSquareOfObject;
    }
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getObjectPerimetr() {
        return objectPerimetr;
    }
    public void setObjectPerimetr(String objectPerimetr) {
        this.objectPerimetr = objectPerimetr;
    }
    public String getObjectCapacity() {
        return objectCapacity;
    }
    public void setObjectCapacity(String objectCapacity) {
        this.objectCapacity = objectCapacity;
    }
    public String getReasonForInclusion() {
        return reasonForInclusion;
    }
    public void setReasonForInclusion(String reasonForInclusion) {
        this.reasonForInclusion = reasonForInclusion;
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



    public String getRequestDay() {
        return requestDay;
    }
    public void setRequestDay(String requestDay) {
        this.requestDay = requestDay;
    }
    public String getRequestMonthNumber() {
        return requestMonthNumber;
    }
    public void setRequestMonthNumber(String requestMonthNumber) {
        this.requestMonthNumber = requestMonthNumber;
    }
    public String getRequestMonth() {
        return requestMonth;
    }
    public void setRequestMonth(String requestMonth) {
        this.requestMonth = requestMonth;
    }
    public String getRequestYear() {
        return requestYear;
    }
    public void setRequestYear(String requestYear) {
        this.requestYear = requestYear;
    }



    private String currentMonth(String month) {

        if (month.equals("0"))
            return "cічня";
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
