package serviceDataStructure;

import userDataStructure.userNode;

import java.io.Serializable;

/**
 * Created by ahmedhqadri on 5/27/16.
 */
public class servicesInfoList implements Serializable {

    private servicesInfoNode head;

    public void AddNode(serviceLogNode log, userNode user){
        //try to find the same user in the list
        servicesInfoNode temp = retrieve(user,head);
        //if not, allocate a new memory and store it
        if(temp == null){
            temp = head;
            head = new servicesInfoNode(log,user);
            head.setNext(temp);
        }else{ //if so, store the data into that user's service log
            temp.setRecords(log);
        }
    }

    //recursive function to find same member/provider in the list
    public servicesInfoNode retrieve(userNode user, servicesInfoNode temp){
        //list is empty,return null
        if(temp == null){return null;}

        //found the same member/provider, return head
        if(temp.retrieve(user)){
            return head;
        }else {//otherwise, call recursive to next node
            return retrieve(user, temp.getNext());
        }
    }
}

