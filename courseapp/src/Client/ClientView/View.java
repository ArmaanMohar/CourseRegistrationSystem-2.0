package Client.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import Client.ClientController.ClientCommunication;

/**
 * do testing with just client view
 */

public class View extends JFrame{
    private static final long serialVersionUID = 1L;
    private ClientCommunication action;
    private ClientGUI cg;
    private AdminGUI ag;
   
    public View(ClientCommunication ccm){
        super("Welcome to Course Registration System");
        setSize(550,450);
        action = ccm;
        add(welcome());
    }

    public JPanel welcome(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(5,3));
        JButton admin = new JButton("Admin");
        JButton user = new JButton("Student");

        for(int i =0; i<2; i++){
        p.add(new JLabel(" "));
        }
        p.add(admin);
        for(int j =0; j<3; j++){
            p.add(new JLabel(" "));
        }
        p.add(user);
        for(int i =0; i<3; i++){
            p.add(new JLabel(" "));
        }

        setVisible(true);

        admin.addActionListener((ActionEvent e) ->{
            String a = "admin";
            runView(a);
        });

        user.addActionListener((ActionEvent e) ->{
            String s = "student";
            runView(s);
        });
        return p;
    }

    public void runView(String t){
        if(t.equals("admin")){
            this.ag = new AdminGUI(this);
        }
        if(t.equals("student")){
            this.cg = new ClientGUI(this);
        }
    }

    public ClientCommunication getAction(){
        return action;
    }
}
