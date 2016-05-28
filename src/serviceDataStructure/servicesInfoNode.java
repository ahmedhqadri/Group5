package serviceDataStructure;

import userDataStructure.memberNode;
import userDataStructure.providerNode;

import java.io.Serializable;

/**
 * Created by ahmedhqadri on 5/27/16.
 */
public class servicesInfoNode implements Serializable {

    private servicesInfoNode Next;
    private providerNode Provider;
    private memberNode Member;
    private providerService Service;

    public servicesInfoNode(){
        Next = null;
    }

    public servicesInfoNode(memberNode member, providerNode provider,
                            providerService service){
        this.Member = member;
        this.Provider = provider;
        this.Service.setID(Integer.parseInt(service.getID()));
        this.Service.setName(service.getName());
        this.Service.setCost(Integer.parseInt(service.getCost()));
        Next = null;
    }


    /*public serviceDataStructure.servicesInfoNode(providerNode Provider, memberNode Member,
                             providerService Service){
        this.Provider = Provider;
        this.Member = Member;
        Next = null;
    }*/


    public void setNext(servicesInfoNode next){
        Next = next;
    }
}
