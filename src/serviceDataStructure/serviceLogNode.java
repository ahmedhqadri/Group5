package serviceDataStructure;

import userDataStructure.memberNode;
import userDataStructure.providerNode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class serviceLogNode implements Serializable{
    private serviceLogNode Next; //A reference to the next service node in the data structure.
    private providerNode Provider; //the provider involved in this service transaction.
    private memberNode Member; //the member involved in this service transaction.
    private String manualDate; //A date entered by the provider denoting the time that the service was provided.
    private Date entryDate; //the time and date at which this service was logged in the system; automated.
    private providerService Service; //the service that was logged.
    private String Comments; //any comments that the provider wishes to add.
    private int weekNum; //week number for this service (supplied by weekListNode)
    private serviceLogNode nextMember; //next unique member in the list
    private serviceLogNode nextProvider; //next unique provider in the list

    public serviceLogNode(providerNode provider, memberNode member, String manualdate,
                          providerService service, String comments){
        Provider = provider;
        Member = member;
        manualDate = manualdate;
        entryDate = new Date();
        Service = service;
        Comments = comments;
        Member.addService(this);
        Provider.addService(this);
        weekNum = 0;
        nextMember = null;
        nextProvider = null;
    }


    //returns data in this node as an easily-readable string.
    public String returnAsString(){
        return
                "Date logged: " + entryDate.toString()
                        + "\nDate of Service: " + manualDate
                        + "\nProvider: " + Provider.getUserNumber()
                        + "\nMember: " + Member.getUserNumber()
                        + "\nService Code: " + Service.getID()
                        + "\nService Fee: " + Service.getCost()
                        + "\nComments: " + Comments;
    }

    //returns the data needed for the member report
    public String memberData(){
        return
                "Date of Service: " + manualDate
                    + "\nProvider: " + Provider.getName()
                    + "\nService: " + Service.getName();
    }
    //returns the data needed for the provider report
    public String providerData(){
        return
                "Date logged: " + entryDate.toString()
                        + "\nDate of Service: " + manualDate
                        + "\nMember: " + Member.getName()
                        + "\nMember ID: " + Member.getUserNumber()
                        + "\nService Code: " + Service.getID()
                        + "\nService Fee: " + Service.getCost();
    }
    //returns the amount to be paid to the provider for the service
    public float providerFee(){
        return Service.getFee();
    }

    //returns the entirety of the structure as a string to the end of writing reports.
    public String returnStructureAsString(){
        if(Next == null)
            return returnAsString();
        //test
        return returnAsString() + "\n\n" + Next.returnStructureAsString();
    }

    //sets the value of the next reference. Most other methods for altering or reading
    //this data structure are unnecessary, as it will be accessed mostly via references
    //in the user data structure.
    public void setNext(serviceLogNode next){
        Next = next;

        //This node will now be the start of the provider/member lists
        //but we still need to ensure that each member/provider is only
        //in the list once
        nextProvider = rebuildProviderList(Provider);
        nextMember = rebuildMemberList(Member);
    }

    //used to ensure that the provider list does not contain a second reference
    //to the provider in the newly added node.  This will disconnect the second
    //reference from the list if found.
    private serviceLogNode rebuildProviderList(providerNode newProvider){
        if(Next == null || newProvider == null)
            return null;

        serviceLogNode current = Next;
        serviceLogNode previous = null;
        serviceLogNode newNext = Next;
        boolean foundMatch = false;
        while(current != null && !foundMatch){
            if(current.Provider.getUserNumber() == newProvider.getUserNumber()){
                foundMatch = true;
                if(previous == null) { //newly added serviceLogNode will become the new provider list head
                    newNext = current.nextProvider;
                    current.nextProvider = null;
                }
                //need to pass the provider list over this node so that the newly added
                //node's provider only appears once in the list
                else{
                    previous.nextProvider = current.nextProvider;
                    current.nextProvider = null;
                }
            }
            previous = current;
            current = current.nextProvider;
        }
        return newNext;
    }

    //same as rebuildProviderList but for the member
    private serviceLogNode rebuildMemberList(memberNode newMember){
        if(Next == null || newMember == null)
            return null;

        serviceLogNode current = Next;
        serviceLogNode previous = null;
        serviceLogNode newNext = Next;
        boolean foundMatch = false;
        while(current != null && !foundMatch){
            if(current.Member.getUserNumber() == newMember.getUserNumber()){
                foundMatch = true;
                if(previous == null) {
                    newNext = current.nextMember;
                    current.nextMember = null;
                }
                else{
                    previous.nextMember = current.nextMember;
                    current.nextMember = null;
                }
            }
            previous = current;
            current = current.nextMember;
        }
        return newNext;
    }

    //getter/setter for weekNum since weekNum is set by the weekListNode and
    //getWeekNum is used for report generation by the member/provider nodes
    public int getWeekNum() {return weekNum;}
    public void setWeekNum(int weekNum) {this.weekNum = weekNum;}

    //used by the printWeeklyReports function to walk the member and provider lists
    public serviceLogNode getNextProvider() {return nextProvider;}
    public serviceLogNode getNextMember() {return nextMember;}
    public providerNode getProvider(){ return Provider; }
    public memberNode getMember(){ return Member; }
}