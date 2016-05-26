import GUI.GUIRoot;
import javafx.application.Application;
import javafx.stage.Stage;
import serviceDataStructure.weekListNode;
import userDataStructure.serializationObject;

import java.io.*;

/**
 * Created by Spaghetti on 4/4/2016.
 */
public class main extends Application {
    private GUIRoot guiroot;

    public static void main(String[] args) {
            launch();
        }

    public void start(Stage primaryStage) throws Exception{
        serializationObject toRead = null;
        try {
            FileInputStream fileInput = new FileInputStream("ChocAnData.dat");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            toRead = (serializationObject) objectInput.readObject(); //attempt to read in data
            objectInput.close();
        }
        catch (IOException e){
            System.err.println("No extant data found; writing new test data structures.");
        }
        catch (ClassNotFoundException e){
            System.err.println("No extant data found; writing new test data structures.");
        }
        guiroot = new GUIRoot(primaryStage, toRead);
    }

    @Override
    public void stop() throws Exception {
        serializationObject toSerialize = guiroot.buildSerializationObject();
        try {
            FileOutputStream FileOutput = new FileOutputStream("ChocAnData.dat");
            ObjectOutputStream ObjectOutput = new ObjectOutputStream(FileOutput);
            ObjectOutput.writeObject(toSerialize); //write the data out to file
            ObjectOutput.close(); //close the stream
        }
        catch (IOException e) {
            System.err.println ("Save unsuccessful; "+ e.getMessage() + " not serializable");
        }
        super.stop();
    }
}
