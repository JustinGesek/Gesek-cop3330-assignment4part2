
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

import java.time.LocalDate;
import java.util.*;

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

            String but_text = but.getText();



            // get the desired name of the new list
            TextField tf = (TextField) scene.lookup("#itemName");
            String itemName = tf.getText();

            DatePicker dp = (DatePicker) scene.lookup("#itemDate");
            LocalDate date = dp.getValue();
            int year = date.getYear();
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            GregorianCalendar itemDate = new GregorianCalendar();
            itemDate.set(year, month, day);

            if (! but_text.contains("Edit")) {
                myLists.lists.get(listBeingViewed).addItem(new Item(itemName, itemDate, false));

                showItemsManual();
            }
            else {
                int item_index = Integer.parseInt(but_text.split(" ")[2]) - 1;
                Item item = new Item(itemName, itemDate, false);
                myLists.lists.get(listBeingViewed).items.set(item_index, item ) ;
                // now change the display
                VBox vb = (VBox) scene.lookup("#ScrollVBox");
                vb.getChildren().set(item_index, createItemDisplay(item, item_index )) ;

                // then change back the button text
                but.setText("New item");
            }
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

    public void editItemsHanlder(Button editBut){
        String id = editBut.getId();
        int numerical_id = Integer.parseInt(id.split("_")[1]);
        scene = editBut.getScene();
        Button itemButton = (Button) scene.lookup("#itemButton");
        itemButton.setText("Edit Item " + (numerical_id + 1));

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

    public HBox createItemDisplay(Item curItem, int i){
        GregorianCalendar itemDate = curItem.getDueDate();
        int month = itemDate.get(Calendar.MONTH);
        int day = itemDate.get(Calendar.DAY_OF_MONTH);
        int year = itemDate.get(Calendar.YEAR);

        Label label = new  Label(curItem.getDescription() + " Due: " +
                year + "-"  + month  +  "-" + day);

        // add button
        Button editItem = new Button("Edit This Item");
        editItem.setId("editButton_" + i);
        editItem.setOnAction(action -> { editItemsHanlder(editItem); });


        HBox hb = new HBox(label, editItem );

        return hb;
    }

    public void showItemsManual(){
        VBox vb = (VBox) scene.lookup("#ScrollVBox");
        int numKids = vb.getChildren().size();
        vb.getChildren().remove(0, numKids);
        // System.out.println("The list should be empty now!!!!");


        System.out.println("trying to view list for index: " + listBeingViewed);
        for (int i =0 ; i < myLists.lists.get(listBeingViewed).items.size(); i++) {

            Item curItem =myLists.lists.get(listBeingViewed).items.get(i) ;
            HBox itemBox = createItemDisplay(curItem, i);
            vb.getChildren().add(itemBox);
        }


    }









}


