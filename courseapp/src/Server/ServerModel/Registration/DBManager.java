package Server.ServerModel.Registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBManager{
	static String URL;
	private String user;
	private String password;

	public DBManager(){
			URL = "jdbc:mysql://localhost:3306/mydb";
			user = "root";
			password = "root";
	}

	public String createConnection(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myCon = DriverManager.getConnection(URL, user, password);
			Statement myState = myCon.createStatement();
			String sql = "insert into student" + "(name, id)" + "values ('herm', '8')";
			myState.executeUpdate(sql);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return (" sucess #");
	}

	//public static void main(String [] args) {
	//	DBManager db = new DBManager();
	//	System.out.println(db.createConnection());
	//}
}

