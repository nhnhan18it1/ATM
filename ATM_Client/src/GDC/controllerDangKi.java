package GDC;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.ResourceBundle;

import DAO.connectDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class controllerDangKi implements Initializable {

	String host="192.168.197.128";
	@FXML
	private TextField txtUser;
	boolean Cuser = false;
	@FXML
	private Label lblCuser;
	@FXML
	private PasswordField pwfPass;
	boolean CPass = false;
	@FXML
	private Label lblCPass;
	@FXML
	private PasswordField pwfRePass;
	boolean CRePass = false;
	@FXML
	private Label lblCRepass;
	@FXML
	private TextField txtname;
	boolean Cname = false;
	@FXML
	private Label lblCName;
	@FXML
	private TextField txtAddress;
	boolean CAdd = false;
	@FXML
	private Label lblCAdd;
	@FXML
	private TextField txtSDT;
	boolean CSDT = false;
	@FXML
	private Label lblCSDT;
	@FXML
	private DatePicker datePicker;
	@FXML
	private CheckBox cbAccept;

	public void Back(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("frMain.fxml"));
		Parent cnAdm = loader.load();
		cnAdm.getStylesheets().add(getClass().getResource("frMain.css").toExternalForm());
		Scene sc = new Scene(cnAdm);
		s.setScene(sc);

	}

	public void DangKi(ActionEvent e)
			throws AccessException, NumberFormatException, RemoteException, NotBoundException {
		if (Cuser && CSDT && CPass && CRePass && CPass && CAdd && Cname) {
			if (!String.valueOf(datePicker.getValue()).equals("")) {
				if (cbAccept.isSelected()) {
					connectDAO dao = new connectDAO();
					boolean r = dao.DangKi(txtUser.getText(), pwfPass.getText(), txtname.getText(),
							txtAddress.getText(), Integer.parseInt(txtSDT.getText()));
					if (r) {
						Alert al = new Alert(AlertType.INFORMATION);
						al.setContentText("Thành Công");
						al.show();
					} else {
						Alert al = new Alert(AlertType.ERROR);
						al.setContentText("Lỗi Hệ Thống Vui Lòng Thử Lại Sau");
						al.show();

					}
				} else {
					Alert al = new Alert(AlertType.ERROR);
					al.setContentText("Vui Lòng Chấp Nhận Điều Khoản Sử Dụng Của Chúng Tôi");
					al.show();
				}
			} else {
				Alert al = new Alert(AlertType.ERROR);
				al.setContentText("Vui Lòng Không DDeerr Trống Thông Tin");
				al.show();
			}

		} else {
			Alert al = new Alert(AlertType.ERROR);
			al.setContentText("Vui Lòng Nhập Đầy Đủ Thông Tin Trước Khi Tiến Hành Đăng Kí");
			al.show();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Socket sk = new Socket(host, 9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CheckinF();
	}

	public void SetDate(ActionEvent e) {
		datePicker.setConverter(new javafx.util.StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	public void CheckinF() {
		connectDAO dao = new connectDAO();
		Image imgtrue = new Image("file:///C:/Users/nhavb/eclipse-workspace/ATM_Client/img/icon-true.jpg");
		Image imgf = new Image("file:///C:/Users/nhavb/eclipse-workspace/ATM_Clien/img/icon-false.jpg");
		txtUser.textProperty().addListener((Observable, olsvalue, newvalue) -> {
			String s = newvalue;
			if (s.length() >= 8 && s.length() < 14) {
				try {
					if (dao.CheckUserName(s)) {
						ImageView imgv = new ImageView(imgtrue);
						imgv.setFitHeight(25);
						imgv.setFitWidth(25);
						lblCuser.setGraphic(imgv);
						Cuser = true;
					} else {
						ImageView imgv = new ImageView(imgf);
						imgv.setFitHeight(25);
						imgv.setFitWidth(25);
						lblCuser.setGraphic(imgv);
						Cuser = false;
					}
				} catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCuser.setGraphic(imgv);
				Cuser = false;
			}
		});
		pwfPass.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s = newvalue;
			if (s.length() >= 8 && s.length() < 14) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCPass.setGraphic(imgv);
				CPass = true;

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCPass.setGraphic(imgv);
				CPass = false;
			}
		});
		pwfRePass.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s = newvalue;
			if (s.equals(pwfPass.getText())) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCRepass.setGraphic(imgv);
				CRePass = true;

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCRepass.setGraphic(imgv);
				CRePass = false;
			}
		});
		txtAddress.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s = newvalue;
			if (s.length() >= 8) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCAdd.setGraphic(imgv);
				CAdd = true;

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCAdd.setGraphic(imgv);
				CAdd = false;
			}
		});
		txtname.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s = newvalue;
			if (s.length() >= 12) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCName.setGraphic(imgv);
				Cname = true;

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCName.setGraphic(imgv);
				Cname = false;
			}
		});
		txtSDT.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s = newvalue;
			if (s.length() >= 8 && s.length() < 14) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCSDT.setGraphic(imgv);
				CSDT = true;

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCSDT.setGraphic(imgv);
				CSDT = false;
			}
		});

	}

}
