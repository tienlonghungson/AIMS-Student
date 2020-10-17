package views.handler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import utils.Configs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResultHanler extends ScreenHandler implements Initializable {

	public ResultHanler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	private Label pageTitle;

	@FXML
	private Label resultLabel;

	@FXML
	private Label messageLabel;

	@FXML
	private Button okButton;

	@FXML
	void confirmPayment(MouseEvent event) throws IOException {
		ScreenHandler cartController = new CartHandler(this.stage, Configs.CART_SCREEN_PATH);
		cartController.setScreenTitle("Cart Screen");
		cartController.show();
	}

}