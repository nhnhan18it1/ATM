package GDC;

import java.io.IOException;

import Main.mainfrDangNhap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class controllerfrMain {

	public void DangNhap(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		mainfrDangNhap mdn=new mainfrDangNhap();
		mdn.start(s);
	}
	public void DangKi(ActionEvent e) {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		mainfrDangKi dk=new mainfrDangKi();
		dk.start(s);
	}
	
}
