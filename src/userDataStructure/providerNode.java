package userDataStructure;

import serviceDataStructure.serviceLogNode;
import serviceDataStructure.servicesInfoList;
import serviceDataStructure.servicesInfoNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

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
    private int lastWeeklyNum; //the week number of the last weekly generated data
    private int weeklyConsultations; //generated for a specific week when printing the report
    private float weeklyFee;    //total fee for a specific week, which is generated by printing the report
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
        weeklyConsultations = 0;
        weeklyFee = 0.0f;
        lastWeeklyNum = 0;
    }

    public void addService(serviceLogNode toInsert){
        if (Head == null)
            Head = new servicesInfoList();
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

    public int getWeeklyConsultations() { return weeklyConsultations; }

    public float getWeeklyFee(){ return weeklyFee; }
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

    //Print the provider report for the specific week number
    public boolean printReport(int weekNumber){
        if (Head == null || weekNumber > Head.getHead().getRecords().getWeekNum())
            return false;

        servicesInfoNode current = Head.getHead();
        serviceLogNode record = current.getRecords();
        while(record != null && record.getWeekNum() >  weekNumber) {
            current = current.getNext();
            if(current != null)
                record = current.getRecords();
            else
                record = null;
        }
        //generate the week's report
        if(record != null && record.getWeekNum() == weekNumber) {
            File file = new File(providerName + "_" + new Date() + ".txt");
            FileWriter writer;
            weeklyConsultations = 0; // reset totals
            weeklyFee = 0.0f;
            try {
                if(file.createNewFile()) {
                    writer = new FileWriter(file);
                    writer.write(returnInfo()); //header info
                    while (record != null && record.getWeekNum() == weekNumber) {
                        writer.write("\n" + record.returnAsString()); //per service info
                        weeklyFee += record.providerFee();
                        ++weeklyConsultations;
                        current = current.getNext();
                        if (current != null)
                            record = current.getRecords();
                        else
                            record = null;
                    }
                    writer.write("\nTotal Number Consultations: " + weeklyConsultations);
                    writer.write("\nTotal Fee: $" + weeklyFee);
                    writer.flush();
                    writer.close();
                    lastWeeklyNum = weekNumber; //successfully updated weekly data for this week
                    return true;
                }
                else
                    System.out.println("Failed to create " + providerName + "'s report file!");
            } catch (IOException e) {
                System.out.println("Failed to create " + providerName + "'s report file!");
            }
        }
        return false;
    }

    //Print the provider's EFT data for the specific week number
    public boolean printEFT(int weekNumber){
        if(!generateWeeklyTotals(weekNumber)) //generate the weekly totals
            return false;

        //print the EFT data
        File file = new File(providerName + "_EFT_" + new Date() + ".txt");
        FileWriter writer;
        try {
            if(file.createNewFile()) {
                writer = new FileWriter(file);
                writer.write("Provider: " + providerName);
                writer.write("\nProvider Number:" + getUserNumber());
                writer.write("\nTotal Amount: $" + weeklyFee);
                writer.flush();
                writer.close();
                return true;
            }
            else
                System.out.println("Failed to create " + providerName + "'s EFT file!");
        } catch (IOException e) {
            System.out.println("Failed to create " + providerName + "'s EFT file!");
        }
        return false;
    }

    //generates the weeklyFee and weeklyConsultations for a given week number
    public boolean generateWeeklyTotals(int weekNumber) {
        if (lastWeeklyNum == weekNumber)
            return true; //weekly totals already generated
        if (Head == null || weekNumber > Head.getHead().getRecords().getWeekNum())
            return false;

        servicesInfoNode current = Head.getHead();
        serviceLogNode record = current.getRecords();
        while (record != null && record.getWeekNum() > weekNumber) {
            current = current.getNext();
            if (current != null)
                record = current.getRecords();
            else
                record = null;
        }
        if (record != null && record.getWeekNum() == weekNumber) { // generate the weekly totals
            weeklyFee = 0.0f;
            weeklyConsultations = 0;
            while (record != null && record.getWeekNum() == weekNumber) {
                weeklyFee += record.providerFee();
                ++weeklyConsultations;
                current = current.getNext();
                if (current != null)
                    record = current.getRecords();
                else
                    record = null;
            }
            lastWeeklyNum = weekNumber;
            return true;
        }
        return false; //no data found for week number
    }
}