package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import serviceDataStructure.providerService;
import serviceDataStructure.serviceLogNode;
import userDataStructure.memberNode;
import userDataStructure.providerNode;
import userDataStructure.userHashTable;

import java.io.Serializable;

/**
 * Created by Spaghetti on 5/2/2016.
 */
//GUI pane for allowing providers to interface with the ChocAn system.
public class providerPane extends StackPane {
    private providerNode currentProvider; //the provider that is presently logged into the system.
    private memberNode currentMember; //the member that this provider is meeting with.
    private GridPane buttonPane; //a gridpane to ensure that all buttons onscreen can be clicked.
    private Text memberInformation; //text for displaying information on the currently-validated member.
    private Text providerNumberHeading; //a heading denoting the provider that is logged in.
    private Text serviceProviderNumber; //text denoting the provider's number in the service logging mode.
    private Text serviceMemberNumber; //text denoting a validated member's number in the service logging mode.
    private TextField [] serviceFields; //text fields for inputting information about a new service.
    private TextArea commentField; //multi-row text field for inputting comments regarding a service
    private Text [] serviceLabels; //labels for each service field.
    private TextField memberAuthenticationField; //a textfield for authenticating a member ID
    private providerButton [] Buttons; //array of buttons associated with this pane. (see private class)
    private int Mode; //denotes what the user is doing; 0 means l
    private ScrollPane serviceListPane;
    private VBox serviceListBox;
    private providerService currentService; //the current service selected by the user.
    private providerService [] serviceList; //all possible services that can be logged.
    private int numberOfServices; //the number of services in the service list.


