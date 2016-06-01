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

    public serviceLogNode(providerNode provider, memberNode member, String manualdate,
                          providerService service, String comments){
        Provider = provider;
        Member = member;
        manualDate = manualdate;
        entryDate = new Date();
        Service = service;
        Comments = comments;
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

    //returns the entirety of the structure as a string to the end of writing reports.
    public String returnStructureAsString(){
        if(Next == null)
            return returnAsString();
        return returnAsString() + "\n\n" + Next.returnStructureAsString();
    }





    //sets the value of the next reference. Most other methods for altering or reading
    //this data structure are unnecessary, as it will be accessed mostly via references
    //in the user data structure.
    public void setNext(serviceLogNode next){
        Next = next;
    }
}
