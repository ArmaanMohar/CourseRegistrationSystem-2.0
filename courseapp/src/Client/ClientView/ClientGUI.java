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


public class ClientGUI extends GUI {
    private static final long serialVersionUID =42L;
    
    
    public ClientGUI(View v){
        theView = v;
        valid="";
        view = 1;
        tGUI();
    }

    public void tGUI(){
        detailsEntered = true;

        jta = new JTextArea("Welcome!\n This is a course registration system (beta).\n Please start by entering your details..\n\nCRITICAL WARNING: You can not do anything without entering your details.");
        jta.setMargin(new Insets(3, 7, 3, 5));
        jta.setEditable(false);

        prepareGUI();
    }

    public void prepareGUI(){
        setTitle("Course Registration System -------- V@CLIENT");
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

        JButton search = new JButton("Search for courses in db");
        JButton viewAll = new JButton("View all the courses in db");
        JButton quit = new JButton("Quit the application");



        // setting visibilities of buttons
        search.setVisible(detailsEntered);
        viewAll.setVisible(detailsEntered);
        quit.setVisible(detailsEntered);
        quit.setVisible(true);

        jp.add(new JLabel(" "));

        // creating spaces between buttons using JLabel
        jp.add(search);
        jp.add(new JLabel(" "));
        jp.add(viewAll);
        jp.add(new JLabel(" "));
        jp.add(quit);

        search.addActionListener((ActionEvent e) -> {
            guiSerOutput(searchCourse());
        });
      
        viewAll.addActionListener((ActionEvent e) -> {
            guiSerOutput(viewAllCourses());
        });
        quit.addActionListener((ActionEvent e) -> {
            quit();
        });

        return jp;
    }

    @Override
    String removeCourse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    String addTheCourse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    String studentCourses() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
