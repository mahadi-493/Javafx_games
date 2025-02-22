package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameZoneMainController {
	@FXML
	Text userget;
	@FXML
	private ImageView gamepad;
	@FXML
    private Button buttonTicTacToe;
	@FXML
	private Button buttonHangman;
	@FXML
	private Button buttonCricket;
	 
	
	public void displayname(String userge) {
	    
		userget.setText(userge.toUpperCase());
	}
	

    @FXML
    void onTicTacToe(ActionEvent event) throws IOException {
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("TicTacToe.fxml"));
         Parent root = loader.load();
         tictactoeController ticTacToeController = loader.getController();
         ticTacToeController.setUsername(userget.getText());

         Scene scene = new Scene(root);
         Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
         window.setScene(scene);
         window.show();
         
    }
    @FXML
    void onHangman(ActionEvent event) throws IOException {
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("HangMan.fxml"));
         Parent root = loader.load();
         HangmanController hangmanController = loader.getController();
         hangmanController.setUsername(userget.getText());

         Scene scene = new Scene(root);
         Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
         window.setScene(scene);
         window.show();
    }
    @FXML
    void onlogout(ActionEvent event) throws IOException {
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
         Parent root = loader.load();
         Scene scene = new Scene(root);
         Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
         window.setScene(scene);
         window.show();
    }
    
}
