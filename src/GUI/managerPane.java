package GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Created by Spaghetti on 5/2/2016.
 */
public class managerPane extends StackPane implements Serializable{
    private Text userInformation; //an information display for any user that the manager retrieves.
    private managerButton [] Buttons = new managerButton[3]; //buttons for navigating this part of the GUI

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
        userInformation = new Text();
        userInformation.setFont(Font.font("Verdana", 12));
        userInformation.setTranslateX(150);
        userInformation.setTranslateY(30);
    }

    //a class for adding clickable buttons to this UI pane.
    /*buttonFunction identities are as follows:
    0 -> Validate Member
    1 -> Validate Provider
    2 ->
    2 -> Enter Input.
     */
    private static class managerButton extends StackPane{
        public managerButton(String buttontext, int buttonFunction, managerPane Parent){
            Rectangle buttonBackground = new Rectangle(100, 100);
            buttonBackground.setFill(Color.CORNFLOWERBLUE);
            this.setOnMouseClicked(event -> {

            });
            getChildren().add(buttonBackground);
            Text buttonText = new Text(buttontext);
            buttonText.setFont(Font.font("Verdana", 12));
            getChildren().add(buttonText);

        }
    }
}
