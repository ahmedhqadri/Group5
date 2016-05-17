package userDataStructure;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class memberNode extends userNode implements Serializable{
    private String memberName; //the member's name; 25 characters
    private String memberStreet; //the member's street; 25 characters
    private String memberCity; //the member's city; 14 characters
    private String memberState; //the member's state; 2 letters
    private int memberZip; //the member's street; 5 digit integer
    private boolean Suspended; //whether or not the member has been suspended by a manager

    public memberNode(int memberNumber){
        super(memberNumber);
        Suspended = false;
    }

    public memberNode(int membernumber, String membername, String memberstreet,
                      String membercity, String memberstate, int memberzip){
        super(membernumber);
        memberName = membername;
        memberStreet = memberstreet;
        memberCity = membercity;
        memberState = memberstate;
        memberZip = memberzip;
        Suspended = false;
    }

    public boolean isSuspended(){
        return Suspended;
    }

    public void setSuspended(boolean toSet){
        Suspended = toSet;
    }

    public String getMemberInfo(){
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
