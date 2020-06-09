package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import ATMCN.BienDongSD;
import ATMCN.TaiKhoan;

public class ServerDao {

	
		public static final String hostName = "localhost";
		public static final String userName = "root";
		public static final String password = "";
		public static final String dbname = "atmdb";
		private static Connection Connection;

		public void testcn() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbname, userName,
						password);
				if (conn != null) {
					System.out.println("Thanh cong");
				} else {
					System.out.println("that bai");
				}
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("Select * from TaiKhoan");
				while (rs.next()) {
					System.out.println(rs.getInt("ID"));
				}

				conn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		public boolean Update(String sql) {
			boolean re=false;
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbname, userName,
						password);

				Statement st = conn.createStatement();
				st.executeUpdate(sql);
				
				conn.close();
				st.close();
				re=true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			return re;
		}
		public Vector selectTK(String sql) {
			Vector list=new Vector<>();
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbname, userName,
						password);
				
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					list.add(new TaiKhoan(rs.getInt("ID"), rs.getString("TaiKhoan"), rs.getString("MatKhau"), rs.getString("TenChuTK"), rs.getInt("SoDu"), rs.getInt("SDT"), rs.getString("DiaChi")));
				}

				conn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			return list;
		}
		public Vector GetBDSD(String sql) {
			Vector list=new Vector<>();
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbname, userName,
						password);
				
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					list.add(new BienDongSD(rs.getInt("IDTK"), rs.getInt("IDTK_TD"), rs.getString("ThoiGian"), rs.getString("Type"), rs.getInt("STBienDong")));
				}

				conn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			
			return list;
		}
//	public static void main(String[] args) {
//		ServerDao serverDao=new  ServerDao();
//		Vector list= serverDao.selectTK("Select * from TaiKhoan");
//		System.out.println(list.size());
//	}
}
