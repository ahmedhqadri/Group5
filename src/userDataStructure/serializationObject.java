package userDataStructure;

import serviceDataStructure.providerService;
import serviceDataStructure.weekListNode;

import java.io.Serializable;

/**
 * Created by Spaghetti on 5/23/2016.
 */
//contains references to all serializable data structures.
//used to condense the exported data into a single dat file.
public class serializationObject implements Serializable{
    private providerService [] Services;
    private weekListNode Weeks;
    private userHashTable Users;

    public serializationObject(providerService [] services, weekListNode weeks, userHashTable users){
        Services = services;
        Weeks = weeks;
        Users = users;
    }

    public userHashTable getUserHashTable(){
        return Users;
    }

    public weekListNode getWeeksHead(){
        return Weeks;
    }

    public providerService [] getServices(){
        return Services;
    }
}
