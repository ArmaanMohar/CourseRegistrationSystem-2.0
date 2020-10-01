package Server.ServerModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    static int numOfReg;
    
    public Model(ServerThread t){
        myThread = t;
        search = null;
        myRs = null;
        URL = "jdbc:mysql://localhost:3306/mydb";
		user = "root";
        password = "root";
        numOfReg = 0;
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
            search = myCon.prepareStatement("SELECT privileges, userID, username FROM mydb.student UNION SELECT privileges, userID, username FROM mydb.admin");
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
            if( authority == 1){
                search = myCon.prepareStatement("INSERT INTO mydb.student VALUES((?),(?),(?),(?))");
                search.setString(2, name);
                search.setInt(1, id);
                search.setInt(4, authority);
                search.setString(3, pass);
                search.executeUpdate();
                result += "# Successfully added user #";
            } else if(authority == 0){
            search = myCon.prepareStatement("INSERT INTO mydb.admin VALUES((?),(?),(?),(?))");
            search.setInt(4, authority);
            search.setInt(1, id);
            search.setString(2, name);
            search.setString(3, pass);
            search.executeUpdate();
            result += "# Successfully added user #";
            } else{
                result += " # Could not add User #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String validateUser(int ID, String pw, String n, int v){
        String result = "# ";
        try {
            search = myCon.prepareStatement("SELECT s.userName, s.privileges FROM mydb.student s WHERE s.userPassword=(?) and s.userID=(?) and s.userName=(?) UNION SELECT a.userName, a.privileges FROM mydb.admin a WHERE a.userPassword=(?) and a.userID=(?) and userName=(?)");
            search.setString(1, pw);
            search.setInt(2, ID);
            search.setString(3, n);
            search.setString(4, pw);
            search.setInt(5, ID);
            search.setString(6, n);
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

    /**
     * add course to specifed student
     * @param courseName
     * @param studentID
     * @param courseSection
     * @return
     */
    public String addCourseStudent(String courseName, int studentID, int courseSection){
        String result = "";
        int courseID = -1;
        int csec = -1;
        try {
            search = myCon.prepareStatement("SELECT cID, secNum FROM mydb.course WHERE cName=(?)");
            search.setString(1, courseName);
            myRs = search.executeQuery();
            if(myRs.next() == false){
                result += " # error adding course #";
            } else{
                do{
                        courseID = myRs.getInt("cID");
                        csec = myRs.getInt("secNum");
                
                    if(csec <= courseSection || csec >=0){
                        search = myCon.prepareStatement("INSERT INTO mydb.registration VALUES((?),(?),(?),(?))");
                        search.setInt(1, studentID);
                        search.setInt(2, courseID);
                        search.setInt(3, courseSection);
                        numOfReg++;
                        search.setInt(4, numOfReg);
                        search.executeUpdate();
                        result += "# successfully added course #";
                        }
                } while(myRs.next());
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception s){
            s.printStackTrace();
        }
        return result;
    }


    /**
     * gets the course schedule of specified student
     * @param stID
     * @return
     */
    public String getCourseList(int stID){
        String line = "#Here's "+stID+" course schedule";
        /*ArrayList<Integer> coID = new ArrayList<>(); //course ID's student is registered in
        try {
            search = myCon.prepareStatement("SELECT DISTINCT courseID FROM mydb.registraion WHERE stuID=(?)");
            search.setInt(1, stID);
            ResultSet list = search.executeQuery();
            while(list.next()){
                coID.add(list.getInt("courseID"));
            }


            //for each courseID, append course info to line
            for(int i= 0; i < coID.size(); i++){
                search = myCon.prepareStatement("SELECT")
            }
            */
            try{
            search = myCon.prepareStatement("SELECT r.stuID, r.cID, r.secNum, c.cName FROM mydb.registration r,mydb.course c WHERE r.cID=c.cID");
            //search.setInt(1, stID);
            myRs = search.executeQuery();
            while(myRs.next()){
                String courseName = myRs.getString("cName");
                int section = myRs.getInt("secNum");
                line += "# # CourseName: " + courseName + " SectionNumber: "+ section + "# #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return line;
    }

    public String removeFromStudentList(String name, int sID){
        String result = "#trying to remove#";
        int courseID;
        try {
            search = myCon.prepareStatement("SELECT cID FROM mydb.course WHERE cName=(?)");
            search.setString(1, name);
            ResultSet temp = search.executeQuery();
            if(temp.next() == false){
                result += "# #course does not exist in database # #";
            } else{
                do {
                    courseID = temp.getInt("cID");
                } while (temp.next());
                search = myCon.prepareStatement("DELETE FROM mydb.registration WHERE stuID=(?) and cID=(?)");
                search.setInt(1, sID);
                search.setInt(2, courseID);
                search.executeUpdate();
                result += "# #deleted successfully # #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

   

    /**
     * searches entire database for specifed course
     * @param name
     * @param id
     * @return
     */
    public String searchCourse(String name, int id){
        String result = "#Heres what I found #";
        try {
            search = myCon.prepareStatement("SELECT * FROM mydb.course WHERE cName=(?) and cID=(?)");
            search.setString(1, name.toLowerCase());
            search.setInt(2, id);
            myRs = search.executeQuery();
            if(myRs.next() == false){
                result = "# # No such Course exists # #";
            } else{
            while(myRs.next()){
                String n = myRs.getString("cName");
                int i = myRs.getInt("cID");
                result += "# # Coursename: " + n + " CourseID: " + i +"# #";
            }
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
                String cname = myRs.getString("cName");
                int id = myRs.getInt("cID");
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
        String result = "#removing course from db...#";
        try {
            search = myCon.prepareStatement("DELETE FROM mydb.course WHERE cName=(?) and cID=(?)");
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