    public providerPane(){
        setAlignment(Pos.CENTER); //align all GUI elements in relation to the center.
        Rectangle Background = new Rectangle(800, 800);
        Background.setFill(new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.RED),
                        new Stop(0.3, Color.ORANGERED),
                        new Stop(0.7, Color.ORANGE),
                        new Stop(1, Color.CORAL)}));
        getChildren().add(Background);
        //initialize all text fields, labels, etc
        memberInformation = new Text();
        memberInformation.setFont(Font.font("Verdana", 12));
        memberInformation.setTranslateX(150);
        memberInformation.setTranslateY(30);
        serviceProviderNumber = new Text();
        serviceProviderNumber.setFont(Font.font("Verdana", 12));
        serviceProviderNumber.setTranslateX(-100);
        serviceProviderNumber.setTranslateY(-100);
        serviceMemberNumber = new Text();
        serviceMemberNumber.setFont(Font.font("Verdana", 12));
        serviceMemberNumber.setTranslateX(-100);
        serviceMemberNumber.setTranslateY(-50);
        serviceFields = new TextField[2];
        serviceLabels = new Text[3];
        for(int i = 0; i < 3; ++i){
            serviceLabels[i] = new Text();
            serviceLabels[i].setFont(Font.font("Verdana", 12));
            serviceLabels[i].setTranslateX(-200);
            serviceLabels[i].setTranslateY(-120 + (i)*70);
        }
        for(int i = 0; i < 2; ++i){
            serviceFields[i] = new TextField();
            serviceFields[i].setTranslateX(50);
            serviceFields[i].setTranslateY(-120 + (i)*70);
            serviceFields[i].setMaxSize(300, 30);
        }
        serviceFields[0].setOnMouseClicked(event -> {
            if(serviceFields[0].getText().matches("Please enter date in MM-DD-YYYY format.") ||
                    serviceFields[0].getText().matches("Service logged!")){
                serviceFields[0].setText("");
            }
        });
        serviceFields[1].setOnMouseClicked(event -> {
            if(serviceFields[1].getText().matches("Please enter a valid service code as a 6-digit number.") ||
                    serviceFields[1].getText().matches("Service ID invalid.") ||
                    serviceFields[1].getText().matches("Service logged!"))
                serviceFields[1].setText("");
        });
        serviceLabels[0].setText("Date of Service: ");
        serviceLabels[1].setText("Service Code: ");
        serviceLabels[2].setText("Comments:");
        commentField = new TextArea();
        commentField.setTranslateY(70);
        commentField.setTranslateX(50);
        commentField.setMaxSize(300, 150);
        commentField.setTranslateY(70);
        memberAuthenticationField = new TextField();
        memberAuthenticationField.setMaxWidth(150);
        memberAuthenticationField.setMaxHeight(40);
        memberAuthenticationField.setTranslateX(150);
        memberAuthenticationField.setTranslateY(-120);

        serviceListPane = new ScrollPane();
        serviceListPane.setVmax(440);
        serviceListPane.setPrefSize(350, 250);
        serviceListPane.setStyle("-fx-font-size: 30px;");
        serviceListBox = new VBox();
        serviceListBox.setVgrow(serviceListPane, Priority.ALWAYS);
        serviceListPane.setContent(serviceListBox);

        providerNumberHeading = new Text();
        providerNumberHeading.setTranslateY(-200);
        providerNumberHeading.setFont(Font.font("Verdana", 24));
        getChildren().add(providerNumberHeading);

        //initialize all buttons with appropriate text.
        Buttons = new providerButton[6];
        Buttons[0] = new providerButton("Validate New Member", 0, this);
        Buttons[0].setTranslateX(150);
        Buttons[0].setTranslateY(-60);
        Buttons[1] = new providerButton("Log Service", 1, this);
        Buttons[1].setTranslateX(-150);
        Buttons[1].setTranslateY(-50);
        Buttons[2] = new providerButton("Enter", 2, this);
        Buttons[2].setTranslateX(150);
        Buttons[2].setTranslateY(200);
        Buttons[3] = new providerButton("Service Lookup", 3, this);
        Buttons[3].setTranslateX(-150);
        Buttons[3].setTranslateY(50);
        Buttons[4] = new providerButton("Back", 4, this); //back is used multiple times, so don't enter coords
        Buttons[5] = new providerButton("Log Out", 5, this);
        Buttons[5].setTranslateX(0);
        Buttons[5].setTranslateY(200);

        numberOfServices = 5;
        serviceList = new providerService[5];
        serviceList[0] = new providerService("Chocolate Massage", 123456, 500.0f, this);
        serviceList[1] = new providerService("Chocolate Therapy", 111111, 200.0f, this);
        serviceList[2] = new providerService("Dental Work", 222222, 300.0f, this);
        serviceList[3] = new providerService("Chocolate Intervention", 333333, 400.0f, this);
        serviceList[4] = new providerService("Liposuction", 444444, 700.0f, this);
        for(int i = 0; i < numberOfServices; ++i){
            serviceListBox.getChildren().add(serviceList[i].buildUIButton());
        }

        buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        getChildren().add(buttonPane);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if(Mode == 0)
                    checkMemberValidationInput();
                else if(Mode == 1){
                    checkServiceInput();
                }
            }
            if(event.getCode() == KeyCode.TAB){
                if(Mode == 1 && serviceFields[1].isFocused())
                    commentField.requestFocus();
            }
        });

        commentField.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER){
                if(event.isShiftDown()) {
                    commentField.appendText("\n");
                    event.consume();
                }
                else{
                    event.consume();
                    checkServiceInput();
                }
            }
            if(event.getCode() == KeyCode.TAB) {
                if(event.isShiftDown()) {
                    commentField.appendText("   ");
                    event.consume();
                }
                else{
                    event.consume();
                    serviceFields[0].requestFocus();
                }
            }
        });
        commentField.setOnMouseClicked(event -> {
            event.consume();
            if(commentField.getText().matches("Comments can contain no more than 200 characters.") ||
                    commentField.getText().matches("Service logged!"))
                commentField.setText("");
        });
    }

    //a class for adding clickable buttons to this UI pane.
    /*buttonFunction identities are as follows:
    0 -> Validate member
    1 -> Log service
    2 -> Enter input at service logging screen.
    3 -> Look up service
    4 -> Return to provider base mode
    5 -> Log out
     */
    private static class providerButton extends StackPane{
        public providerButton(String buttontext, int buttonFunction, providerPane Parent){
            Rectangle buttonBackground = new Rectangle(150, 60);
            buttonBackground.setFill(Color.CORAL);
            this.setOnMouseClicked(event -> {
                if(buttonFunction == 0){
                    Parent.checkMemberValidationInput();
                }
                else if(buttonFunction == 1){
                    Parent.swapToServiceLoggingMode();
                }
                else if(buttonFunction == 2){
                    Parent.checkServiceInput();
                }
                else if(buttonFunction == 3){
                    Parent.swapToServiceLookupMode();
                }
                else if(buttonFunction == 4){
                    Parent.swapToBaseMode();
                }
                else if(buttonFunction == 5){
                    Parent.logout();
                }
            });
            getChildren().add(buttonBackground);
            Text buttonText = new Text(buttontext);
            buttonText.setFont(Font.font("Verdana", 12));
            getChildren().add(buttonText);
        }
    }

    //login a new provider into the system and update GUI elements accordingly.
    public void login(providerNode toLogin){
        currentProvider = toLogin;
        currentMember = null; //clear member info from previous sessions
        providerNumberHeading.setText("Hello Provider #" + toLogin.getUserNumber());
        swapToBaseMode();
    }

    public void logout(){
        GUIRoot.swapToLoginPane();
    }

    public void swapToBaseMode(){
        Mode = 0;
        buttonPane.getChildren().clear();
        buttonPane.getChildren().addAll(Buttons[0], Buttons[1], Buttons[3], Buttons[5]);
        for(int i = 0; i < 3; ++i) {
            this.getChildren().remove(serviceLabels[i]);
            if(i != 2)
                this.getChildren().remove(serviceFields[i]);
        }
        this.getChildren().remove(commentField);
        this.getChildren().add(memberAuthenticationField);
        this.getChildren().add(memberInformation);
    }

    public void checkMemberValidationInput(){
        if(!isNumber(memberAuthenticationField.getText()) || memberAuthenticationField.getText().matches("")){
            memberInformation.setText("Please enter the member's\n 9 digit ID number.");
            return;
        }
        currentMember = ((memberNode)
                userHashTable.Retrieve(Integer.parseInt(memberAuthenticationField.getText()), 1));
        //search the user hash table for a member node (indicated by int arg "1") matching the text in the field
        if(currentMember == null)
            memberInformation.setText("Member not found.");
        else {
            if(currentMember.isSuspended()){
                memberInformation.setText("Member suspended!");
                currentMember = null;
            }
            else
                memberInformation.setText(currentMember.returnInfo());
        }
    }

    public void checkServiceInput(){
        boolean valid = true;

        if(!parseDate(serviceFields[0].getText())) {
            serviceFields[0].setText("Please enter date in MM-DD-YYYY format.");
            valid = false;
        }
        if(serviceFields[1].getText().length() != 6 || !isNumber(serviceFields[1].getText())) {
            serviceFields[1].setText("Please enter a valid service code as a 6-digit number.");
            valid = false;
        }
        else {
            currentService = null;
            for (int i = 0; i < numberOfServices; ++i) {
                if (serviceFields[1].getText().matches(serviceList[i].getID())) {
                    currentService = serviceList[i];
                    break;
                }
            }
            if (currentService == null) {
                serviceFields[1].setText("Service ID invalid.");
                valid = false;
            }
        }
        if(commentField.getText().length() > 200) {
            commentField.setText("Comments can contain no more than 200 characters.");
            valid = false;
        }
        if(valid){
            if(GUIRoot.getWeekStructure() == null)
                GUIRoot.addNewWeek();
            else if(!GUIRoot.getWeekStructure().isOfCurrentWeek())
                GUIRoot.addNewWeek();
            GUIRoot.getWeekStructure().addService(new serviceLogNode(
                    currentProvider, currentMember, serviceFields[0].getText(),
                    currentService, commentField.getText()
            ));
            currentService = null;
            serviceFields[0].setText("Service logged!");
            serviceFields[1].setText("Service logged!");
            commentField.setText("Service logged!");
        }
    }

    //parses a date entered by the user to ensure that it is in MM-DD-YYYY format.
    public boolean parseDate(String toParse){
        if(toParse.length() != 10)
            return false; //this format necessitates a 10-character input
        if(toParse.charAt(2) != '-' || toParse.charAt(5) != '-')
            return false;
        for(int i = 0; i < 10; ++i){ //ensure that each character is '-' or a decimal between 0 and 9
            if(i != 2 && i != 5 && !Character.isDigit(toParse.charAt(i)))
                return false;
        }
        return true;
    }

    public boolean isNumber(String toParse){
        for(int i = 0; i < toParse.length(); ++i){
            if(!Character.isDigit(toParse.charAt(i)))
                return false;
        }
        return true;
    }

    public void swapToServiceLookupMode(){
        Mode = 2;
        if (currentMember == null)
            memberInformation.setText("You must validate a ChocAn member \nbefore looking up services.");
        else {
            buttonPane.getChildren().clear();
            buttonPane.getChildren().add(Buttons[4]);
            Buttons[4].setTranslateX(0);
            Buttons[4].setTranslateY(200);
            for (int i = 0; i < 3; ++i) {
                this.getChildren().remove(serviceLabels[i]);
                if (i != 2)
                    this.getChildren().remove(serviceFields[i]);
            }
            this.getChildren().remove(commentField);
            this.getChildren().remove(memberAuthenticationField);
            this.getChildren().remove(memberInformation);
            //this.getChildren().add(searchField);
            buttonPane.getChildren().add(serviceListPane);
        }
    }

    public void swapToServiceLoggingMode() {
        Mode = 1;
        if (currentMember == null) {
            memberInformation.setText("You must validate a ChocAn member \nbefore logging services.");
        } else {
            buttonPane.getChildren().clear();
            buttonPane.getChildren().addAll(Buttons[2], Buttons[4]);
            Buttons[4].setTranslateX(-150);
            Buttons[4].setTranslateY(200);
            for (int i = 0; i < 3; ++i) {
                if (i != 2) {
                    this.getChildren().add(serviceFields[i]);
                    serviceFields[i].setText("");
                }
                this.getChildren().add(serviceLabels[i]);
            }
            if(currentService != null){
                serviceFields[1].setText(currentService.getID());
            }
            this.getChildren().add(commentField);
            commentField.setText("");
            this.getChildren().remove(memberAuthenticationField);
            this.getChildren().remove(memberInformation);
        }
    }

    public void setCurrentService(providerService currentservice){
        currentService = currentservice;
    }

    public providerService [] getServiceList(){
        return serviceList;
    }

    public void setServiceList(providerService [] toSet){
        serviceList = toSet;
    }
}