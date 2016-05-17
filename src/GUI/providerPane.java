package GUI;

import com.sun.deploy.util.StringUtils;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import serviceDataStructure.serviceNode;
import userDataStructure.memberNode;
import userDataStructure.providerNode;

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
    private TextField searchField; //a textfield for searching for service IDs
    private int selectedField; //index of the service field in which we are currently entering text;
    //used primarily to allow keyboard-based navigation of the fields.
    private providerButton [] Buttons; //array of buttons associated with this pane. (see private class)


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

        searchField = new TextField();
        searchField.setTranslateY(-100);
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

        buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        getChildren().add(buttonPane);
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
            buttonBackground.setOnMouseReleased(event -> {
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
        currentMember = ((memberNode)
                GUIRoot.getUserStructure().Retrieve(Integer.parseInt(memberAuthenticationField.getText()), 1));
        //search the user hash table for a member node (indicated by int arg "1") matching the text in the field
        if(currentMember == null)
            memberInformation.setText("Member not found.");
        else
            memberInformation.setText(currentMember.getMemberInfo());
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
        if(commentField.getText().length() > 200) {
            commentField.setText("Comments can contain no more than 200 characters.");
            valid = false;
        }
        if(valid){
            if(GUIRoot.getWeekStructure() == null)
                GUIRoot.addNewWeek();
            else if(!GUIRoot.getWeekStructure().isOfCurrentWeek())
                GUIRoot.addNewWeek();
            GUIRoot.getWeekStructure().addService(new serviceNode(
                    currentProvider, currentMember, serviceFields[0].getText(),
                    Integer.parseInt(serviceFields[1].getText()), commentField.getText()
            ));
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
        buttonPane.getChildren().clear();
        buttonPane.getChildren().add(Buttons[4]);
        Buttons[4].setTranslateX(0);
        Buttons[4].setTranslateY(200);
        for(int i = 0; i < 3; ++i) {
            this.getChildren().remove(serviceLabels[i]);
            if(i != 2)
                this.getChildren().remove(serviceFields[i]);
        }
        this.getChildren().remove(commentField);
        this.getChildren().add(searchField);
    }

    public void swapToServiceLoggingMode() {
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
            this.getChildren().add(commentField);
            commentField.setText("");
            this.getChildren().remove(memberAuthenticationField);
            this.getChildren().remove(memberInformation);
            selectedField = 0; //start the selected field at 0.
        }
    }
}