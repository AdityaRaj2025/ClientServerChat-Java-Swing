

import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.DefaultCaret;
import java.io.*;


import java.awt.Color;

import java.net.ServerSocket;
import java.net.Socket;
 
public class java_server extends JFrame implements ActionListener, KeyListener{
 
    // Extra variables
    static String message = "";
    static String userName = "";
 
    // Networking Variables
    static ServerSocket server = null;
    static Socket socket = null;
    static PrintWriter writer = null;
 
    // // Graphics Variables
    static JTextArea msgRec = new JTextArea(100, 50);
    static JTextArea msgSend = new JTextArea(100, 50);
    JButton send = new JButton("Send");
    JScrollPane pane2, pane1;
    JLabel l1;

    public java_server(int prt_no) {
    	
        super("Java Server");
        setBounds(0, 0, 415, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
 
        msgRec.setEditable(false);
      
        msgRec.setText("");
 
        msgRec.setWrapStyleWord(true);
        msgRec.setLineWrap(true);
 
        pane2 = new JScrollPane(msgRec);
        pane2.setBounds(0, 0, 400, 200);
        pane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane2);
 
        msgSend.setLineWrap(true);
        msgSend.setWrapStyleWord(true);
 
        msgSend.addKeyListener(this);
 
        pane1 = new JScrollPane(msgSend);
        pane1.setBounds(5, 210, 300, 30);
        pane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane1);
 
        send.setBounds(315, 210, 80, 35);
        add(send);
        send.addActionListener(this);
        
        l1 = new JLabel("online");
        l1.setBounds(50,250,100,30);
        add(l1);
 
        if ((userName) != null) {
            setVisible(true);
        } else {
            System.exit(0);
        }
    }

    private void sendMessage() {
        writer.println(msgSend.getText());
        writer.flush();
 
        msgRec.append("\nMe: " + msgSend.getText());
 
        cursorUpdate();
 
        msgSend.setText("");
        msgSend.setCaretPosition(0);
    }
 
    private static void cursorUpdate() {
        // Update cursor position
        DefaultCaret caret = (DefaultCaret) msgRec.getCaret();
        caret.setDot(msgRec.getDocument().getLength());
 
        DefaultCaret caret2 = (DefaultCaret) msgSend.getCaret();
        caret2.setDot(msgSend.getDocument().getLength());
    }

// ActionEvents
@Override
public void actionPerformed(ActionEvent e) {
    Object scr = e.getSource();

    if (scr == send) {
        sendMessage();
    } 

}

// / KeyBoardEvents

@Override
public void keyTyped(KeyEvent e) {

}

@Override
public void keyReleased(KeyEvent e) {

}

@Override
public void keyPressed(KeyEvent e) {

    if ((e.getKeyCode() == KeyEvent.VK_ENTER)) {
        msgSend.append("\n");
    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        sendMessage();
    }

    else if ((e.getKeyCode() == KeyEvent.VK_X)) {
        System.exit(0);
    }
} 
    public static void main(int prt_no) throws Exception {
        (new Thread(new Runnable() {
            public void run() {
                new java_server(prt_no);
            }
 
        })).start();
 
        server = new ServerSocket(prt_no);
        System.out.println(server.getInetAddress().getLocalHost());
         
        socket = server.accept();
 
        msgRec.setText("Connected to client:");
        // listening port thread
        (new Thread(new Runnable() {
            public void run() {
 
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
 
                    String line = null;
                    boolean testFlag = true;
                    while ((line = reader.readLine()) != null) {
 
                        msgRec.append("\nfriend:" + line);
//                      msgRec.append("\nMe: " + msgSend.getText());

 
                        // Cursor Update
                        cursorUpdate();
                    }
 
                } catch (IOException ee) {
                    try {
                        server.close();
                        socket.close();
                    } catch (IOException eee) {
                        eee.printStackTrace();
                    }
                    ee.printStackTrace();
                }
 
            }
        })).start();
 
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
 
        } catch (IOException e) {
            try {
                server.close();
                socket.close();
            } catch (IOException eee) {
            }
        }
    }
}
  