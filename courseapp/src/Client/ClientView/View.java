package Client.ClientView;

import java.awt.BorderLayout;
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
import Client.ClientController.ClientCommunication;

/**
 * do testing with just client view
 */

public class View extends JFrame{
    private JTextArea jta;
    private String cName;
    private int cID;
    private int cSec;
    private int cap;
    private String studentName;
    private int studentId;
    private boolean detailsEntered;
    private String valid;
    private ClientCommunication action;
    
    public View(ClientCommunication ccm){
        action = ccm;
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
        setTitle("Course Registration System");
        setSize(600, 800);
        setLayout(new GridLayout(2, 1));
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
        JButton enterDetails = new JButton("Initialize Model");
        JButton quit = new JButton("Quit the application");



        // setting visibilities of buttons
        search.setVisible(true);
        addCourse.setVisible(true);
        remove.setVisible(true);
        viewAll.setVisible(true);
        viewStuCourses.setVisible(true);
        enterDetails.setVisible(true);
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
        jp.add(quit);

       // enterDetails.addActionListener((ActionEvent e) ->{
         //   guiSerOutput(populatedb());
        //});

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
            guiSerOutput(studentCourses());
        });
        quit.addActionListener((ActionEvent e) -> {
            quit();
        });

        return jp;
    }

    /**
     * Creates another GUI and shows the Server output on it
     * 
     * @param serverOutput String containing server output
     */
    public void guiSerOutput(String serverOutput) {
        jta.setText(""); // resetting text area
        if (serverOutput == null) { // checking if null response from server
            jta.setText("Error in your input, Server didn't respond!");
            return;
        }

        // showing server output to text area
        String[] outputs = serverOutput.split("#"); // splitting different lines by #
        for (String o : outputs) { // showing output
            jta.append(o);
            jta.append("\n");
        }
    }

    public void quit(){
        int res = JOptionPane.showConfirmDialog(null, "Press OK to quit.", "Quit?", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) { // exit only if ok is pressed
            action.closeCon();
            System.exit(0);
        } else if (res == JOptionPane.CANCEL_OPTION) { // otherwise back to program
            return;
        }

        return;
    }
    
    /**
     * Returns student courses to the buttons, student courses are received from
     * server through clientCommunication
     * 
     * @return studentCourses String
     */
    public String studentCourses() {
        return action.showStudentCourses();
    }

    /**
     * Returns allCourses to the buttons, allCourses are received from server
     * through clientCommunication
     * 
     * @return allCourses String
     */
    public String viewAllCourses() {
        return action.viewAllCourses();
    }

    /**
     * Invokes remove course in server
     * 
     * @return confirmation String confirming the status of removal
     */
    public String removeCourse() {
        callForInput(false);
        return action.removeCourse(cName, cID);
    }

    /**
     * Invokes the server to add the course
     * 
     * @return String confirmation of addition of course
     */
    public String addTheCourse() {
        callForInput(true); // true prompts for input of section number
        return action.addCourse(cName, cID, cSec, cap);
    }


    public String searchCourse(){
        callForInput(false);
        return action.searchCourse(cName, cID);
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
