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

    public abstract void tGUI();

    public abstract void prepareGUI();

    abstract JPanel addButtons();

    public String validatePassword(int id, String pw, String name){
        return theView.getAction().checkPW(id, pw, name);
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
            theView.getAction().closeCon();
            System.exit(0);
        } else if (res == JOptionPane.CANCEL_OPTION) { // otherwise back to program
            return;
        }

        return;
    }

    abstract String studentCourses();

    /**
     * Returns allCourses to the buttons, allCourses are received from server
     * through clientCommunication
     * 
     * @return allCourses String
     */
    public String viewAllCourses(){
        return theView.getAction().viewAllCourses();
    }

    abstract String removeCourse();

    abstract String addTheCourse();

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

    public int callInputForUserID() {
        int id = -1;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter your ID: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid ID entered, Please enter only numeric value", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        return id;
    }

    public String callInputForUserName() {
        String name = "";
        try {
            name = JOptionPane.showInputDialog(null, "Enter your name");
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

    public String callInputForUserPassword() {
        String name = "";
        try {
            name = JOptionPane.showInputDialog(null, "Enter your password");
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
