package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import userDataStructure.managerNode;
import userDataStructure.memberNode;
import userDataStructure.providerNode;
import userDataStructure.userHashTable;
import serviceDataStructure.weekListNode;
import GUI.GUIRoot;

import java.io.Serializable;

/**
 * Created by Spaghetti on 5/2/2016.
 */
//class containing GUI elements pertinent to managers' use of the system.
public class managerPane extends StackPane implements Serializable{
    private providerNode currentProvider; //a provider node that we're interacting with
    private memberNode currentMember; //a member node that we're interacting with
    private managerNode currentManager; //the manager that is presently logged in.
    private int newMemberorProvider; //1 denotes adding a new member; 2 denotes adding a new provider; 0 denotes neither
    private boolean isBase; //whether we're on the base screen or not.
    private TextField retrievalField;
    private Text userInformation; //an information display for any user that the manager retrieves.
    private Text managerNumberHeading;
    private TextField [] editFields = new TextField[6];
    private Text [] editLabels = new Text[6];
    private GridPane buttonPane = new GridPane();
    private managerButton [] Buttons = new managerButton[12]; //buttons for navigating this part of the GUI

    public managerPane(){
        setAlignment(Pos.CENTER); //align all GUI elements in relation to the center.
        Rectangle Background = new Rectangle(800, 800); //make a background for the pane.
        Background.setFill(new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.BLUE),
                        new Stop(0.3, Color.BLUEVIOLET),
                        new Stop(0.7, Color.VIOLET),
                        new Stop(1, Color.DARKVIOLET)}));
        getChildren().add(Background);
        //initialize all text fields, labels, etc
        buttonPane.setAlignment(Pos.CENTER);
        getChildren().add(buttonPane);

        retrievalField = new TextField();
        retrievalField.setMaxWidth(150);
        retrievalField.setMaxHeight(40);
        retrievalField.setTranslateX(150);
        retrievalField.setTranslateY(-120);
        userInformation = new Text();
        userInformation.setFont(Font.font("Verdana", 12));
        userInformation.setTranslateX(150);
        userInformation.setTranslateY(110);

        Buttons[0] = new managerButton("Retrieve Member", 0, this);
        Buttons[0].setTranslateX(150);
        Buttons[0].setTranslateY(-50);
        Buttons[1] = new managerButton("Retrieve Provider", 1, this);
        Buttons[1].setTranslateX(150);
        Buttons[1].setTranslateY(20);
        Buttons[2] = new managerButton("Edit User", 2, this);
        Buttons[2].setTranslateX(-150);
        Buttons[2].setTranslateY(-130);
        Buttons[3] = new managerButton("Delete User", 3, this);
        Buttons[3].setTranslateX(-150);
        Buttons[3].setTranslateY(-70);
        Buttons[4] = new managerButton("", 4, this); //this has variable text filled in by other methods
        Buttons[4].setTranslateX(-150);
        Buttons[4].setTranslateY(-10);
        Buttons[5] = new managerButton("New Member", 5, this);
        Buttons[5].setTranslateX(-150);
        Buttons[5].setTranslateY(50);
        Buttons[6] = new managerButton("New Provider", 6, this);
        Buttons[6].setTranslateX(-150);
        Buttons[6].setTranslateY(110);
        Buttons[7] = new managerButton("Print Reports", 7, this);
        Buttons[7].setTranslateX(-150);
        Buttons[7].setTranslateY(170);
        Buttons[8] = new managerButton("Logout", 8, this);
        Buttons[8].setTranslateX(150);
        Buttons[8].setTranslateY(200);
        Buttons[9] = new managerButton("Accept", 9, this);
        Buttons[9].setTranslateX(-150);
        Buttons[9].setTranslateY(200);
        Buttons[10] = new managerButton("Back", 10, this);
        Buttons[10].setTranslateX(150);
        Buttons[10].setTranslateY(200);
        Buttons[11] = new managerButton("Print User\n Report", 11, this);
        Buttons[11].setTranslateX(0);
        Buttons[11].setTranslateY(110);

        for(int i = 0; i < 6; ++i){
            editFields[i] = new TextField();
            editFields[i].setTranslateX(100);
            editFields[i].setTranslateY(-150 + 60 * i);
            editFields[i].setMaxSize(250, 30);
            editLabels[i] = new Text();
            editLabels[i].setFont(Font.font("Verdana", 20));
            editLabels[i].setTranslateX(-120);
            editLabels[i].setTranslateY(-150 + 60 * i);
        }
        editLabels[0].setText("ID:");
        editLabels[1].setText("Name:");
        editLabels[2].setText("Street:");
        editLabels[3].setText("City:");
        editLabels[4].setText("State:");
        editLabels[5].setText("Zip:");

        setOnKeyReleased(event ->{
            if(event.getCode() == KeyCode.ENTER){
                if(isBase){ //base screen case
                    retrieveUser(1); //try and retrieve a member
                    if(currentMember != null) {
                        userInformation.setText(currentMember.returnInfo());
                        addUserButtons();
                    }
                }
                else{
                    parseEditingOrAddingInput(); //parse the input on this screen
                }
                event.consume();
            }
        });

        managerNumberHeading = new Text();
        managerNumberHeading.setTranslateY(-200);
        managerNumberHeading.setFont(Font.font("Verdana", 24));
        getChildren().add(managerNumberHeading);
    }

    public void Login(managerNode toLogin){
        currentManager = toLogin;
        currentMember = null;
        currentProvider = null;
        userInformation.setText("");
        returnToBaseMode();
    }

    public void retrieveUser(int type){
        if(!isNumber(retrievalField.getText()) || retrievalField.getText().length() != 9) {
            userInformation.setText("Invalid ID.");
            currentMember = null;
            currentProvider = null;
            return;
        }
        int number = Integer.parseInt(retrievalField.getText());
        if(type == 1){ //member case
            currentMember = (memberNode) userHashTable.Retrieve(number, type);
            currentProvider = null;
            if(currentMember == null){ //member not found
                userInformation.setText("Member not found.");
            }
        }
        if(type == 2){ //provider case
            currentProvider = (providerNode)userHashTable.Retrieve(number, type);
            currentMember = null;
            if(currentProvider == null){ //provider not found
                userInformation.setText("Provider not found.");
            }
        }
    }

    public void swapToEditingMode(){
        isBase = false;
        newMemberorProvider = 0;
        buttonPane.getChildren().removeAll(Buttons[0], Buttons[1], Buttons[2], Buttons[3], Buttons[4],
                Buttons[5], Buttons[6], Buttons[7], Buttons[8], Buttons[11]); //remove other buttons.
        getChildren().removeAll(retrievalField, userInformation);

        if(currentMember != null) {
            managerNumberHeading.setText("Edit Member #" + currentMember.getUserNumber());
            editFields[1].setText(currentMember.getName());
            editFields[2].setText(currentMember.getStreet());
            editFields[3].setText(currentMember.getCity());
            editFields[4].setText(currentMember.getState());
            editFields[5].setText(currentMember.getZip());
        }
        else {
            managerNumberHeading.setText("Edit Provider #" + currentProvider.getUserNumber());
            editFields[1].setText(currentProvider.getName());
            editFields[2].setText(currentProvider.getStreet());
            editFields[3].setText(currentProvider.getCity());
            editFields[4].setText(currentProvider.getState());
            editFields[5].setText(currentProvider.getZip());
        }
        for(int i = 1; i < 6; ++i){
            getChildren().addAll(editFields[i], editLabels[i]);
        }
        buttonPane.getChildren().addAll(Buttons[9], Buttons[10]);
    }

    public void reverseMemberSuspension(){
        if(currentMember != null){
            currentMember.setSuspended(!currentMember.isSuspended());
            if(currentMember.isSuspended())
                Buttons[4].setText("Remove Suspension");
            else
                Buttons[4].setText("Suspend Member");
            userInformation.setText(currentMember.returnInfo()); //update this accordingly
        }
    }

    public void swapToUserAddingMode(int Type){
        isBase = false;
        newMemberorProvider = Type;
        buttonPane.getChildren().removeAll(Buttons[0], Buttons[1], Buttons[2], Buttons[3], Buttons[4],
                Buttons[5], Buttons[6], Buttons[7], Buttons[8], Buttons[11]); //remove other buttons.
        getChildren().removeAll(userInformation, retrievalField);
        if(Type == 1)
            managerNumberHeading.setText("New Member");
        else
            managerNumberHeading.setText("New Provider");
        for(int i = 0; i < 6; ++i){
            getChildren().addAll(editFields[i], editLabels[i]);
            editFields[i].setText(""); //clear all extant text
        }
        buttonPane.getChildren().addAll(Buttons[9], Buttons[10]); //add the accept and back  buttons
    }

    public void removeUser(){ //remove a user from the data structure.
        if(currentMember != null) {
            userHashTable.Remove(currentMember.getUserNumber(), 1);
            currentMember = null;
        }
        else if(currentProvider != null) {
            userHashTable.Remove(currentProvider.getUserNumber(), 2);
            currentProvider = null;
        }
        userInformation.setText("User Removed.");
        addUserButtons();
    }

    public void addUserButtons(){//add visible buttons to the UI for user object interaction
        buttonPane.getChildren().removeAll(Buttons[2], Buttons[3], Buttons[4], Buttons[11]);
        //remove any extant copies of these; duplicates cause errors.
        if(currentMember != null){
            buttonPane.getChildren().addAll(Buttons[2], Buttons[3], Buttons[4], Buttons[11]);
            if(currentMember.isSuspended())
                Buttons[4].setText("Remove Suspension");
            else
                Buttons[4].setText("Suspend Member");
            userInformation.setText(currentMember.returnInfo());
        }
        else if(currentProvider != null){
            buttonPane.getChildren().addAll(Buttons[2], Buttons[3], Buttons[11]);
            userInformation.setText(currentProvider.returnInfo());
        }
    }

    //Prints reports for this week for individual users and the overarching manager report.
    public void printWeeklyReports(){
        weekListNode serviceWeek = GUIRoot.getWeekStructure(); //get most recent week to print
        if(serviceWeek != null){
            serviceWeek.printWeeklyReport();
        }
    }

    //verifies info in editing/adding fields and--if it's valid--adds or edits it into the user data structure.
    public void parseEditingOrAddingInput(){
        boolean valid = true; //denotes the validity of user input contained in the fields.
        if(editFields[1].getText().length() > 25 || editFields[1].getText().length() < 1){
            editFields[1].setText("This field must contain between 1 and 25 characters.");
            valid = false;
        }
        if(editFields[2].getText().length() > 25 || editFields[2].getText().length() < 1){
            editFields[2].setText("This field must contain between 1 and 25 characters.");
            valid = false;
        }
        if(editFields[3].getText().length() > 14 || editFields[3].getText().length() < 1){
            editFields[3].setText("This field must contain between 1 and 14 characters.");
            valid = false;
        }
        if(editFields[4].getText().length() != 2){
            editFields[4].setText("This field must contain a 2 letter state code.");
            valid = false;
        }
        if(editFields[5].getText().length() != 5 || !isNumber(editFields[5].getText())){
            editFields[5].setText("This field must contain a 5 digit number.");
            valid = false;
        }
        if(newMemberorProvider == 0 && valid){ //editing an extant user case
            if(currentMember != null){
                currentMember.setName(editFields[1].getText());
                currentMember.setStreet(editFields[2].getText());
                currentMember.setCity(editFields[3].getText());
                currentMember.setState(editFields[4].getText());
                currentMember.setZip(editFields[5].getText());
            }
            else if(currentProvider != null){
                currentProvider.setName(editFields[1].getText());
                currentProvider.setStreet(editFields[2].getText());
                currentProvider.setCity(editFields[3].getText());
                currentProvider.setState(editFields[4].getText());
                currentProvider.setZip(editFields[5].getText());
            }
        }
        else{ //new member or provider case
            if(isNumber(editFields[0].getText()) && editFields[0].getText().length() == 9){
                if(userHashTable.Retrieve(Integer.parseInt(editFields[0].getText()), newMemberorProvider) != null){
                    valid = false; //extant user ID case.
                    editFields[0].setText("This user ID is already in use.");
                }

                if(newMemberorProvider == 1 && valid){ //new member case
                    currentMember = new memberNode(
                                    Integer.parseInt(editFields[0].getText()),
                                    editFields[1].getText(),
                                    editFields[2].getText(),
                                    editFields[3].getText(),
                                    editFields[4].getText(),
                                    editFields[5].getText());
                    userHashTable.Insert(currentMember);
                    currentProvider = null;
                    userInformation.setText(currentMember.returnInfo());
                    addUserButtons();
                }
                else if(newMemberorProvider == 2 && valid){ //new provider case.
                    currentProvider = new providerNode(
                                    Integer.parseInt(editFields[0].getText()),
                                    editFields[1].getText(),
                                    editFields[2].getText(),
                                    editFields[3].getText(),
                                    editFields[4].getText(),
                                    editFields[5].getText());
                    userHashTable.Insert(currentProvider);
                    currentMember = null;
                    userInformation.setText(currentProvider.returnInfo());
                    addUserButtons();
                }
            }
            else{ //invalid user number
                editFields[0].setText("Please enter a new 9 digit number.");
                valid = false;
            }
        }
        if(valid)
            returnToBaseMode(); //return to base mode if the input was valid.
    }

    public void returnToBaseMode(){
        isBase = true;
        newMemberorProvider = 0; //denote that we aren't adding either of these.
        buttonPane.getChildren().removeAll(Buttons[9], Buttons[10]);
        for(int i = 0; i < 6; ++i){
            getChildren().removeAll(editFields[i], editLabels[i]);
        }
        managerNumberHeading.setText("Hello Manager #" + currentManager.getUserNumber());
        buttonPane.getChildren().addAll(Buttons[0], Buttons[1], Buttons[5], Buttons[6], Buttons[7], Buttons[8]);
        getChildren().addAll(userInformation, retrievalField);
        addUserButtons(); //additionally, add user buttons if we have a current member or provider.
    }

    public void Logout(){
        GUIRoot.swapToLoginPane();
    }

    //print an individual report for a specified user.
    public void printIndividualReport(){
        if(GUIRoot.getWeekStructure() != null) {
            if (currentProvider != null)
                currentProvider.printReport(GUIRoot.getWeekStructure().getWeekNum());
            if (currentMember != null)
                currentMember.printReport(GUIRoot.getWeekStructure().getWeekNum());
        }
    }

    //checks to make sure that the passed string contains strictly numeric characters.
    public boolean isNumber(String toParse){
        for(int i = 0; i < toParse.length(); ++i){
            if(!Character.isDigit(toParse.charAt(i)))
                return false;
        }
        return true;
    }

    //a class for adding clickable buttons to this UI pane.
    /*buttonFunction identities are as follows:
    0 -> Validate Member
    1 -> Validate Provider
    2 -> Edit User
    3 -> Delete User
    4 -> Suspend/Unsuspend Member
    5 -> Add new member
    6 -> Add new provider
    7 -> Print weekly reports
    8 -> Log Out
    9 -> Back from new member/provider/edit user menu
    10 -> Accept new member/provider/edited user
    11 -> Print individual report
     */
    private class managerButton extends StackPane{
        private Text buttonText;

        public managerButton(String buttontext, int buttonFunction, managerPane Parent){
            Rectangle buttonBackground = new Rectangle(150, 50);
            if(buttonFunction == 11) {
                buttonBackground.setHeight(100);
                buttonBackground.setWidth(100);
            }
            buttonBackground.setFill(Color.CORNFLOWERBLUE);
            this.setOnMouseClicked(event -> {
                if(buttonFunction == 0){
                    Parent.retrieveUser(1);
                    Parent.addUserButtons();
                }
                else if(buttonFunction == 1){
                    Parent.retrieveUser(2);
                    Parent.addUserButtons();
                }
                else if(buttonFunction == 2){
                    Parent.swapToEditingMode();
                }
                else if(buttonFunction == 3){
                    Parent.removeUser();
                }
                else if(buttonFunction == 4){
                    Parent.reverseMemberSuspension();
                }
                else if(buttonFunction == 5){
                    Parent.swapToUserAddingMode(1);
                }
                else if(buttonFunction == 6){
                    Parent.swapToUserAddingMode(2);
                }
                else if(buttonFunction == 7){
                    Parent.printWeeklyReports();
                }
                else if(buttonFunction == 8){
                    Parent.Logout();
                }
                else if(buttonFunction == 9){
                    Parent.parseEditingOrAddingInput();
                }
                else if(buttonFunction == 10){
                    Parent.returnToBaseMode();
                }
                else if(buttonFunction == 11){
                    Parent.printIndividualReport();
                }
            });
            getChildren().add(buttonBackground);
            buttonText = new Text(buttontext);
            buttonText.setFont(Font.font("Verdana", 12));
            getChildren().add(buttonText);
        }

        public void setText(String toSet){ //sets the text to a given string in the case that we want to change this
            buttonText.setText(toSet);
        }
    }
}
