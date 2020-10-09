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
    private StudentGUI sg;
   
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
        JButton student = new JButton("Student");
        JButton client = new JButton("Client");

        for(int i =0; i<1; i++){
        p.add(new JLabel(" "));
        }
        p.add(admin);
        for(int j =0; j<1; j++){
            p.add(new JLabel(" "));
        }
        p.add(student);
        for(int i =0; i<1; i++){
            p.add(new JLabel(" "));
        }
        p.add(client);

        setVisible(true);

        admin.addActionListener((ActionEvent e) ->{
            String a = "admin";
            runView(a);
        });

        student.addActionListener((ActionEvent e) ->{
            String s = "student";
            runView(s);
        });
        client.addActionListener((ActionEvent e) ->{
            String c = "client";
            runView(c);
        });
        return p;
    }

    public void runView(String t){
        if(t.equals("admin")){
            this.ag = new AdminGUI(this);
        }
        if(t.equals("client")){
            this.cg = new ClientGUI(this);
        }
        if(t.equals("student")){
            this.sg = new StudentGUI(this);
        }
    }

    public ClientCommunication getAction(){
        return action;
    }
}
