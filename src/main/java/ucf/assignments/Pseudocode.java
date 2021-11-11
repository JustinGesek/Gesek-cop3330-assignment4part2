
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

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Pseudocode extends Application {

    Parent root ;
    Scene scene ;
    public static ListCollection myLists;
    boolean viewingLists = true;
    int listBeingViewed = -1;
    int filterSelection = 0;
    int fileAction = 0;
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
            tf.setText("");
            addList(listName);


            showListManual();
        }


    }

    public void addList(String listName) {
        for (List curList : myLists.lists){
            if (curList.getTitle().equals(listName))
                return;
        }
        myLists.addList(listName);
    }

    public void clearCurrentList(ActionEvent actionEvent){
        if (!viewingLists){
            Button but = (Button) actionEvent.getTarget();

            // use button to get the scene for lookups
            scene = but.getScene();

            myLists.lists.get(listBeingViewed).items.clear();
            showItemsManual();
        }
    }

    public void createNewItemHandler(ActionEvent actionEvent) {
        if (!viewingLists){
            // get the button that called this handler
            Button but = (Button) actionEvent.getTarget();

            // use button to get the scene for lookups
            scene = but.getScene();

            String but_text = but.getText();



            // get the desired name of the new item
            TextField tf = (TextField) scene.lookup("#itemName");
            String itemName = tf.getText();
            if (itemName.equals("")){
                return;
            }

            tf.setText("");

            for (Item curItem : myLists.lists.get(listBeingViewed).items){
                if (curItem.getDescription().equals(itemName))
                    return;
            }

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

                // getting the item to know whether or not it's checked
                VBox vb = (VBox) scene.lookup("#ScrollVBox");
                HBox itemBox = (HBox) vb.getChildren().get(item_index) ;
                CheckBox item_cb = (CheckBox) itemBox.lookup("#checkBox_" + item_index);

                Item item = new Item(itemName, itemDate, item_cb.isSelected());
                myLists.lists.get(listBeingViewed).items.set(item_index, item ) ;
                // now change the display
                //VBox vb = (VBox) scene.lookup("#ScrollVBox");
                vb.getChildren().set(item_index, createItemDisplay(item, item_index )) ;

                // then change back the button text
                but.setText("New item");
            }
        }
    }

    public void filterChangedHandler(ActionEvent actionEvent){
        ComboBox cb = (ComboBox) actionEvent.getTarget();
        filterSelection =  cb.getSelectionModel().getSelectedIndex();

        showItemsManual();
    }

    public void showListHandler(ActionEvent actionEvent) {
        // get the button that called this handler
        Button but = (Button) actionEvent.getTarget();

        // use button to get the scene for lookups
        scene = but.getScene();

        showListManual();
    }

    public void changeFileChoiceHandler(ActionEvent actionEvent){
        ComboBox cb = (ComboBox) actionEvent.getTarget();
        fileAction =  cb.getSelectionModel().getSelectedIndex();
    }

    public void fileExecutionHandler(ActionEvent actionEvent){
        // extract file name
        // get the button that called this handler
        Button but = (Button) actionEvent.getTarget();
        // use button to get the scene for lookups
        scene = but.getScene();
        TextField fileName = (TextField) scene.lookup("#filename");

        if (fileAction == 0){  // saving a list to file
            String fileChoice = fileName.getText();
            if (fileChoice != ""){
                saveFile(fileChoice);
            }
            else {
                for (int i = 0; i < myLists.lists.size(); i ++){
                    listBeingViewed = i;
                    saveFile(myLists.lists.get(i).getTitle() + ".txt");
                }
            }

        }
        else if (fileAction == 1){ // loading a list from file
            String loadSpec = fileName.getText();
            if (loadSpec.contains(",")){
                String[] filenames = loadSpec.split(",");
                for (String name : filenames){
                    loadFile(name);
                }
            }
            else {
                loadFile(loadSpec);
            }

            viewingLists = true ;
            showListManual();
        }
    }

    public void saveFile(String fileName){
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter(fileName, true); //true tells to append data.
            out = new BufferedWriter(fstream);
            // go thorugh each item in list and write to file
            for (int i = 0; i < myLists.lists.get(listBeingViewed).items.size(); i++) {
                Item curItem = myLists.lists.get(listBeingViewed).items.get(i);

                GregorianCalendar calendar = curItem.getDueDate() ;
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                fmt.setCalendar(calendar);
                String dateFormatted = fmt.format(calendar.getTime());

                String itemText =curItem.getDescription() + "~" + dateFormatted + "~";

                if (curItem.getIsCompleted())
                    itemText += "TRUE";
                else
                    itemText += "FALSE";

                out.write(itemText + "\n");
            }
            out.close();
        }


        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void loadFile(String filename)  {
        try {
            File file = new File(
                    filename);

            BufferedReader br
                    = new BufferedReader(new FileReader(file));

            // Declaring a string variable
            String st;


            String listName = filename.replace(".txt", "");
            // create new list
            myLists.addList(listName);

            List newList = myLists.lists.get(myLists.lists.size() - 1);
            while ((st = br.readLine()) != null) {
                String[] itemProperties = st.split("~");


                //now create new item
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = df.parse(itemProperties[1]);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                GregorianCalendar itemDate = (GregorianCalendar) cal;
                Boolean itemComp = true;
                if (itemProperties[2].contains("F"))
                    itemComp = false;

                Item newItem = new Item(itemProperties[0], itemDate, itemComp);

                newList.addItem(newItem);

            }
        }
        catch (IOException | ParseException e){

        }
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

    public void removeItemHanlder(Button removeBut){
        String id = removeBut.getId();
        int numerical_id = Integer.parseInt(id.split("_")[1]);
        scene = removeBut.getScene();

        myLists.lists.get(listBeingViewed).items.remove( numerical_id);
        showItemsManual();
    }

    public void removeListHanlder(Button removeBut){
        String id = removeBut.getId();
        int numerical_id = Integer.parseInt(id.split("_")[1]);
        scene = removeBut.getScene();

        myLists.lists.remove( numerical_id);
        showListManual();
    }

    public void editTitleHanlder(Button editBut){
        String id = editBut.getId();
        int numerical_id = Integer.parseInt(id.split("_")[1]);
        scene = editBut.getScene();
        VBox vb = (VBox) scene.lookup("#ScrollVBox");
        HBox hb = (HBox) vb.getChildren().get(numerical_id);

        if (editBut.getText().contains("Edit")) {
            TextField newTitle = new TextField();
            newTitle.setId("newTitle_" + numerical_id);
            hb.getChildren().add(newTitle);
            editBut.setText("Save Title");
        }
        else {
            TextField newTitleField = (TextField) scene.lookup("#newTitle_" + numerical_id);
            String newTitle = newTitleField.getText();
            myLists.lists.get(numerical_id).setTitle(newTitle);
            editBut.setText("Edit Title");
            hb.getChildren().remove(hb.getChildren().size() -1);
            hb.getChildren().set(0, new Label(newTitle));
        }

    }



    public void showListManual(){
        viewingLists = true ;
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

            Button editTitle = new Button("Edit Title");
            editTitle.setId("editButton_" + i);
            editTitle.setOnAction(action -> { editTitleHanlder(editTitle); });

            Button removeList = new Button("Remove This List");
            removeList.setId("removeButton_" + i);
            removeList.setOnAction(action -> { removeListHanlder(removeList); });

            HBox hb = new HBox(label, showItems, editTitle,  removeList );
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

        Button removeItem = new Button("Remove This Item");
        removeItem.setId("removeButton_" + i);
        removeItem.setOnAction(action -> { removeItemHanlder(removeItem); });

        Label completed = new Label("Complete: ");
        CheckBox cb = new CheckBox();
        cb.setId("checkBox_" + i);

        cb.setSelected(curItem.getIsCompleted());

        HBox hb = new HBox(label, completed, cb, editItem, removeItem  );

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
            Boolean itemCompleted = curItem.getIsCompleted();
            if (!itemCompleted && filterSelection == 1 ||
                itemCompleted && filterSelection == 2)
                continue ;
            HBox itemBox = createItemDisplay(curItem, i);
            vb.getChildren().add(itemBox);
        }


    }









}


