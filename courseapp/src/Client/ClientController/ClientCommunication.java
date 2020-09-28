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
    public String populate(){
        String line = "12 0 0 0 0";
        return communicate(line);
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
		line += studentName + " " + studentId + " 0";

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
		line += name + " " + id + " 0";
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
		line += name + " " + id + " " + sec +  " " + cap + " 0";
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
		line += courseName + " " + courseId + " 0";
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
		line += adminName + " " + adminId + " 0";
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
	public String searchCourse(String name, int id) {
		String line = "1 ";
		line += name + " 0" + id + " 0";

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
	public String addCourse(String name, int id, int sec) {
		String line = "2 ";
		line += name + " " + id + " " + sec;

		return communicate(line);
	}

	/**
	 * removes any course from the student, returns server response
	 * 
	 * @param name course name
	 * @param id   course id
	 * @return string of server response
	 */
	public String removeCourse(String name, int id) {
		String line = "3 ";
		line += name + " " + id + " 0";

		return communicate(line);
	}

	/**
	 * shows all the courses by invoking server functions and returning the response
	 * to ClientGUI
	 * 
	 * @return server response string
	 */
	public String viewAllCourses() {
		return communicate("4 allCourses 0 0");
	}

	/**
	 * shows all the student's courses by invoking server functions and returning
	 * the response to ClientGUI
	 * 
	 * @return server response string
	 */
	public String showStudentCourses() {
		return communicate("5 stuCourses 0 0");
	}

	/**
	 * Closes the connection to server and turns off the server
	 */
	public void closeCon() {
		communicate("11 closeCon 0 0");
		try {
			socketIn.close();
			socketOut.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println("Server connection aborted!!");
	}

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
