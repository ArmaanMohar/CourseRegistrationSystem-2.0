package Server.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import Server.ServerModel.*;

/**
 * Creates threads which enables server to be used by multiple clients
 * simultenously
 * 
 * @author Punit Patel
 * @author Armaan Mohat
 * @author Tom Pritchard
 * @since April 14, 2020
 * @version 2.0
 */
public class ServerThread extends Thread {

    /**
     * Socket where client connects
     */
    private Socket socket;

    /**
     * Input reader from socket
     */
    private BufferedReader socketIn;

    /**
     * Message sender to client
     */
    private PrintWriter socketOut;

    /**
     * boolean to track the status of server (running or not)
     */
    private boolean running;

    /**
     * Model class object instance
     */
    private Model model;

    /**
     * Instance of the server communication
     */
    private ServerCommunication serCom;
    static String URL;
	private String user;
    private String password;
    private Connection myCon;
    

    /**
     * Constructs a new serverThread for each different client
     * 
     * @param soc socket
     */
    public ServerThread(Socket soc, ServerCommunication serCom) {
        socket = soc;
        try {
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("Socket reading failed.");
        }
        this.model = new Model(this);
        running = true;
        this.serCom = serCom;
    }


    /**
     * Runs the thread
     */
    public void run() {
        socketOut.flush();
        String line = "";

        while (running) {
            try {
                line = socketIn.readLine();
            } catch (Exception e) {
                running = false;
            }

            if (line.isEmpty() || line == null) { // double checks for wrong input in server
                socketOut.println("Invalid request string.");
                break;
            }
            // String [] inputs = new String[5];
            String[] inputs = line.split(" "); // splits input string into different command and argument strings
            int choice = Integer.parseInt(inputs[0]); // commandString
            String name = inputs[1]; // args String passed into int
            int id = Integer.parseInt(inputs[2]); // args String
            int secNum = Integer.parseInt(inputs[3]); // args String passed into int
            int cap = Integer.parseInt(inputs[4]);
            String pw = inputs[5];

            switch (choice) {
                case 1:
                    String searchedCourse = model.searchCourse(name, id);
                    socketOut.println(searchedCourse);
                    socketOut.flush();
                    break;
                case 2:
                    String addedCourse = model.addCourse(name, id, secNum, cap);
                    socketOut.println(addedCourse);
                    socketOut.flush();
                    break;
                case 3:
                    String remove = model.removeCourse(name, id);
                    socketOut.println(remove);
                    socketOut.flush();
                    break;
                case 4:
                    String fullCatalogue = model.viewAllCourses();
                    socketOut.println(fullCatalogue);
                    socketOut.flush();
                    break;
                    /*
                case 5:
                    //String takenCourses = model.coursesTaken();
                   // socketOut.println(takenCourses);
                    break;
                case 6:
                    model = new Model(name, id);
                    socketOut.println("VALID #" + "Welcome! #\t" + name + " - " + id
                            + "# # #Now you can use the system.. # # Please select from the following choices.");
                    break;
                case 7:
                    model = new Model(name, id);
                    if (name.toUpperCase().equals("PAT") && id == 7) {
                        socketOut.println("VALID #" + "Welcome Admin! #\t" + name + " - " + id
                                + "# # #Now you can use the system.. # # Please select from the following choices.");
                    } else {
                        socketOut.println("Error! # Invalid Credentials! #");
                    }
                    break;
                case 8:
                    socketOut.println("Add Course Not Completed Yet!");
                    // String newCourse = model.addNewCourse(name, id, secNum, cap);
                    // socketOut.println(newCourse);
                    break;
                case 9:
                    //String runnable = model.runCourse(name, id);
                    //socketOut.println(runnable);
                    break;
                case 10:
                   // String list = model.classlist(name, id);
                    //socketOut.println(list);
                    break;
                    
                case 7:
                    String courseTakenbyStu = model.viewCourseListofStu(id);
                    socketOut.println(courseTakenbyStu);
                    socketOut.flush();
                    break;
                    */
                case 8:
                    String users = model.viewUsers();
                    socketOut.println(users);
                    socketOut.flush();
                    break;
                case 9:
                    String newUser = model.addUser(name, id, secNum, pw);
                    socketOut.println(newUser);
                    socketOut.flush();
                    break;
                case 10:
                    socketOut.flush();
                    String accessGranted = model.validateUser(id, pw, name, secNum);
                    socketOut.println(accessGranted);
                    socketOut.flush();
                    break;
                case 11:
                    socketOut.flush();
                    closeConnection();
                
                default:
                    socketOut.println("default");
                    closeConnection();
                    running = false;
                    break;
            }
        }
    }

    /**
     * Closes the connection of the serverthread with client
     */
    public void closeConnection() {
        try {
            socketIn.close();
            socketOut.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        System.out.println("\nClient Disconnected!!");
        serCom.clientDisconnect();
    }
}