package GUI;

import serviceDataStructure.weekListNode;
import userDataStructure.managerNode;
import userDataStructure.providerNode;
import userDataStructure.serializationObject;
import userDataStructure.userHashTable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/4/2016.
 */
public class GUIRoot implements Serializable{
    //a class representing the heft of the program's graphical user interface elements

    private Stage primaryStage; //necessary javaFX GUI clsas
    private Scene GUIScene; //necessary javaFX GUI class
    private static StackPane rootPane; //a pane for adding and removing more specific GUI panes to and from.
    private static loginPane userTypeSelectionPane; //the pane for elements pertaining to logging in as a manager or provider.
    private static managerPane managerpane; //the pane for manager-specific user interface elements
    private static providerPane providerpane; //the pane for provider-specific user interface elements
    private static userHashTable userStructure; //the hash table of users.
    private static weekListNode weekStructure; //the linear linear linked list of weeks and services for each week


    public GUIRoot(Stage passedStage, serializationObject data){
        primaryStage = passedStage; //store the passed stage in a variable.
        rootPane = new StackPane(); //initialize a stackpane to store and arrange GUIRoot.GUIRoot elements.
        rootPane.setAlignment(Pos.CENTER);
        userTypeSelectionPane = new loginPane();
        managerpane = new managerPane();
        providerpane = new providerPane(); //initialize instances of each GUI pane class.
        rootPane.getChildren().add(userTypeSelectionPane); //add the selection pane to the root pane.

        GUIScene = new Scene(rootPane); //initialize a scene for the pane.

        primaryStage.setTitle("Chocoholics"); //Name GUIRoot.GUIRoot window.
        primaryStage.setMaxHeight(800); //Set dimensions of GUIRoot.GUIRoot window.
        primaryStage.setMinHeight(800);
        primaryStage.setMaxWidth(800);
        primaryStage.setMinWidth(800);

        primaryStage.setFullScreen(false); //ensure that the GUIRoot.GUIRoot is windowed.
        primaryStage.setResizable(false); //keep the GUIRoot.GUIRoot at a constant size for simplicity of element configuration.

        if(data == null) {
            userStructure = new userHashTable();
            userStructure.Test();
        }
        else{
            userStructure = data.getUserHashTable();
            weekStructure = data.getWeeksHead();
            providerpane.setServiceList(data.getServices());
            //TEST
            System.out.println("Loading");
            managerpane.printWeeklyReports();
            //TEST
        }

        primaryStage.setScene(GUIScene); //assign the scene to the primary stage object.
        primaryStage.show(); //display the GUIRoot.GUIRoot on the user's computer.
    }

    //swaps to the login pane.
    public static void swapToLoginPane(){
        rootPane.getChildren().removeAll(userTypeSelectionPane, managerpane, providerpane);
        //ensure that no extant panes are left on the root pane.
        rootPane.getChildren().add(userTypeSelectionPane); //add the login pane to the root pane.
    }

    //swaps to the manager pane.
    public static void swapToManagerPane(managerNode tologin){
        rootPane.getChildren().removeAll(userTypeSelectionPane, managerpane, providerpane);
        //ensure that no extant panes are left on the root pane.
        rootPane.getChildren().add(managerpane);
        managerpane.Login(tologin);
    }

    //swaps to the provider pane.
    public static void swapToProviderPane(providerNode tologin){
        rootPane.getChildren().removeAll(userTypeSelectionPane, managerpane, providerpane);
        //ensure that no extant panes are left on the root pane.
        rootPane.getChildren().add(providerpane);
        providerpane.login(tologin);
    }

    //returns the user structure.
    public static userHashTable getUserStructure(){
        return userStructure;
    }

    //returns the week structure along with its substructure of services for each given week.
    public static weekListNode getWeekStructure(){
        return weekStructure;
    }

    public static void addNewWeek(){
        weekListNode temp = new weekListNode(weekStructure);
        weekStructure = temp;
    }

    //returns a reference to the provider pane.
    public static providerPane getProviderPane(){
        return providerpane;
    }

    public serializationObject buildSerializationObject(){
        serializationObject toReturn = new serializationObject(
                providerpane.getServiceList(),
                weekStructure,
                userStructure
        );
        return toReturn;
    }
}
