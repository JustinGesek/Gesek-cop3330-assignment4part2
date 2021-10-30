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