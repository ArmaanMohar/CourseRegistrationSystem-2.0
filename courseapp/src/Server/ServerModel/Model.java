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
    static Connection myCon;
    
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
            System.out.println("Client connected to db...");
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
    }

    public String viewUsers(){
        String result = "#Here's the current users #";
        try {
            search = myCon.prepareStatement("SELECT privileges, userID, username FROM mydb.users");
            myRs = search.executeQuery();
            if(!myRs.isBeforeFirst()){
                result += "#No users in database #";
                return result;
            } else{
            while(myRs.next()){
                int auth = myRs.getInt("privileges");
                int ID = myRs.getInt("userID");
                String name = myRs.getString("userName");
                if(auth > 0){
                    result += "# # USERNAME: " + name + " USERID: " + ID + ", is a student # #";
                }
                if(auth == 0){
                    result += "# # USERNAME: " + name + " USERID: " + ID + ", is a admin # #";
                }
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String addUser(String name, int id, int authority, String pass){
        String result = "";
        try {
            if( authority > 0){
                String student = "INSERT INTO mydb.student VALUES((?),(?),(?))";
                search = myCon.prepareStatement(student);
                search.setString(1, name);
                search.setInt(2, id);
                search.setInt(3, id);
                search.executeUpdate();
            }
            search = myCon.prepareStatement("INSERT INTO mydb.users VALUES((?),(?),(?),(?))");
            search.setInt(1, authority);
            search.setInt(2, id);
            search.setString(3, name);
            search.setString(4, pass);
            search.executeUpdate();
            result += "# Successfully added user #";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String validateUser(int ID, String pw, String n, int v){
        String result = "# ";
        try {
            search = myCon.prepareStatement("SELECT userName, privileges FROM mydb.users WHERE userPassword=(?) and userID=(?) and userName=(?)");
            search.setString(1, pw);
            search.setInt(2, ID);
            search.setString(3, n);
            myRs = search.executeQuery();
            if(myRs.next() == false){
                result = "# -1";
            } else{
                do {
                    int p = myRs.getInt("privileges");
                    String userName = myRs.getString("userName");
                    if( p == v){
                        result += userName;
                    } else{
                result = "# -1";
                    }
                } while (myRs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    /*
    public String viewCourseListofStu(int ID){
        String result = "";
        try {
            search = myCon.prepareStatement("SELECT name, secNum FROM mydb.course WHERE")
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    */

   

    public String searchCourse(String name, int id){
        String result = "#Heres what I found #";
        try {
            search = myCon.prepareStatement("SELECT * FROM mydb.course WHERE name=(?) and idCourse=(?)");
            search.setString(1, name.toLowerCase());
            search.setInt(2, id);
            myRs = search.executeQuery();
            while(myRs.next()){
                String n = myRs.getString("name");
                int i = myRs.getInt("idCourse");
                result += "# # Coursename: " + n + " CourseID: " + i +"# #";
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
        String result = "#Heres what I found #";
        try {
            String sql = "select * from mydb.course";
            search = myCon.prepareStatement(sql);
            myRs = search.executeQuery();
            while(myRs.next()){
                String cname = myRs.getString("name");
                int id = myRs.getInt("idCourse");
                int num = myRs.getInt("secNum");
                int cap = myRs.getInt("secCap");
                result += " # # CourseName: "+cname+" CourseID: "+id+" SectionNumber: "+num+" Cap: "+cap+"# #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String removeCourse(String name, int id){
        String result = "#removing course...#";
        try {
            search = myCon.prepareStatement("DELETE FROM mydb.course WHERE name=(?) and idCourse=(?)");
            search.setString(1, name);
            search.setInt(2, id);
            search.executeUpdate();
            result += "# successfully deleted #";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String addCourse (String name, int id, int num, int cap){
        String result = "adding new course to db...";
        try {
            search = myCon.prepareStatement("INSERT INTO mydb.course VALUES((?), (?), (?), (?))");
            search.setString(1, name);
            search.setInt(2, id);
            search.setInt(3, num);
            search.setInt(4, cap);
            search.executeUpdate();
            result += "#successfully added course to db #";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    


    public static void main(String [] args){

    }

        
    
}
