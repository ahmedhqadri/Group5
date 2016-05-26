package GUI;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Created by Spaghetti on 5/2/2016.
 */
public class managerPane extends StackPane implements Serializable{

    //a class for adding clickable buttons to this UI pane.
    /*buttonFunction identities are as follows:
    0 -> Validate Member
    1 -> Log Service
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
