package userDataStructure;

import serviceDataStructure.serviceLogNode;
import serviceDataStructure.servicesInfoList;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/4/2016.
 */
public class userHashTable implements Serializable{
    //a class for storing information pertaining to members' accounts; extends Java Hashtable class.
    private static int Size = 100;
    private static userNode[] Users;

    public userHashTable(){
        Users = new userNode[Size];
    }

    public static void Insert(userNode toInsert){
        int hashedElement = toInsert.getUserNumber()%Size;
        if(Users[hashedElement] == null){
            Users[hashedElement] = toInsert;
        }
        else{
            toInsert.setNext(Users[hashedElement]);
            Users[hashedElement] = toInsert;
        }
    }

    public static userNode Retrieve(int toRetrieve, int typeToRetrieve){
        if(Users[toRetrieve%Size] == null){
            return null;
        }
        else {
            return Users[toRetrieve % Size].Retrieve(toRetrieve, typeToRetrieve);
        }
    }

    public void Test(){
        Insert(new managerNode(123456789));
        Insert(new providerNode(987654321));
        Insert(new managerNode(987654321));
        Insert(new memberNode(123456789, "John Smith", "1111 Test Ave.", "Testland", "OR", "97229"));
        Insert(new memberNode(111111111));
    }
}
