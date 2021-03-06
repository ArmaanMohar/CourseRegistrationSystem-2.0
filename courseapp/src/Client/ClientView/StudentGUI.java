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

public class StudentGUI extends GUI {
    private static final long serialVersionUID =12L;
    private int studentId;
    private String studentName;
    
    public StudentGUI(View v){
        theView = v;
        studentName="";
        studentId=-1;
        valid="";
        view = 1;
        tGUI();
    }

    @Override
    public void tGUI(){
        detailsEntered = false;

        jta = new JTextArea("Welcome!\n This is a course registration system (beta).\n Please start by entering your details..\n\nCRITICAL WARNING: You can not do anything without entering your details.");
        jta.setMargin(new Insets(3, 7, 3, 5));
        jta.setEditable(false);

        prepareGUI();
    }

    @Override
    public void prepareGUI(){
        setTitle("Course Registration System -------- V@STUDENT");
        setSize(600, 800);
        setLayout(new GridLayout(2, 1));
        JScrollPane jsp = new JScrollPane(jta);
        add(jsp);
        add(addButtons());

        setVisible(true);
    }

    @Override
    public JPanel addButtons(){
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp,1));

        JButton search = new JButton("Search for courses in db");
        JButton addCourseToMyCourseList = new JButton("Add course to my courses");
        JButton removeCourseFromMyCourseList = new JButton("Remove course from my courses");
        JButton viewAll = new JButton("View all the courses in db");
        JButton viewMyCourses = new JButton("View all the courses taken by Student");
        JButton enterDetails = new JButton("LOG IN");
        JButton changePassword = new JButton("Change My Password");
        JButton quit = new JButton("Quit the application");



        // setting visibilities of buttons
        search.setVisible(detailsEntered);
        addCourseToMyCourseList.setVisible(detailsEntered);
        removeCourseFromMyCourseList.setVisible(detailsEntered);
        viewAll.setVisible(detailsEntered);
        viewMyCourses.setVisible(detailsEntered);
        enterDetails.setVisible(!detailsEntered);
        changePassword.setVisible(detailsEntered);
        quit.setVisible(detailsEntered);
        quit.setVisible(true);

        jp.add(new JLabel(" "));
        jp.add(enterDetails);

        // creating spaces between buttons using JLabel
        jp.add(search);
        jp.add(new JLabel(" "));
        jp.add(addCourseToMyCourseList);
        jp.add(new JLabel(" "));
        jp.add(removeCourseFromMyCourseList);
        jp.add(new JLabel(" "));
        jp.add(viewAll);
        jp.add(new JLabel(" "));
        jp.add(viewMyCourses);
        jp.add(new JLabel(" "));
        jp.add(changePassword);
        jp.add(new JLabel(" "));
        jp.add(quit);

        enterDetails.addActionListener((ActionEvent e) ->{
            JFrame log = new JFrame();
            JPanel logIn = new JPanel();
            log.setSize(350,250);
            log.add(logIn);
            logIn.setLayout(null);


            JLabel name = new JLabel("user name");
            name.setBounds(10,20,80,25);
            logIn.add(name);
            JTextField N = new JTextField(20);
            N.setBounds(100,20,165,25);
            logIn.add(N);

            JLabel userID = new JLabel("user ID");
            userID.setBounds(10,50,80,25); //x,y,width,height
            logIn.add(userID);
            JTextField ID = new JTextField(20);
            ID.setBounds(100, 50, 165, 25);
            logIn.add(ID);

            JLabel pw = new JLabel("Enter password:");
            pw.setBounds(10,80, 100,25);
            logIn.add(pw);
            JTextField password = new JTextField(20);
            password.setBounds(100,80,165, 25);
            logIn.add(password); 
            
            JButton submit = new JButton("SUBMIT");
            submit.setBounds(10,110, 100, 25);
            logIn.add(submit);
        
            
            log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            log.setTitle("Please login here");
            log.setSize(500, 300);
            log.setVisible(true);
          

            submit.addActionListener((ActionEvent s) -> {
                if((N.getText().isEmpty() || ID.getText().isEmpty() || password.getText().isEmpty())){
                    JOptionPane.showMessageDialog(this,"Invalid INPUT", "Invalid INPUT", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.studentId = Integer.parseInt(ID.getText()); 
                this.studentName= N.getText();   
                String valid = validatePassword(studentId , password.getText(), studentName, view);
                if(valid.equals("# -1")){
                    JOptionPane.showMessageDialog(this,"Invalid Login", "Invalid Login", JOptionPane.ERROR_MESSAGE);
                    return;
                } else{
                    detailsEntered = true;
                    guiSerOutput("# Welcome "+ this.studentName);
                    search.setVisible(detailsEntered);
                    addCourseToMyCourseList.setVisible(detailsEntered);
                    removeCourseFromMyCourseList.setVisible(detailsEntered);
                    viewAll.setVisible(detailsEntered);
                    viewMyCourses.setVisible(detailsEntered);
                    enterDetails.setVisible(!detailsEntered);
                    changePassword.setVisible(detailsEntered);
                    quit.setVisible(true);
                }     
                log.dispose();
            });          
        });

        search.addActionListener((ActionEvent e) -> {
            guiSerOutput(searchCourse());
        });
        addCourseToMyCourseList.addActionListener((ActionEvent e) -> {
            guiSerOutput(addTheCourse());
        });
        removeCourseFromMyCourseList.addActionListener((ActionEvent e) -> {
            guiSerOutput(removeCourse());
        });
        viewAll.addActionListener((ActionEvent e) -> {
            guiSerOutput(viewAllCourses());
        });
        viewMyCourses.addActionListener((ActionEvent e) -> {
            guiSerOutput(studentCourses());
        });
        changePassword.addActionListener((ActionEvent e) ->{
            guiSerOutput(changePass());
        });
        quit.addActionListener((ActionEvent e) -> {
            quit();
        });

        return jp;
    }

    
    public String changePass(){
        String pass = newPassword();
        return theView.getAction().changePassW(studentId, pass);
    }


    @Override
    public String addTheCourse(){
        cName = callInputForName();
        callInputForSection();
        return theView.getAction().addCourseToStudent(this.cName, this.studentId, this.cSec);
    }

    @Override
    public String removeCourse(){
        cName = callInputForName();
        return theView.getAction().removeCourseFromStu(cName, this.studentId);
    }

    
    /**
     * Returns student courses to the buttons, student courses are received from
     * server through clientCommunication
     * 
     * @return studentCourses String
     */
    @Override
    public String studentCourses() {
        return theView.getAction().getMyCourseList(this.studentId);
    }
}
