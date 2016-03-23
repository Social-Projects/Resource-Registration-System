package org.registrator.community.dto;

public class PointAreaDTO {
    
    private int orderNumber;
    private int latitudeDegrees;
    private int latitudeMinutes;
    private double latitudeSeconds;
    private int longitudeDegrees;
    private int longitudeMinutes;
    private double longitudeSeconds;
    
    public PointAreaDTO() {
        
    }
    
    public PointAreaDTO(int orderNumber, int latitudeDegrees,
            int latitudeMinutes, double latitudeSeconds, int longitudeDegrees,
            int longitudeMinutes, double longitudeSeconds) {
        this.orderNumber = orderNumber;
        this.latitudeDegrees = latitudeDegrees;
        this.latitudeMinutes = latitudeMinutes;
        this.latitudeSeconds = latitudeSeconds;
        this.longitudeDegrees = longitudeDegrees;
        this.longitudeMinutes = longitudeMinutes;
        this.longitudeSeconds = longitudeSeconds;
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    public int getLatitudeDegrees() {
        return latitudeDegrees;
    }
    public void setLatitudeDegrees(int latitudeDegrees) {
        this.latitudeDegrees = latitudeDegrees;
    }
    public int getLatitudeMinutes() {
        return latitudeMinutes;
    }
    public void setLatitudeMinutes(int latitudeMinutes) {
        this.latitudeMinutes = latitudeMinutes;
    }
    public double getLatitudeSeconds() {
        return latitudeSeconds;
    }
    public void setLatitudeSeconds(double latitudeSeconds) {
        this.latitudeSeconds = latitudeSeconds;
    }
    public int getLongitudeDegrees() {
        return longitudeDegrees;
    }
    public void setLongitudeDegrees(int longitudeDegrees) {
        this.longitudeDegrees = longitudeDegrees;
    }
    public int getLongitudeMinutes() {
        return longitudeMinutes;
    }
    public void setLongitudeMinutes(int longitudeMinutes) {
        this.longitudeMinutes = longitudeMinutes;
    }
    public double getLongitudeSeconds() {
        return longitudeSeconds;
    }
    public void setLongitudeSeconds(double longitudeSeconds) {
        this.longitudeSeconds = longitudeSeconds;
    }

    public void setLatitudeValues(Double latitude) {
        this.latitudeDegrees = (int) Math.floor(latitude);
        this.latitudeMinutes = (int) Math.floor((latitude - latitudeDegrees)*60);
        this.latitudeSeconds = ((latitude-latitudeDegrees)*60 - latitudeMinutes)*60;
    }
    public void setLongitudeValues (Double longitude) {
        this.longitudeDegrees = (int) Math.floor(longitude);
        this.longitudeMinutes = (int) Math.floor((longitude - longitudeDegrees)*60);
        this.longitudeSeconds = ((longitude-longitudeDegrees)*60 - longitudeMinutes)*60;
    }
    
    public Double getDecimalLatitude(){
        return this.latitudeDegrees + this.latitudeMinutes /60d
                + this.latitudeSeconds /3600d;
    }
    
    public Double getDecimalLongitude(){
        return this.longitudeDegrees + this.longitudeMinutes / 60d
                + this.longitudeSeconds / 3600d;
    }

    @Override
    public String toString() {

        return "\n" + "Номер точки полігону"+orderNumber+
                "Номер точки полігону: широта:"+"градуси: " + latitudeDegrees+
        "мн"+ latitudeMinutes+
        "сек " + latitudeSeconds+ "\n " + "*****"+
        "град " + longitudeDegrees+
        "мн "+longitudeMinutes+
        "сек "+longitudeSeconds;
    }
    
}

