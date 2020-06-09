package Main;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

import ATMCN.TaiKhoan;
import DAO.connectDAO;
import GDC.mainfrMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerfrDangNhap implements Initializable {
	String host="localhost";
	@FXML
	private TextField txtUs;
	@FXML
	private PasswordField pwfPass;
	public void Back(ActionEvent e) {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		mainfrMain mm=new mainfrMain();
		mm.start(s);
	}
	public void DangNhap(ActionEvent e) throws NotBoundException, IOException {
		connectDAO dao=new connectDAO();
		Vector list= dao.Login(txtUs.getText(), pwfPass.getText());
		if (list.size()==0) {
			Alert al=new Alert(AlertType.INFORMATION);
			al.setContentText("Vui Lòng Nhập Chính Xác Tài Khoản Mật Khẩu");
			al.show();
		}
		else {
			
			Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("frMainM.fxml"));
			Parent cnAdm = loader.load();
			//cnAdm.getStylesheets().add(getClass().getResource("ND.css").toExternalForm());
			Scene sc = new Scene(cnAdm);
			controllerMainM cta = loader.getController();
			Enumeration en=list.elements();
			while (en.hasMoreElements()) {
				TaiKhoan tk = (TaiKhoan) en.nextElement();
				cta.SetInf(tk.getID(), tk.getSoDu());;
			}
			
			Alert al = new Alert(AlertType.INFORMATION);
			al.setHeaderText("Dang nhap Thanh cong");
			al.show();
			s.setScene(sc);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			Socket sk=new Socket(host, 9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
