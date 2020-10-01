package Client.ClientView;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public abstract class GUI extends JFrame {
    static final long serialVerisionUID = 2L;
    JTextArea jta;
    String cName;
    int cID;
    int cSec;
    boolean detailsEntered;
    View theView;
    String valid;
    int cap;
    int view;

    /**
     * sets jframe of app
     */
    public abstract void tGUI();

    /**
     * more setup
     */
    public abstract void prepareGUI();

    /**
     * adds buttons to app
     */
    abstract JPanel addButtons();

    /**
     * Password validation
     * @param id
     * @param pw
     * @param name
     * @param view
     * @return
     */
    public String validatePassword(int id, String pw, String name, int view){
        return theView.getAction().checkPW(id, pw, name, view);
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

    /**
     * Quit the app
     */
    public void quit(){
        int res = JOptionPane.showConfirmDialog(null, "Press OK to quit.", "Quit?", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) { // exit only if ok is pressed
            theView.getAction().closeCon();
            System.exit(0);
        } else if (res == JOptionPane.CANCEL_OPTION) { // otherwise back to program
            return;
        }

        return;
    }

    /**
     * Returns allCourses to the buttons, allCourses are received from server
     * through clientCommunication
     * 
     * @return allCourses String
     */
    public String viewAllCourses(){
        return theView.getAction().viewAllCourses();
    }

    /**
     * if student then removes course from schedule if admin then removes course from DB
     * @return
     */
    abstract String removeCourse();

    /**
     * if student then adds course to schedule if admin then adds course to DB
     * @return
     */
    abstract String addTheCourse();

    /**
     * searches for specifed course name
     * @return
     */
    public String searchCourse(){
        callForInput(false);
        return theView.getAction().searchCourse(cName);
    }

    /**
     * if student then shows current schedule if admin then shows schedule of specified student
     * @return
     */
    //abstract String studentCourses();

    /**
     * Helps other functions to get input for course name and id
     */
    public void callForInput(boolean act) {
        this.cName = callInputForName();
        System.out.println(cName);
        if (act == true) {
            this.cID = callInputForID();
            callInputForSection();
            callInputForCap();
        }
    }

    /**
     * course cap
     */
    public void callInputForCap() {
        int sec = 1;
        try {
            sec = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Course Cap Number: "));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid section entered, Please try again", "Error!",
                    JOptionPane.ERROR_MESSAGE);
                    callInputForCap();
        }
        this.cap = sec;
    }

    /**
     * course section
     * Creates input dialog and asks for input from user, sets the input as cSec
     */
    public void callInputForSection() {
        int sec = 1;
        try {
            sec = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Course Section Number(between 1 and 3): "));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid section entered, Please try again", "Error!",
                    JOptionPane.ERROR_MESSAGE);
                    callInputForSection();
        }
        this.cSec = sec;
    }

    /**
     * Course ID
     * Creates input dialog and asks for course id input from user
     * 
     * @return id integer id entered
     */
    public int callInputForID() {
        int id = -1;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Course ID: "));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID entered, Please enter only numeric value", "Error!",
                    JOptionPane.ERROR_MESSAGE);
                    callInputForID();
        }

        return id;
    }

    /**
     * Course Name
     * Creates input dialog and asks for input from user
     * 
     * @return name first word of string of name
     */
    public String callInputForName() {
        String name = "";
        do {
            name = JOptionPane.showInputDialog(null, "Enter Course Name: ");
            if(name.matches("-?\\d+") || name.isEmpty()){
                JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (name.isEmpty() || name.matches("-?\\d+"));

       /* name = JOptionPane.showInputDialog(null, "Enter Course Name: ");
        if(name.matches("-?\\d+") || name.isEmpty()){
            JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        */
    
        String[] mulWords = name.split(" ");
        if (mulWords[0].isEmpty()) {
            return mulWords[1]; // return second words if first words entered was space or empty
        }

        return mulWords[0]; // only accepting the first word entered as the name
    }

    /** logging in
     * @return User ID
     */
    public int callInputForUserID() {
        int id = -1;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter user ID: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid ID entered, Please enter only numeric value", "Error!",
                    JOptionPane.ERROR_MESSAGE);
                    callInputForUserID();
        }
        return id;
    }


    /**
     * logging in
     * @return user Name
     */
    public String callInputForUserName() {
        String name = "";
        do {
            name = JOptionPane.showInputDialog(null, "Enter user name");
            if(name.isEmpty()){
                JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            }
        } while (name.isEmpty());

        try {
            name = JOptionPane.showInputDialog(null, "Enter user name");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
                    callInputForUserName();
        }
        String[] mulWords = name.split(" ");
        if (mulWords[0].isEmpty()) {
            return mulWords[1]; // return second words if first words entered was space or empty
        }

        return mulWords[0]; // only accepting the first word entered as the name
    }

    /**
     * logging in
     * @return User password
     */
    public String callInputForUserPassword() {
        String name = "";
        try {
            name = JOptionPane.showInputDialog(null, "Enter user password");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
                    callInputForUserPassword();
        }
        return name;
    }
    
}
