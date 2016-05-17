package GUI;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import userDataStructure.managerNode;
import userDataStructure.providerNode;
import userDataStructure.userNode;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;

/**
 * Created by Spaghetti on 4/25/2016.
 */
public class loginPane extends StackPane {
    private TextField managerTextField;
    private TextField providerTextField;
    private String textInField;
    private int whichSide; //0 denotes that the user is trying to log in as a manager;
    //1 denotes that the user has yet to select a user type to log in as;
    //2 denotes that the user is trying to log in as a provider.
    private Text errorMessage;

    public loginPane(){
        setAlignment(Pos.CENTER);
        Rectangle Background = new Rectangle(2400, 800);
        Background.setFill(new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.DEEPSKYBLUE),
                        new Stop(0.4, Color.BLUEVIOLET),
                        new Stop(0.6, Color.CORAL),
                        new Stop(1, Color.ORANGE)}));

        //code for login text fields
        managerTextField = new TextField();
        managerTextField.setTranslateX(-800);
        managerTextField.setMaxWidth(200);
        managerTextField.setMaxHeight(50);
        managerTextField.setOnMouseReleased(event2 -> {
            managerTextField.setText("");
        });

        providerTextField = new TextField();
        providerTextField.setTranslateX(800);
        providerTextField.setMaxWidth(200);
        providerTextField.setMaxHeight(50);
        providerTextField.setOnMouseReleased(event2 -> {
            providerTextField.setText("");
        });

        //error message code for when the user enters invalid login information.
        errorMessage = new Text("Input invalid.");
        errorMessage.setFill(Color.RED);
        errorMessage.setFont(Font.font("Verdana, 18"));
        errorMessage.setTranslateY(50);

        getChildren().add(Background);
        getChildren().add(managerTextField);
        getChildren().add(providerTextField); //add the text field to the root pane.

        loginButton managerToCenter = new loginButton(600, 1, Color.MOCCASIN, "Back", this);
        loginButton providerToCenter = new loginButton(-600, 1, Color.LIGHTBLUE, "Back", this);
        loginButton centerToManager = new loginButton(-200, 0, Color.CORNFLOWERBLUE, "Manager", this);
        loginButton centerToProvider = new loginButton(200, 2, Color.ORANGE, "Provider", this);
        GridPane buttonPane = new GridPane(); //establish gridpane so that all buttons are on the same level
        //within the loginpane's stackpane's stack.
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(managerToCenter, providerToCenter, centerToManager, centerToProvider);
        getChildren().add(buttonPane);

        setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                if (whichSide != 1) {
                    errorMessage.setTranslateX((whichSide - 1)*800);
                    try {
                        userNode Retrieved;
                        if(whichSide == 0){ //manager case
                            textInField = managerTextField.getText();
                        }
                        else{ //provider case.
                            textInField = providerTextField.getText();
                        }

                        Retrieved = GUIRoot.getUserStructure().Retrieve(Integer.parseInt(textInField), whichSide);
                        //attempt to retrieve an account node of a manager or provider based on whichside.

                        //TEST USER RETRIEVAL
                        if (Retrieved == null) {
                            getChildren().remove(errorMessage); //ensure that this isn't already added to the GUI.
                            getChildren().add(errorMessage);
                        } else {
                            if(whichSide == 0) { //manager case
                                GUIRoot.swapToManagerPane((managerNode)Retrieved);
                            }
                            else{ //provider case
                                GUIRoot.swapToProviderPane((providerNode)Retrieved);
                            }
                        }
                    } catch (Exception e) {
                        getChildren().remove(errorMessage); //ensure that this object isn't already added.
                        getChildren().add(errorMessage);
                    }
                }
            }
            //automatically clear and start entering text if the user types without clicking on the field.
            else if(whichSide == 0 && managerTextField.getText().equals("           Please enter your ID.")){
                getChildren().remove(errorMessage);
                managerTextField.requestFocus();
                managerTextField.clear();
                managerTextField.appendText(event.getText());
            }
            //automatically clear and start entering text if the user types without clicking on the field.
            else if(whichSide == 2 && providerTextField.getText().equals("           Please enter your ID.")){
                getChildren().remove(errorMessage);
                providerTextField.requestFocus();
                providerTextField.clear();
                providerTextField.appendText(event.getText());
            }
            else{
                getChildren().remove(errorMessage);
            }
        });
    }

    public void setSide(int toSet){
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        final KeyValue horizontalMovement = new KeyValue(translateXProperty(),
                -800*(toSet-1));
        final KeyFrame duration = new KeyFrame(Duration.millis(250), horizontalMovement);
        timeline.getKeyFrames().add(duration);
        timeline.play();
        if(toSet == 0){
            providerTextField.requestFocus();
            managerTextField.setText("           Please enter your ID.");
        }
        if(toSet == 2){
            managerTextField.requestFocus();
            providerTextField.setText("           Please enter your ID.");
        }
        getChildren().remove(errorMessage); //ensure that we reset any extant error messages from previous entries.
        whichSide = toSet;
    }

    private static class loginButton extends StackPane{
        public loginButton(int x, int endside, Color color, String buttonText, loginPane Parent){

            setTranslateX(x);
            Rectangle buttonBackground = new Rectangle(100, 100);
            buttonBackground.setFill(color);
            Text ButtonText = new Text(buttonText);
            ButtonText.setFont(Font.font("Verdana",12));
            getChildren().add(buttonBackground);
            getChildren().add(ButtonText);
            buttonBackground.setOnMouseReleased(event1 -> {
                Parent.setSide(endside);
            });
        }
    }
}
