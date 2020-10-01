package Server.ServerModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

    /**
     * Connects model to MySQL DB
     */
    public void connecttoDB(){
        try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			myCon = DriverManager.getConnection(URL, user, password);
            System.out.println("Client connected to db...");
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
    }

    public String changePassword(String newPass, int userId){
        String result = "";
        int success;
        int priv = -1;
        try {
            search = myCon.prepareStatement("SELECT privileges FROM mydb.student WHERE userID=(?) UNION SELECT privileges FROM mydb.admin WHERE userID=(?)");
            search.setInt(1, userId);
            search.setInt(2, userId);
            myRs = search.executeQuery();
            if(myRs.next() == false){
                result += "# # User does not exist # #";
            } else{
                do {
                     priv = myRs.getInt("privileges");
                } while (myRs.next());
            }
            if(priv == 1){
                search = myCon.prepareStatement("UPDATE mydb.student SET userPassword=(?) WHERE userID=(?)");
            search.setString(1, newPass);
            search.setInt(2, userId);
            success = search.executeUpdate();
            if(success > 0){
                result += "# #successfully update password # #";                
            } else{
                result += "# # Error updating password # #";
            }
            } else if(priv == 0){
                search = myCon.prepareStatement("UPDATE mydb.admin SET userPassword=(?) WHERE userID=(?)");
                search.setString(1, newPass);
                search.setInt(2, userId);
                success = search.executeUpdate();
                if(success > 0){
                    result += "# #successfully update password # #";                
                } else{
                    result += "# # Error updating password # #";
                }
            } else if(priv == -1){
                result += "# # Critical Error # #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * View info of All Users in DB
     * @return
     */
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

    /**
     * Add new user to DB
     * @param name
     * @param id
     * @param authority
     * @param pass
     * @return
     */
    public String addUser(String name, int id, int authority, String pass){
        String result = "";
        int success=-1;
        try {
            if( authority == 1){
                search = myCon.prepareStatement("INSERT INTO mydb.student VALUES((?),(?),(?),(?))");
                search.setString(2, name);
                search.setInt(1, id);
                search.setInt(4, authority);
                search.setString(3, pass);
                success = search.executeUpdate();
                if(success > 0){
                result += "# Successfully added user #";
                } else{
                    result += "# #Error adding student user to db # #";
                }
            } else if(authority == 0){
            search = myCon.prepareStatement("INSERT INTO mydb.admin VALUES((?),(?),(?),(?))");
            search.setInt(4, authority);
            search.setInt(1, id);
            search.setString(2, name);
            search.setString(3, pass);
            success = search.executeUpdate();
            if(success>0){
            result += "# Successfully added user #";
            } else{
                result += "# # Error adding admin user to db # #";
            }
            } else{
                result += " # Could not add User #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return result += "# # ERROR ID must be unique # #";
        } 
        return result;
    }

    /**
     * Password Validation
     * @param ID
     * @param pw
     * @param n
     * @param v
     * @return
     */
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
        int success;
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
                        success = search.executeUpdate();
                        if(success>0){
                        result += "# successfully added course #";
                        } else{
                            result += "# # Error adding course to student # #";
                        }
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
        String line = "#Here's the course schedule";
        int courseid=-1;
        int secNum=-1;
            try{
                search = myCon.prepareStatement("SELECT cID, secNum FROM mydb.registration WHERE stuID=(?)");
                search.setInt(1, stID);
                myRs = search.executeQuery();
                if(myRs.next() == false){
                    line += "# #Student isn't registered in any course # #";
                    return line;
                } else{
                    do {
                        courseid = myRs.getInt("cID");
                        secNum = myRs.getInt("secNum");
                    } while (myRs.next());
                }

            search = myCon.prepareStatement("SELECT cName FROM mydb.course WHERE cID=(?)");
            search.setInt(1, courseid);
            myRs = search.executeQuery();
            if(myRs.next() == false){
                line += "# # course not found # #";
                return line;
            } else{
                do {
                    String courseName = myRs.getString("cName");
                    line += "# # CourseName: " + courseName + " SectionNumber: "+ secNum + "# #";
                } while (myRs.next());
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * remove course from student's schedule
     * @param name
     * @param sID
     * @return
     */
    public String removeFromStudentList(String name, int sID){
        String result = "#trying to remove#";
        int courseID;
        int success;
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
                success = search.executeUpdate();
                if(success>0){
                result += "# #deleted successfully # #";
                } else if(success == 0){
                    result += "# # course "+name+" does not exist in student's schedule # #";
                } else {
                    result += "# # Error removing course from your schedule # #";
                }                
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
    public String searchCourse(String name){
        String result = "#Heres what I found #";
        try {
            search = myCon.prepareStatement("SELECT cName,cID, secNum FROM mydb.course WHERE cName=(?)");
            search.setString(1, name);
            myRs = search.executeQuery();
            if(myRs.next() == false){
                result = "# # No such Course exists # #";
            } else{
                do{
                    String n = myRs.getString("cName");
                int i = myRs.getInt("cID");
                int sec = myRs.getInt("secNum");
                result += "# # Coursename: " + n + " CourseID: " + i + " Section Number: "+sec+"# #";
                }
            while(myRs.next());
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

    /**
     * remove course from DB
     * @param name
     * @param id
     * @return
     */
    public String removeCourse(String name){
        String result = "#removing course from db...#";
        int success;
        try {
            search = myCon.prepareStatement("DELETE FROM mydb.course WHERE cName=(?)");
            search.setString(1, name);
            success = search.executeUpdate();
            if(success >0){
            result += "# successfully deleted #";
            } else if(success == 0){
                result += "# # course "+name+" does not exist # #";
            } else {
                result += "# # Error removing course from db # #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * add course to DB
     * @param name
     * @param id
     * @param num
     * @param cap
     * @return
     */
    public String addCourse (String name, int id, int num, int cap){
        String result = "adding new course to db...";
        int success;
        try {
            search = myCon.prepareStatement("INSERT INTO mydb.course VALUES((?), (?), (?), (?))");
            search.setString(1, name);
            search.setInt(2, id);
            search.setInt(3, num);
            search.setInt(4, cap);
            success = search.executeUpdate();
            if(success >0){
            result += "#successfully added course to db #";
            } else{
                result += "# #Error adding course to db # #";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    


    public static void main(String [] args){

    }

        
    
}
