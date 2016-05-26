package serviceDataStructure;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class weekListNode implements Serializable{
    private weekListNode Next; //connection to the next element in the list
    //private DateFormat startDateFormat; //formatting class for date storage
    private Date startDate; //class for date storage
    private serviceLogNode serviceHead; //head of service substructure; stores service transactions throughout given week

    public weekListNode(){
        Next = null;
        startDate = new Date();
        //startDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public weekListNode(weekListNode next){
        Next = next;
        startDate = new Date();
    }

    //returns a boolean denoting whether or not a week has elapsed since the initialization of the object
    //that this method is called from.
    public boolean isOfCurrentWeek(){
        Date currentDate = new Date();
        return currentDate.getHours() < 168 + startDate.getHours();
    }

    //adds a new service at the head of the service substructure.
    public void addService(serviceLogNode toAdd){
        toAdd.setNext(serviceHead);
        serviceHead = toAdd;
    }

}
