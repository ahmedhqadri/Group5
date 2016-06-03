package serviceDataStructure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private int weekNum; //week's number

    public weekListNode(){
        Next = null;
        startDate = new Date();
        weekNum = 1;
        //startDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public weekListNode(weekListNode next){
        Next = next;
        startDate = new Date();
        weekNum = next.weekNum + 1;
    }

    //returns a boolean denoting whether or not a week has elapsed since the initialization of the object
    //that this method is called from.
    public boolean isOfCurrentWeek(){
        Date currentDate = new Date();
        return currentDate.getHours() < 168 + startDate.getHours();
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
        File file = new File("Accounting_Summary_" + new Date() + ".txt");
        FileWriter writer;
        serviceLogNode current = serviceHead;
        int numProvider = 0;
        int numConsultations = 0;
        float totalFee = 0.0f;
        try {
            if (file.createNewFile()) {
                writer = new FileWriter(file);
                while (current != null) {
                    current.getProvider().generateWeeklyTotals(weekNum);
                    writer.write("Provider: " +current.getProvider().getName());
                    writer.write("\nConsultations: " + current.getProvider().getWeeklyConsultations());
                    writer.write("\nFee: $" + current.getProvider().getWeeklyFee() + "\n");
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
            } else
                System.out.println("Failed to create account summary report file!");
        } catch (IOException e) {
            System.out.println("Failed to create account summary report file!");
        }
        return false;
    }
}