package Client.ClientController;

import Client.ClientView.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;


public class ClientCommunication {
    private Socket aSocket;
    private PrintWriter socketOut; //to server
    private BufferedReader socketIn; //from server

    public ClientCommunication(String serverName, int port){
        new View(this);

        try{
            aSocket = new Socket(serverName, port);  //change to your ip, this is ip of server
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.getStackTrace();
		}
    }
    /**
	 * Passes the student information to the server
	 * 
	 * @param studentName name of the student
	 * @param studentId   student id
	 * @return
	 */
	public String passStudentInfo(String studentName, int studentId) {
		String line = "6 ";
		line += studentName + " " + studentId + " 0" + " 0";

		return communicate(line);
	}

	/**
	 * Gets the classlist, name of students, registered in said class
	 * @param name course name
	 * @param id course id
	 * @return
	 */
	public String showClasslist(String name, int id){
		String line = "10 ";
		line += name + " " + id + " 0" + " 0" + " ";
		return communicate(line);
	}

	/**
	 * Adds a new Course to database as specified by admin user
	 * @param name course name
	 * @param id course id
	 * @param sec number of sections
	 * @param cap cap size of course
	 * @return
	 */
	public String addNewCourse(String name, int id, int sec, int cap){
		String line = "8 ";
		line += name + " " + id + " " + sec +  " " + cap + " 0"+ " ";
		return communicate(line);
	}

	/**
	 * Checks if the course specified can run currently
	 * @param courseName course name
	 * @param courseId course id
	 * @return
	 */
	public String checkifCourseCanRun(String courseName, int courseId){
		String line = "9 ";
		line += courseName + " " + courseId + " 0" + " " + " 0" ;
		return communicate(line);
	}

	/**
	 * Passes admin infomation to the server
	 * @param adminName name of admin
	 * @param adminId id of admin
	 * @return
	 */
	public String passAdminInfo(String adminName, int adminId){
		String line = "7 ";
		line += adminName + " " + adminId + " 0" + " "+ " 0";
		return communicate(line);
	}

	/**
	 * Searches the course in server database, returns server response to ClientGUI
	 * as string
	 * 
	 * @param name course name
	 * @param id   course id
	 * @return String of server response
	 */
	public String searchCourse(String name) {
		String line = "1 ";
		line += name + " 0" + " 0" + " 0" + " n";
		return communicate(line);
	}

	/**
	 * Adds new course to student in server, returns server response as string
	 * 
	 * @param name course name
	 * @param id   course id
	 * @param sec  course section
	 * @return string of server response
	 */
	public String addCourse(String name, int id, int sec, int cap) {
		String line = "2 ";
		line += name + " " + id + " " + sec + " " + cap + " 0";

		return communicate(line);
	}

	/**
	 * removes any course from the student, returns server response
	 * 
	 * @param name course name
	 * @param id   course id
	 * @return string of server response
	 */
	public String removeCourse(String name) {
		String line = "3 ";
		line += name + " 0" + " 0" + " 0" + " 0";

		return communicate(line);
	}

	/**
	 * shows all the courses by invoking server functions and returning the response
	 * to ClientGUI
	 * 
	 * @return server response string
	 */
	public String viewAllCourses() {
		return communicate("4 allCourses 0 0 0 0");
	}

	/**
	 * shows all the student's courses by invoking server functions and returning
	 * the response to ClientGUI
	 * 
	 * @return server response string
	 */
	public String showStudentCourses() {
		return communicate("5 stuCourses 0 0 0 0");
	}

	public String checkPW(int ID, String p, String n, int view){
		String line = "10 ";
		line += n + " " + ID + " " + view + " 0" + " "+ p;
		return communicate(line);
	}

	/**
	 * Closes the connection to server and turns off the server
	 */
	public void closeCon() {
		communicate("11 closeCon 0 0 0 0");
		try {
			socketIn.close();
			socketOut.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println("Server connection aborted!!");
	}

	/**
	 * Add new User to DB
	 * @param usern
	 * @param id
	 * @param pr
	 * @param p
	 * @return
	 */
	public String addNewUser(String usern, int id, int pr, String p){
		String line = "9 ";
		line += usern + " " + id + " " + pr + " 0 "  + p;
		return communicate(line);
	}

	/**
	 * View all Users in DB
	 * @return
	 */
	public String viewUsers(){
		return communicate("8 view 0 0 0 0");
	}

	/**
	 * Adds course to student schedule
	 * @param courseN
	 * @param stuID
	 * @param courseSec
	 * @return
	 */
	public String addCourseToStudent(String courseN, int stuID, int courseSec){
		String line = "7 ";
		line += courseN + " "+ stuID + " " + courseSec + " 0" + " n";
		return communicate(line);
	}

	/**
	 * get student schedule
	 * @param ID
	 * @return
	 */
	public String getMyCourseList(int ID){
		return communicate("6 list"+" "+ID+" 0"+" 0"+ " n");
	}

	/**
	 * remove course from student schedule
	 * @param name
	 * @param id
	 * @return
	 */
	public String removeCourseFromStu(String name, int id){
		return communicate("5 "+name+" "+id+" 0"+" 0"+ " n");
	}

	/*
	public String viewThisStudentCourse(int id){
		return communicate("7 list " + id + " 0" + " 0" + " n");
	}
	*/

	/**
	 * helps other functions to send the data to server
	 * 
	 * @param line string containing commands and argument to server
	 * @return server response
	 */
	public String communicate(String line) {
		String response = "";

		try {
			socketOut.println(line); // sending info string to server
			response = socketIn.readLine(); // receiving info string from server
		} catch (Exception e) {
			System.out.println("Error in sending/receiving data to/from server\nError name: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Runs the client side
	 * 
	 * @param args String argument
	 */
	public static void main(String[] args) {
		new ClientCommunication("localhost", 9898); // start new client
	}
}
