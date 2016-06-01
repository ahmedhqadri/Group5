package serviceDataStructure;

import userDataStructure.userNode;

import java.io.Serializable;

/**
 * Created by ahmedhqadri on 5/27/16.
 */
public class servicesInfoList implements Serializable {

    private servicesInfoNode head;

    public void AddNode(serviceLogNode log) {

        if(head == null){
            head = new servicesInfoNode(log);
            head.setNext(null);
        }
        else{
            servicesInfoNode temp;
            temp = new servicesInfoNode(log);
            temp.setNext(head);
            head = temp;
        }
    }
}
