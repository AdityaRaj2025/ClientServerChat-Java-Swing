

import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.DefaultCaret;
import java.io.*;
import java.net.Socket;

public class java_client extends JFrame implements ActionListener, KeyListener{
 
    // Extra variables
    static String message = "";
    static String userName = "";
    static String iPAddress = null;
 
    // Networking Variables
    static Socket socket = null;
    static PrintWriter writer = null;
 
    // // Graphics Variables
    static JTextArea msgRec = new JTextArea(100, 50);
    static JTextArea msgSend = new JTextArea(100, 50);
    JButton send = new JButton("Send");
    JScrollPane pane2, pane1;
    JLabel l1;

    public java_client() {
        super("Java Client");
      //
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
 //
        msgSend.setLineWrap(true);
        msgSend.setWrapStyleWord(true);
 
        msgSend.addKeyListener(this);
 //
        pane1 = new JScrollPane(msgSend);
        pane1.setBounds(5, 210, 300, 30);
        pane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane1);
 //
        send.setBounds(315, 210, 80, 35);
        add(send);
        send.addActionListener(this);
        
        l1 = new JLabel("online");
        l1.setBounds(50,250,100,50);
        add(l1);

        if ((userName) != null) {
            setVisible(true);
        } else {
            System.exit(0);
        }
    }
 
    /**
     * @param args
     */
    public static void main(String iPAdress,int prt_no) throws Exception {
        // swing thread
        (new Thread(new Runnable() {
            public void run() {
                new java_client();
 
            }
 
        })).start();
 
        socket = new Socket(iPAddress,prt_no);
        msgRec.setText("Connected to server:");
 
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
                        cursorUpdate();
 
                        if (!reader.ready()) {
                            testFlag = true;
                        }
                    }
 
                } catch (IOException ee) {
                    try {
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
                socket.close();
            } catch (IOException eee) {
            }
        }
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
 
        if ((e.getKeyCode() == KeyEvent.VK_ENTER) && e.isShiftDown()) {
            msgSend.append("\n");
 
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage();
        }
 
        else if ((e.getKeyCode() == KeyEvent.VK_X) && e.isControlDown()) {
            System.exit(0);
        }
    }
 

    private void sendMessage() {
        writer.println(msgSend.getText());
 
        msgRec.append("\nMe: " + msgSend.getText());
        writer.flush();
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
}
 