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
    private providerService Head;

    public servicesInfoNode(){
        Next = null;
        Head = null;
    }

    public servicesInfoNode(providerService head) {
        Head = head;
    }

    public servicesInfoNode getNext() {
        return Next;
    }

    public void setNext(servicesInfoNode next) {
        Next = next;
    }

    public providerService getRecords() {
        return Head;
    }

    public providerService getData() {
        return Head;
    }

    public void setData(providerService data) {
        Head = data;
    }
}