package userDataStructure;

import serviceDataStructure.providerService;
import serviceDataStructure.serviceLogNode;
import serviceDataStructure.servicesInfoList;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/6/2016.
 */
public abstract class userNode implements Serializable{
    private userNode Next; //reference to the next node in the LLL
    private int userNumber; //the user's ID number.

    public userNode(){
        Next = null;
        userNumber = 0;
    }

    public userNode(int number){
        Next = null;
        userNumber = number;
    }

    public int getUserNumber(){
        return userNumber;
    }

    public void setNext(userNode toSet){
            Next = toSet;
    }

    public userNode Retrieve(int toRetrieve, int usercategory){
        if(userNumber == toRetrieve && usercategory == getUserCategory()){
            return this;
        }
        else if(Next == null){
            return null;
        }
        else{
            return Next.Retrieve(toRetrieve, usercategory);
        }
    }



    public userNode Remove(int toRemove, int usercategory){
        if(userNumber == toRemove && usercategory == getUserCategory()){
            return Next;
        }
        else if(Next != null){
            Next = Next.Remove(toRemove, usercategory);
        }
        return this;
    }

    public abstract int getUserCategory(); //returns this user's category; 0 for manager, 1 for member, 2 for provider.

    public abstract String returnInfo(); //return a nicely-formatted string representing this
}
