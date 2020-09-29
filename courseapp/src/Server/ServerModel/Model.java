package Server.ServerModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Server.ServerController.ServerThread;
import Server.ServerModel.Registration.*;

public class Model {
    private ServerThread myThread;
    private Statement myState;
    private PreparedStatement search;
    private ResultSet myRs;
    static String URL;
	private String user;
    private String password;
    private Connection myCon;
    
    public Model(ServerThread t){
        myThread = t;
        search = null;
        myRs = null;
        URL = "jdbc:mysql://localhost:3306/mydb";
		user = "root";
        password = "root";
        connecttoDB();
    }

    public void connecttoDB(){
        try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			myCon = DriverManager.getConnection(URL, user, password);
            //Statement myState = myCon.createStatement();
            System.out.println("Client connected to db...");
			//String sql = "insert into student" + "(name, id)" + "values ('bart', '1')";
            //myState.executeUpdate(sql);
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
    }

    public String searchCourse(String name, int id){
        String result = "";
        try {
            //String sql = "select * from mydb.student where name=? and id=?";
            search = myCon.prepareStatement("select * from mydb.course where namel=? and idCourse=?");
            search.setString(1, name.toLowerCase());
            search.setInt(2, id);
            myRs = search.executeQuery();
            while(myRs.next()){
                String n = myRs.getString("namel");
                int i = myRs.getInt("idCourse");
                result += "#Heres what I found #" + "# # Coursename: " + n + " CourseID: " + i +"# #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;  
    }

    /**
     * returns info of all courses found in database
     */
    public String viewAllCourses(){
        String result = "";
        try {
            String sql = "select * from mydb.course";
            search = myCon.prepareStatement(sql);
            myRs = search.executeQuery();
            while(myRs.next()){
                String cname = myRs.getString("namel");
                int id = myRs.getInt("idCourse");
                int num = myRs.getInt("secNum");
                int cap = myRs.getInt("secCap");
                result += "#Heres what I found #" +" # # CourseName: "+cname+" CourseID: "+id+" SectionNumber: "+num+" Cap: "+cap+"# #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*public String removeCourse(String name, int id){

    }

    public String addCourse(String name, int id, int secNum){

    }
    */

    public static void main(String [] args){

    }

        
    
}
