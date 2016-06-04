package serviceDataStructure;

import GUI.GUIRoot;
import userDataStructure.memberNode;
import userDataStructure.providerNode;
import userDataStructure.userHashTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Spaghetti on 4/25/2016.
 */
//a LLL node class that represents a week of services. Contains a secondary LLL containing the actual services.
public class weekListNode implements Serializable{
    private weekListNode Next; //connection to the next element in the list
    //private DateFormat startDateFormat; //formatting class for date storage
    private Calendar startDate; //class for date storage
    private serviceLogNode serviceHead; //head of service substructure; stores service transactions throughout given week
    private int weekNum; //week's number

    public weekListNode(){
        Next = null;
        startDate = Calendar.getInstance();
        weekNum = 1;
        //startDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public weekListNode(weekListNode next){
        Next = next;
        startDate = Calendar.getInstance();
        if(next == null)
            weekNum = 1;
        else
            weekNum = next.weekNum + 1;
    }

    //returns a boolean denoting whether or not a week has elapsed since the initialization of the object
    //that this method is called from.
    public boolean isOfCurrentWeek(){
        Calendar currentDate = Calendar.getInstance();
        return currentDate.get(Calendar.HOUR_OF_DAY) < 168 + startDate.get(Calendar.HOUR_OF_DAY);
    }

    //adds a new service at the head of the service substructure.
    public void addService(serviceLogNode toAdd){
        toAdd.setWeekNum(weekNum);
        toAdd.setNext(serviceHead);
        serviceHead = toAdd;
    }

    //returns the week that matches the week number
    public weekListNode serviceWeek(int weekNumber){
        if (weekNumber < 1 || weekNumber > weekNum)
            return null;
        if (weekNumber == weekNum)
            return this;
        return serviceWeek(weekNumber);
    }

    //go through the serviceLog list and print all of member and provider reports
    //along with the
    public boolean printWeeklyReport(){
        serviceLogNode current = serviceHead;
        boolean allSuccess = true;
        while(current != null){ //print member reports
            if(!current.getMember().printReport(weekNum))
                allSuccess = false;
            current = current.getNextMember();
        }
        current = serviceHead;
        while(current != null){ //print provider reports and EFT data
            if(!current.getProvider().printReport(weekNum))
                allSuccess = false;
            else
                current.getProvider().printEFT(weekNum);
            current = current.getNextProvider();
        }
        if(!printAccountSummary())
            allSuccess = false;
        return allSuccess;
    }

    //Print the accounting summary for the week's services
    public boolean printAccountSummary(){
        DateFormat DF = new SimpleDateFormat("MM.dd.yyyy");
        Calendar calendar = Calendar.getInstance();
        File file = new File("Accounting_Summary_" + DF.format(calendar.getTime()) + ".txt");
        FileWriter writer;
        serviceLogNode current = serviceHead;
        int numProvider = 0;
        int numConsultations = 0;
        float totalFee = 0.0f;
        try {
            file.createNewFile();
            writer = new FileWriter(file);
            while (current != null) {
                current.getProvider().generateWeeklyTotals(weekNum);
                writer.write("Provider: " +current.getProvider().getName());
                writer.write("\nConsultations: " + current.getProvider().getWeeklyConsultations());
                writer.write("\nFee: $" + current.getProvider().getWeeklyFee()
                        + "\n-----------------------\n");
                totalFee += current.getProvider().getWeeklyFee();
                numConsultations += current.getProvider().getWeeklyConsultations();
                ++numProvider;
                current = current.getNextProvider();
            }
            writer.write("Total Number Providers: " + numProvider);
            writer.write("\nTotal Number Consultations: " + numConsultations);
            writer.write("\nTotal Fee: $" + totalFee);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Failed to create account summary report file!");
        }
        return false;
    }

    //test method; returns a scripted set of service data
    public static weekListNode Test(){
        weekListNode toReturn = new weekListNode();
        memberNode testmember = (memberNode)userHashTable.Retrieve(123454321, 1);
        providerNode testprovider = (providerNode)userHashTable.Retrieve(987654321, 2);
        if(testmember != null && testprovider != null) {
            serviceLogNode testlog = new serviceLogNode(
                    testprovider, testmember, "05-05-2016",
                    GUIRoot.getProviderPane().getServiceList()[1], "It was okay I guess."
            );
            toReturn.addService(testlog);
        }
        testmember = (memberNode)userHashTable.Retrieve(123456789, 1);
        if(testmember != null && testprovider != null) {
            serviceLogNode testlog = new serviceLogNode(
                    testprovider, testmember, "04-04-2016",
                    GUIRoot.getProviderPane().getServiceList()[2], "This was just great."
            );
            toReturn.addService(testlog);
        }
        testmember = (memberNode)userHashTable.Retrieve(111111111, 1);
        testprovider = (providerNode)userHashTable.Retrieve(222222222, 2);
        if(testmember != null && testprovider != null) {
            serviceLogNode testlog = new serviceLogNode(
                    testprovider, testmember, "03-03-2016",
                    GUIRoot.getProviderPane().getServiceList()[3], "I have no idea what I'm doing."
            );
            toReturn.addService(testlog);
        }

        return toReturn;
    }

    public int getWeekNum(){
        return weekNum;
    }
}