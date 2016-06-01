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

    public servicesInfoNode(){
        Next = null;
        Head = null;
    }

    public servicesInfoNode(serviceLogNode head) {
        Head = head;
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

    public serviceLogNode getData() {
        return Head;
    }

    public void setData(serviceLogNode data) {
        Head = data;
    }
}