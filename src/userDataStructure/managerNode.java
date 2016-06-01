package userDataStructure;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class managerNode extends userNode implements Serializable{

    public managerNode(int managerNumber){
        super(managerNumber);
    }

    @Override
    public int getUserCategory() { //returns the user's category as an int.
        return 0;
    }

    @Override
    public String returnInfo(){
        return getUserNumber() + "";
    }
}
