package Main;

import java.io.IOException;
import java.util.Enumeration;

import ATMCN.TaiKhoan;
import GDC.mainfrMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class controllerMainM {

	@FXML 
	private TextField txtID;
	@FXML 
	private TextField txtSoDu;
	public void SetInf(int ID,int SD) {
		txtID.setText(String.valueOf(ID));
		txtSoDu.setText(String.valueOf(SD));
	}
	public void NapTien(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("frNapTien.fxml"));
		Parent cnAdm = loader.load();
		//cnAdm.getStylesheets().add(getClass().getResource("ND.css").toExternalForm());
		Scene sc = new Scene(cnAdm);
		controllerNapTien cta = loader.getController();
		cta.SetInf(Integer.parseInt(txtID.getText()), Integer.parseInt(txtSoDu.getText()));
		s.setScene(sc);
	}
	public void ChuyenTien(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("frChuyenTien.fxml"));
		Parent cnAdm = loader.load();
		//cnAdm.getStylesheets().add(getClass().getResource("ND.css").toExternalForm());
		Scene sc = new Scene(cnAdm);
		controllerChuyenTien cta = loader.getController();
		cta.SetInf(Integer.parseInt(txtID.getText()), Integer.parseInt(txtSoDu.getText()));
		s.setScene(sc);
	}
	public void RutTien(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("frRutTien.fxml"));
		Parent cnAdm = loader.load();
		//cnAdm.getStylesheets().add(getClass().getResource("ND.css").toExternalForm());
		Scene sc = new Scene(cnAdm);
		controllerRutTien cta = loader.getController();
		cta.SetInf(Integer.parseInt(txtID.getText()), Integer.parseInt(txtSoDu.getText()));
		s.setScene(sc);
	}
	public void BienDongSoDu(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("frBienDongSoDu.fxml"));
		Parent cnAdm = loader.load();
		//cnAdm.getStylesheets().add(getClass().getResource("ND.css").toExternalForm());
		Scene sc = new Scene(cnAdm);
		controllerBienDongSoDu cta = loader.getController();
		cta.SetInf(Integer.parseInt(txtID.getText()), Integer.parseInt(txtSoDu.getText()));
		s.setScene(sc);
	}
	public void DoiMatKhau(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("frDoiMatKhau.fxml"));
		Parent cnAdm = loader.load();
		//cnAdm.getStylesheets().add(getClass().getResource("ND.css").toExternalForm());
		Scene sc = new Scene(cnAdm);
		controllerDoiMatKhau cta = loader.getController();
		cta.SetInf(Integer.parseInt(txtID.getText()), Integer.parseInt(txtSoDu.getText()));
		s.setScene(sc);
	}
	public void NgungGiaoDich(ActionEvent e) {
		Stage s=(Stage)((Node)e.getSource()).getScene().getWindow();
		mainfrMain m=new mainfrMain();
		m.start(s);
	}
}
