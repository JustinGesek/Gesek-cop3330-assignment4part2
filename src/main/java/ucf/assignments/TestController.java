/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Justin Gesek
 */

package ucf.assignments;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TestController {

    @FXML
    private Label testLabel;

    public void setText(String data) {
        testLabel.setText(data);
    }

}