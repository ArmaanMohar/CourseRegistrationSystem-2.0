package Client.ClientView;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class AdminGUI extends GUI {
    private static final long serialVersionUID =1L;
    private int adminId;
    private String adminName;
    private String pw;
    
    public AdminGUI(View v){
        theView = v;
        adminName="";
        adminId=-1;
        valid="";
        pw = "";
        view = 0;
        tGUI();
    }

    public void tGUI(){
        detailsEntered = false;

        jta = new JTextArea("Welcome!\n This is a course registration system (beta).\n Please start by entering your details..\n\nCRITICAL WARNING: You can not do anything without entering your details.");
        jta.setMargin(new Insets(3, 7, 3, 5));
        jta.setEditable(false);

        prepareGUI();
    }

    public void prepareGUI(){
        setTitle("Course Registration System -------- V@ADMIN");
        setSize(600, 800);
        setLayout(new GridLayout(2, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane jsp = new JScrollPane(jta);
        add(jsp);
        add(addButtons());

        setVisible(true);
    }

    public JPanel addButtons(){
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp,1));

        JButton search = new JButton("Search courses in db");
        JButton addCourse = new JButton("Add course to db");
        JButton remove = new JButton("Remove course from db");
        JButton viewAll = new JButton("View all the courses in db");
        JButton viewStuCourses = new JButton("View all the courses taken by Student");
        JButton enterDetails = new JButton("LOG IN");
        JButton addUser = new JButton("Add New User");
        JButton viewAllUsers = new JButton("View All Users");
        JButton quit = new JButton("Quit the application");



        // setting visibilities of buttons
        search.setVisible(detailsEntered);
        addCourse.setVisible(detailsEntered);
        remove.setVisible(detailsEntered);
        viewAll.setVisible(detailsEntered);
        viewStuCourses.setVisible(detailsEntered);
        enterDetails.setVisible(!detailsEntered);
        addUser.setVisible(detailsEntered);
        viewAllUsers.setVisible(detailsEntered);
        quit.setVisible(detailsEntered);
        quit.setVisible(true);

        jp.add(new JLabel(" "));
        jp.add(enterDetails);

        // creating spaces between buttons using JLabel
        jp.add(search);
        jp.add(new JLabel(" "));
        jp.add(addCourse);
        jp.add(new JLabel(" "));
        jp.add(remove);
        jp.add(new JLabel(" "));
        jp.add(viewAll);
        jp.add(new JLabel(" "));
        jp.add(viewStuCourses);
        jp.add(new JLabel(" "));
        jp.add(addUser);
        jp.add(new JLabel(" "));
        jp.add(viewAllUsers);
        jp.add(new JLabel(" "));
        jp.add(quit);

        enterDetails.addActionListener((ActionEvent e) ->{
            JFrame log = new JFrame();
            JPanel infoInput = new JPanel();
            log.setSize(350,250);
            log.add(infoInput);
            infoInput.setLayout(null);


            JLabel name = new JLabel("user name");
            name.setBounds(10,20,80,25);
            infoInput.add(name);
            JTextField N = new JTextField(20);
            N.setBounds(100,20,165,25);
            infoInput.add(N);

            JLabel userID = new JLabel("user ID");
            userID.setBounds(10,50,80,25); //x,y,width,height
            infoInput.add(userID);
            JTextField ID = new JTextField(20);
            ID.setBounds(100, 50, 165, 25);
            infoInput.add(ID);

            JLabel pw = new JLabel("Enter password:");
            pw.setBounds(10,80, 100,25);
            infoInput.add(pw);
            JTextField password = new JTextField(20);
            password.setBounds(100,80,165, 25);
            infoInput.add(password); 
            
            JButton submit = new JButton("SUBMIT");
            submit.setBounds(10,110, 100, 25);
            infoInput.add(submit);
        
            
            log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            log.setTitle("Please Login here");
            log.setSize(1000, 200);
            log.setVisible(true);
          

            submit.addActionListener((ActionEvent s) -> {
                if((N.getText().isEmpty() || ID.getText().isEmpty() || password.getText().isEmpty())){
                    JOptionPane.showMessageDialog(this,"Invalid INPUT", "Invalid INPUT", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.adminId = Integer.parseInt(ID.getText()); 
                this.adminName = N.getText();   
                String valid = validatePassword(adminId , password.getText(), adminName, view);
                if(valid.equals("# -1")){
                    JOptionPane.showMessageDialog(this,"Invalid CREDENTIALS", "Invalid CREDENTIALS", JOptionPane.ERROR_MESSAGE);
                    return;
                } else{
                    detailsEntered = true;
                    guiSerOutput("# Welcome "+ this.adminName);
                    search.setVisible(detailsEntered);
                    addCourse.setVisible(detailsEntered);
                    remove.setVisible(detailsEntered);
                    viewAll.setVisible(detailsEntered);
                    viewStuCourses.setVisible(detailsEntered);
                    enterDetails.setVisible(!detailsEntered);
                    addUser.setVisible(detailsEntered);
                    viewAllUsers.setVisible(detailsEntered);
                    quit.setVisible(true);
                }     
                log.dispose();
            });                      
        });

        search.addActionListener((ActionEvent e) -> {
            guiSerOutput(searchCourse());
        });
        addCourse.addActionListener((ActionEvent e) -> {
            guiSerOutput(addTheCourse());
        });
        remove.addActionListener((ActionEvent e) -> {
            guiSerOutput(removeCourse());
        });
        viewAll.addActionListener((ActionEvent e) -> {
            guiSerOutput(viewAllCourses());
        });
        viewStuCourses.addActionListener((ActionEvent e) -> {
            //guiSerOutput(viewThisstudentCourses());
        });
        addUser.addActionListener((ActionEvent e) ->{
            guiSerOutput(addNewUser());
        });
        viewAllUsers.addActionListener((ActionEvent e) ->{
            guiSerOutput(viewAllUsers());
        });
        quit.addActionListener((ActionEvent e) -> {
            quit();
        });

        return jp;
    }


    public String viewAllUsers(){
        return theView.getAction().viewUsers();
    }

    public String addNewUser(){
        String userN = callInputForUserName();
        int userId = callInputForUserID();
        int priv = callInputForAccessNumber();
        String pw = callInputForUserPassword();
        return theView.getAction().addNewUser(userN, userId, priv, pw);
    }


    public int callInputForAccessNumber(){
        int access = -1;
        while( access < 0){
        try {
            access = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter 0 for admin user or positive integer for student user: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid number entered, enter 0 or 1", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
        return access;
    }


    
    /**
     * Returns student courses to the buttons, student courses are received from
     * server through clientCommunication
     * 
     * @return studentCourses String
     */
    public String studentCourses() {
        return theView.getAction().showStudentCourses();
    }

    
    
    /**
     * Invokes remove course in server
     * 
     * @return confirmation String confirming the status of removal
     */
    public String removeCourse() {
        callForInput(false);
        return theView.getAction().removeCourse(cName, cID);
    }

    /**
     * Invokes the server to add the course
     * 
     * @return String confirmation of addition of course
     */
    public String addTheCourse() {
        callForInput(true); // true prompts for input of section number
        return theView.getAction().addCourse(cName, cID, cSec, cap);
    }


    public String searchCourse(){
        callForInput(false);
        return theView.getAction().searchCourse(cName, cID);
    }

    /**
     * Helps other functions to get input for course name and id
     */
    public void callForInput(boolean act) {
        this.cName = callInputForName();
        if (cName.isEmpty() || (cName.compareTo(" ") == 0) || cName == null) {
            JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            return; // don't ask for id input if name not entered correctly
        }
        this.cID = callInputForID();
        this.cName = this.cName.toUpperCase();
        if (act == true) {
            callInputForSection();
            callInputForCap();
        }
    }

    public void callInputForCap() {
        int sec = 1;
        try {
            sec = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Cap Number: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid section entered, Please try again", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        this.cap = sec;
    }

    /**
     * Creates input dialog and asks for input from user, sets the input as cSec
     */
    public void callInputForSection() {
        int sec = 1;
        try {
            sec = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Section Number: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid section entered, Please try again", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        this.cSec = sec;
    }

    /**
     * Creates input dialog and asks for input from user
     * 
     * @return id integer id entered
     */
    public int callInputForID() {
        int id = -1;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Course ID: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid ID entered, Please enter only numeric value", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        return id;
    }

    /**
     * Creates input dialog and asks for input from user
     * 
     * @return name first word of string of name
     */
    public String callInputForName() {
        String name = "";
        try {
            name = JOptionPane.showInputDialog(null, "Enter the name of the Course");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        String[] mulWords = name.split(" ");
        if (mulWords[0].isEmpty()) {
            return mulWords[1]; // return second words if first words entered was space or empty
        }

        return mulWords[0]; // only accepting the first word entered as the name
    }

    
    
}
