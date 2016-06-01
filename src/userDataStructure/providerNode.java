package userDataStructure;

import serviceDataStructure.serviceLogNode;
import serviceDataStructure.servicesInfoList;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class providerNode extends userNode implements Serializable{
    private String providerName; //the provider's name; 25 characters
    private String providerStreet; //the provider's street; 25 characters
    private String providerCity; //the provider's city; 14 characters
    private String providerState; //the provider's state; 2 letters
    private String providerZip; //the provider's street; 5 digit integer
    private int totalConsultations; //the provider's total consultations over all time; 3 digit integer
    private servicesInfoList Head;

    public providerNode(int providerNumber){
        super(providerNumber);
    }

    public providerNode(int providernumber, String providername, String providerstreet,
                        String providercity, String providerstate, String providerzip){
        super(providernumber);
        providerName = providername;
        providerStreet = providerstreet;
        providerCity = providercity;
        providerState = providerstate;
        providerZip = providerzip;
        totalConsultations = 0;
    }

    public void addService(serviceLogNode toInsert){
        Head.AddNode(toInsert);
    }

    public void setName(String toName){
        providerName = toName;
    }

    public void setStreet(String toStreet){
        providerStreet = toStreet;
    }

    public void setCity(String toCity){
        providerCity = toCity;
    }

    public void setState(String toState){
        providerState = toState;
    }

    public void setZip(String toZip){
        providerZip = toZip;
    }

    public String getName(){
        return providerName;
    }

    public String getStreet(){
        return providerStreet;
    }

    public String getCity(){
        return providerCity;
    }

    public String getState(){
        return providerState;
    }

    public String getZip(){
        return providerZip;
    }

    public int getTotalConsultations(){
        return totalConsultations;
    }

    @Override
    public int getUserCategory() { //returns the user's category as an int.
        return 2;
    }

    @Override
    public String returnInfo(){
        return "Member Name: " + providerName
                +"\nMember ID: " + getUserNumber()
                +"\nMember Address:\n" + providerStreet
                +"\n" + providerCity + ", " + providerState + " " + providerZip
                +"\nTotal Consultations: " + totalConsultations;
    }
}
