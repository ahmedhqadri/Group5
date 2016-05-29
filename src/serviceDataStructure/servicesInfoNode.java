package serviceDataStructure;

import userDataStructure.memberNode;
import userDataStructure.providerNode;
import userDataStructure.userNode;

import java.io.Serializable;

/**
 * Created by ahmedhqadri on 5/27/16.
 */
public class servicesInfoNode implements Serializable {


    private servicesInfoNode Next;
    private serviceLogNode Head;
    private userNode Data;

    public servicesInfoNode(){
        Next = null;
        Head = null;
    }

    public servicesInfoNode(serviceLogNode head, userNode data) {
        Head = head;
        Data = data;
    }

    public servicesInfoNode getNext() {
        return Next;
    }

    public void setNext(servicesInfoNode next) {
        Next = next;
    }

    public serviceLogNode getRecords() {
        return Head;
    }

    public void setRecords(serviceLogNode head) {
        head.setNext(Head);
        Head = head;
    }

    public userNode getData() {
        return Data;
    }

    public void setData(userNode data) {
        Data = data;
    }

    public boolean retrieve(userNode data){
        //to distinguse "data" is the same class as "Data"
        if(Data.getUserCategory() == data.getUserCategory()){
            //check their UserNumber, if it is same, return true.
            if(Data.getUserNumber() == data.getUserNumber()) {
                return true;
            }
            else{return false;}
        }
        else{return false;}
    }

}