
package ucf.assignments;

import com.example.gesekcop3330assignment4.HelloApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Pseudocode extends Application {

    Parent root ;
    Scene scene ;
    static ListCollection myLists;
    boolean viewingLists = true;
    int listBeingViewed = -1;
    public static void main(String[] args) {
        myLists = new ListCollection();
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)  throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Pseudocode.class.getResource("/ucf.assignments/pseudocode.fxml"));

        //fxmlLoader.
        root = fxmlLoader.load();
        scene = new Scene(root, 850, 500);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public Text actionbar;



    public void createNewListHandler(ActionEvent actionEvent) {
        if (viewingLists) {
            // get the button that called this handler
            Button but = (Button) actionEvent.getTarget();

            // use button to get the scene for lookups
            scene = but.getScene();

            // get the desired name of the new list
            TextField tf = (TextField) scene.lookup("#ListName");
            String listName = tf.getText();
            myLists.addList(listName);

            showListManual();
        }


    }

    public void createNewItemHandler(ActionEvent actionEvent) {
        if (!viewingLists){
            // get the button that called this handler
            Button but = (Button) actionEvent.getTarget();

            // use button to get the scene for lookups
            scene = but.getScene();

            // get the desired name of the new list
            TextField tf = (TextField) scene.lookup("#itemName");
            String itemName = tf.getText();
            myLists.lists.get(listBeingViewed).addItem(new Item(itemName, new Date( ), false ));

            showItemsManual();
        }
    }

    public void showListHandler(ActionEvent actionEvent) {
        // get the button that called this handler
        Button but = (Button) actionEvent.getTarget();

        // use button to get the scene for lookups
        scene = but.getScene();

        showListManual();
    }

    public void showItemsHandler(Button but){
        String id = but.getId();
        int numerical_id = Integer.parseInt(id.split("_")[1]);
        System.out.println(" list being viewed is now " + listBeingViewed);
        listBeingViewed = numerical_id;
        viewingLists = false ;
        showItemsManual();
        //System.out.println("a button was clicked for: " + id);
    }

    public void showListManual(){
        VBox vb = (VBox) scene.lookup("#ScrollVBox");
        int numKids = vb.getChildren().size();
        vb.getChildren().remove(0, numKids);
        // System.out.println("The list should be empty now!!!!");



        for (int i =0 ; i < myLists.lists.size(); i++) {

            List curList =myLists.lists.get(i) ;
            Label label = new  Label(curList.getTitle());
            Button showItems = new Button("Show List Items");
            showItems.setId("showButton_" + i);
            //EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { showItemsHandler() };
            showItems.setOnAction(action -> { showItemsHandler(showItems); });
            HBox hb = new HBox(label, showItems );
            vb.getChildren().add(hb);
        }
    }

    public void showItemsManual(){
        VBox vb = (VBox) scene.lookup("#ScrollVBox");
        int numKids = vb.getChildren().size();
        vb.getChildren().remove(0, numKids);
        // System.out.println("The list should be empty now!!!!");


        System.out.println("trying to view list for index: " + listBeingViewed);
        for (int i =0 ; i < myLists.lists.get(listBeingViewed).items.size(); i++) {

            Item curItem =myLists.lists.get(listBeingViewed).items.get(i) ;
            Label label = new  Label(curItem.getDescription());

            HBox hb = new HBox(label );
            vb.getChildren().add(hb);
        }
    }







}


