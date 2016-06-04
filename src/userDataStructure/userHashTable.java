package userDataStructure;

import serviceDataStructure.serviceLogNode;
import serviceDataStructure.servicesInfoList;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/4/2016.
 */
//the principle data structure for retrieving users; used in logins and member verification, mostly.
public class userHashTable implements Serializable{
    //a class for storing information pertaining to members' accounts; extends Java Hashtable class.
    private int Size = 100;
    private userNode[] Users;
    private static userHashTable Primary;

    public userHashTable(){
        Users = new userNode[Size];
        Primary = this;
    }

    public static void Insert(userNode toInsert){
        int hashedElement = toInsert.getUserNumber()%Primary.Size;
        if(Primary.Users[hashedElement] == null){
            Primary.Users[hashedElement] = toInsert;
        }
        else{
            toInsert.setNext(Primary.Users[hashedElement]);
            Primary.Users[hashedElement] = toInsert;
        }
    }

    public static userNode Retrieve(int toRetrieve, int typeToRetrieve){
        if(Primary.Users[toRetrieve%Primary.Size] == null){
            return null;
        }
        else {
            return Primary.Users[toRetrieve % Primary.Size].Retrieve(toRetrieve, typeToRetrieve);
        }
    }

    public static void Remove(int toRemove, int category){
        int hashedElement = toRemove%Primary.Size;
        if(Primary.Users[hashedElement] != null)
           Primary.Users[hashedElement] = Primary.Users[hashedElement].Remove(toRemove, category);
    }

    public void Test(){
        Insert(new managerNode(123456789));
        Insert(new providerNode(222222222, "Albert King", "1234 3rd NE Ave.", "Testville", "CA", "12345"));
        Insert(new providerNode(987654321, "Stephen Jones", "11111 Riverside St.", "Innawoods", "WA", "33333"));
        Insert(new memberNode(123456789, "John Smith", "1111 Test Ave.", "Testland", "OR", "97229"));
        Insert(new memberNode(111111111, "Alex Arnoult", "43122 Doot St.", "Corpus Christi", "TX", "22222"));
        Insert(new memberNode(123454321, "Steve James", "6432 SW Sleepy Ave.", "Atlanta", "GA", "12321"));
    }

    public void setPrimary(userHashTable primary){
        Primary = primary;
    }
}
