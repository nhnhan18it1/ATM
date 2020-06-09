package DAO;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import ATMCN.CN;
import ATMCN.TaiKhoan;

public class connectDAO {
	
	String host="localhost";
	public Vector Login(String us, String pass) throws NotBoundException, UnknownHostException, IOException {

		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		Vector list = n1.exSelectTaiKhoan(
				"SELECT * FROM taikhoan WHERE taikhoan.TaiKhoan='" + us + "' AND taikhoan.MatKhau='" + pass + "'");
		return list;
	}

	public boolean DangKi(String us, String pass, String Name, String Address, int SDT)
			throws AccessException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		boolean re = n1.exUpdate(
				"INSERT INTO taikhoan(taikhoan.TaiKhoan,taikhoan.MatKhau,taikhoan.DiaChi,taikhoan.SDT,taikhoan.TenChuTK,taikhoan.SoDu) VALUES ('"
						+ us + "','" + pass + "','" + Address + "'," + SDT + ",'" + Name + "',0)");
		return re;
	}

	public boolean CheckUserName(String us) throws AccessException, RemoteException, NotBoundException {
		boolean re = false;
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		Vector list = n1.exSelectTaiKhoan("SELECT * FROM taikhoan WHERE taikhoan.TaiKhoan='" + us + "' ");
		if (list.size() == 0) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	public boolean NapTien(int ID,int sdc, int ST) throws AccessException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		int newm =ST+sdc;
		boolean r = n1.exUpdate("UPDATE taikhoan SET taikhoan.SoDu=" + newm + " WHERE taikhoan.ID=" + ID);
		if (r) {
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = df.format(date);
			n1.exUpdate(
					"INSERT INTO biendongsd(biendongsd.IDTK,biendongsd.STBienDong,biendongsd.ThoiGian,biendongsd.Type) VALUES("
							+ ID + "," + ST + ",'" + dateString + "','NapTien')");
		}
		return r;
	}

	public Vector GetBDSD(int ID) throws AccessException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		Vector list = n1.exSelectBienDong(
				"SELECT * FROM biendongsd WHERE biendongsd.IDTK=" + ID + " OR biendongsd.IDTK_TD=" + ID);
		return list;
	}

	public Vector getInfor(int ID) throws AccessException, RemoteException, NotBoundException {
		
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		Vector list = n1.exSelectTaiKhoan("SELECT * FROM taikhoan WHERE taikhoan.ID=" + ID);
		
		return list;
		
	}
	public boolean doimk(int ID,String np) throws AccessException, RemoteException, NotBoundException {
		
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		boolean re=n1.exUpdate("UPDATE taikhoan SET taikhoan.MatKhau='"+np+"' WHERE taikhoan.ID="+ID);
		return re;
	}
	public boolean RutTien(int ID,int ST,int OST) throws AccessException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		boolean r= n1.exUpdate("UPDATE taikhoan SET taikhoan.SoDu="+(OST-ST)+" WHERE taikhoan.ID="+ID);
		if (r) {
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = df.format(date);
			n1.exUpdate(
					"INSERT INTO biendongsd(biendongsd.IDTK,biendongsd.STBienDong,biendongsd.ThoiGian,biendongsd.Type) VALUES("
							+ ID + "," + ST + ",'" + dateString + "','RutTien')");
		}
		return r;
	}
	public boolean ChuyenTien(int IDC,int IDN,int ST,int OST) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(host, 2019);
		CN n1 = (CN) registry.lookup("svtr");
		boolean r= n1.exUpdate("UPDATE taikhoan SET taikhoan.SoDu="+(OST-ST)+" WHERE taikhoan.ID="+IDC);
		if (r) {
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = df.format(date);
			n1.exUpdate("INSERT INTO biendongsd(biendongsd.IDTK,biendongsd.STBienDong,biendongsd.ThoiGian,biendongsd.IDTK_TD,biendongsd.Type) VALUES("+ IDC + "," + ST + ",'" + dateString + "',"+IDN+",'ChuyenTien')");
			n1.exUpdate("UPDATE taikhoan SET taikhoan.SoDu=((taikhoan.SoDu)+"+ST+") WHERE taikhoan.ID=1");
		}
		return r;
	}
	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException {
		connectDAO connectDAO=new connectDAO();
		System.out.println(connectDAO.CheckOldPass(2, "123456"));
		//boolean b=;
//		if (b) {
//			System.out.println("A");
//		}
//		else {
//			System.out.println("B");
//		}
//		
	}
	public boolean CheckOldPass(int ID,String pass) throws AccessException, RemoteException, NotBoundException {
		
		Vector list=getInfor(ID);
		Enumeration en=list.elements();
		String pp="";
		while (en.hasMoreElements()) {
			TaiKhoan tk = (TaiKhoan) en.nextElement();
			pp=tk.getMatKhau();
		}
		if (pp.equals(pass)) {
			return true;
		}
		else {
			return false;
		}
		
	}
}
