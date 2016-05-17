package serviceDataStructure;

import javafx.scene.text.Text;
import userDataStructure.memberNode;
import userDataStructure.providerNode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class serviceNode implements Serializable{
    private serviceNode Next; //A reference to the next service node in the data structure.
    private providerNode Provider; //the provider involved in this service transaction.
    private memberNode Member; //the member involved in this service transaction.
    private String manualDate; //A date entered by the provider denoting the time that the service was provided.
    private Date entryDate; //the time and date at which this service was logged in the system; automated.
    private int serviceCode; //the code for the service in question.
    private float Fee; //the fee associated with this service.
    private String Comments; //any comments that the provider wishes to add.

    public serviceNode(providerNode provider, memberNode member, String manualdate, int servicecode, String comments){
        Provider = provider;
        Member = member;
        manualDate = manualdate;
        entryDate = new Date();
        serviceCode = servicecode;
        Comments = comments;
    }

    //returns data in this node as an easily-readable string.
    public String returnAsString(){
        return
                "Date logged: " + entryDate.toString()
                        + "\nDate of Service: " + manualDate
                        + "\nProvider: " + Provider.getUserNumber()
                        + "\nMember: " + Member.getUserNumber()
                        + "\nService Code: " + serviceCode
                        + "\nComments: " + Comments;
    }

    //returns the entirety of the structure as a string to the end of writing reports.
    public String returnStructureAsString(){
        if(Next == null)
            return returnAsString();
        return returnAsString() + "\n\n" + Next.returnAsString();
    }

    //sets the value of the next reference. Most other methods for altering or reading
    //this data structure are unnecessary, as it will be accessed mostly via references
    //in the user data structure.
    public void setNext(serviceNode next){
        Next = next;
    }
}
