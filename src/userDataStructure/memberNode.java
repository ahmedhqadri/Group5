package userDataStructure;

import serviceDataStructure.serviceLogNode;
import serviceDataStructure.servicesInfoList;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class memberNode extends userNode implements Serializable{
    private String memberName; //the member's name; 25 characters
    private String memberStreet; //the member's street; 25 characters
    private String memberCity; //the member's city; 14 characters
    private String memberState; //the member's state; 2 letters
    private String memberZip; //the member's street; 5 integer digit string
    private boolean Suspended; //whether or not the member has been suspended by a manager
    private servicesInfoList Head;

    public memberNode(int memberNumber){
        super(memberNumber);
        Suspended = false;
    }

    public memberNode(int membernumber, String membername, String memberstreet,
                      String membercity, String memberstate, String memberzip){
        super(membernumber);
        memberName = membername;
        memberStreet = memberstreet;
        memberCity = membercity;
        memberState = memberstate;
        memberZip = memberzip;
        Suspended = false;
    }

    public void addService(serviceLogNode toInsert){
        Head.AddNode(toInsert);
    }


    public void setName(String toName){
        memberName = toName;
    }

    public void setStreet(String toStreet){
        memberStreet = toStreet;
    }

    public void setCity(String toCity){
        memberCity = toCity;
    }

    public void setState(String toState){
        memberState = toState;
    }

    public void setZip(String toZip){
        memberZip = toZip;
    }

    public String getName(){
        return memberName;
    }

    public String getStreet(){
        return memberStreet;
    }

    public String getCity(){
        return memberCity;
    }

    public String getState(){
        return memberState;
    }

    public String getZip(){
        return memberZip;
    }

    public boolean isSuspended(){
        return Suspended;
    }

    public void setSuspended(boolean toSet){
        Suspended = toSet;
    }

    public String returnInfo(){
        return "Member Name: " + memberName
                +"\nMember ID: " + getUserNumber()
                +"\nMember Address:\n" + memberStreet
                +"\n" + memberCity + ", " + memberState + " " + memberZip
                +"\nSuspended: " + Suspended;
    }

    @Override
    public int getUserCategory() { //returns the user's category as an int.
        return 1;
    }
}
