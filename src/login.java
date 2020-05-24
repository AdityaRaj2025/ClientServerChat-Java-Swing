
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class swing11 implements ActionListener{
	JFrame jf;
	JLabel l1,l2,l3,l4;
	JTextField t1,t2;
	JButton b1,b2;
	
	swing11(){
		
		jf =new JFrame("Sample Swing");
		jf.setVisible(true);
		jf.setSize(400,400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.setLayout(null);
		
		l1=new JLabel("WELCOME TO NARAYAN CHAT");
		jf.add(l1);
		
		l2=new JLabel("Email:");
		jf.add(l2);
		
		t1=new JTextField();
		jf.add(t1);
		
		l3=new JLabel("Password:");
		jf.add(l3);
		
		t2=new JTextField();
		jf.add(t2);
		
		b1=new JButton("LogIn");
		jf.add(b1);
		
		b2=new JButton("SinUp");
		jf.add(b2);
		
		l4=new JLabel();
		jf.add(l4);
		
		l1.setBounds(80,20,200,20);
		l2.setBounds(50,80,100,20);		
		t1.setBounds(150,80,200,20);
		l3.setBounds(50,130,100,20);
		t2.setBounds(150,130,200,20);
		b1.setBounds(100,180,100,20);
		b2.setBounds(100,225,100,20);
		l4.setBounds(50,240,100,20);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
	}	
	
	public void actionPerformed(ActionEvent e){
		String s = e.getActionCommand();
		if(s.equals("LogIn")){
		try {
						
	    	Class.forName("com.mysql.cj.jdbc.Driver");//class to connection mySQL
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dbserver","root","password");
	    	System.out.println("connected to database:");
	    	// For connection....
	    	Statement stm = con.createStatement();
	    	
	    	//select data from DataBase
	    	String sql = "select * from details where email='"+t1.getText()+"' and passwork='"+t2.getText()+"';";
	    	ResultSet rs = stm.executeQuery(sql);
	    	
	    	
	     	String email = t1.getText();
	    	if(rs.next()){
	    		JOptionPane.showMessageDialog(null, "connection sucessful");
	    		
	    		System.out.println(email);
	    		friend.main(email);
	    		
	    	}
	    	else {
	    		JOptionPane.showInternalMessageDialog(null, "Check Email and Password");
	    	}

	    	con.close();
		    }catch(Exception e1) {
		    	
		    	System.out.println("error="+e1);
		    }
			}
			
			else if(s.equals("SinUp")){
				sinup.main(null);
				jf.dispose();
			}
		
		
	}
		
	
}
	

 class login{
	public static void main(String[] args){
		new swing11();	
 }
}