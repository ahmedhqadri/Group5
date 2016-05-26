package serviceDataStructure;

import GUI.providerPane;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Created by Spaghetti on 5/23/2016.
 */
//class for provider services. These are initialized statically so that we can save memory by having
//multiple references to a single object of the class instead of having to duplicate the data members
//for every single serviceLogNode that logs a given service.
public class providerService implements Serializable {
    private String serviceName;
    private int serviceID;
    private float serviceCost;
    private static providerPane Parent;

    public providerService(String servicename, int serviceid, float servicecost, providerPane parent){
        serviceName = servicename;
        serviceID = serviceid;
        serviceCost = servicecost;
        Parent = parent;
    }

    public StackPane buildUIButton() {
        StackPane button = new StackPane();
        button.setAlignment(Pos.CENTER);
        Rectangle buttonBackground = new Rectangle(310, 60);

        buttonBackground.setFill(Color.WHITE);
        button.setOnMouseClicked(event -> {
            Parent.setCurrentService(this);
            Parent.swapToServiceLoggingMode();
        });
        button.getChildren().add(buttonBackground);
        Line line = new Line(0, 0, 310, 0);
        line.setStroke(Color.BLACK);
        line.setTranslateY(29);
        button.getChildren().add(line);
        Text nametext = new Text(serviceName);
        nametext.setFont(Font.font("Verdana", 12));
        nametext.setTranslateX(-75);
        Text idtext = new Text(serviceID + "");
        idtext.setFont(Font.font("Verdana", 12));
        idtext.setTranslateX(40);
        Text costtext = new Text("$" + serviceCost);
        costtext.setFont(Font.font("Verdana", 12));
        costtext.setTranslateX(100);
        button.getChildren().addAll(nametext, idtext, costtext);
        return button;
    }

    public String getName(){
        return serviceName;
    }

    public String getID(){
        return serviceID + "";
    }

    public String getCost(){
        return "$" + serviceCost;
    }
}

