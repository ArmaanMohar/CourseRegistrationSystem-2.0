package Server.ServerModel;

import Server.ServerModel.Registration.*;

public class Model {
    private DBManager myDB;
    private String con;
    
    public Model(){
        myDB = new DBManager();
        con = myDB.createConnection();
    }

    

    public String searchCourse(String courseName, int courseId){
        return ("im return what u typed: " + courseName);
    }

    public String worked(String c){
        return c;

    }

    public String getS(){
        return con;
    }
    
}
