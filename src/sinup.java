import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class swing12 implements ActionListener{
	JFrame jf;
	JLabel l1,l2,l3,l4,l5,l6,l7;
	JTextField t1,t2,t3,t4,t5,t6;
	JButton b1,b2;
	
	swing12(){
		
		jf =new JFrame("SinUp");
		jf.setVisible(true);
		jf.setSize(500,500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.setLayout(null);
		
		l1=new JLabel("REGISTER  TO NARAYAN CHAT");
		jf.add(l1);
		
		l2=new JLabel("Name:");
		jf.add(l2);
		
		t1=new JTextField();
		jf.add(t1);
		
		l3=new JLabel("Phone No:");
		jf.add(l3);
		
		t2=new JTextField();
		jf.add(t2);
		
		l4=new JLabel("Email:");
		jf.add(l4);
		
		t3=new JTextField();
		jf.add(t3);
		
		l5=new JLabel("DOB:");
		jf.add(l5);
		
		t4=new JTextField();
		jf.add(t4);
		
		
		l6=new JLabel("Password:");
		jf.add(l6);
		
		t5=new JTextField();
		jf.add(t5);
		
		l7=new JLabel("Confirm Passwd:");
		jf.add(l7);
		
		t6=new JTextField();
		jf.add(t6);
		
		b1=new JButton("Submit");
		jf.add(b1);
		
		
		
		l1.setBounds(80,20,200,20);
		l2.setBounds(50,80,100,20);		
		t1.setBounds(150,80,200,20);
		l3.setBounds(50,130,100,20);
		t2.setBounds(150,130,200,20);
		l4.setBounds(50,180,100,20);		
		t3.setBounds(150,180,200,20);
		l5.setBounds(50,230,100,20);		
		t4.setBounds(150,230,200,20);
		l6.setBounds(50,280,100,20);		
		t5.setBounds(150,280,200,20);
		l7.setBounds(30,330,100,20);		
		t6.setBounds(150,330,200,20);
		b1.setBounds(100,380,100,20);
		
		
		b1.addActionListener(this);
	}	
	
	public void actionPerformed(ActionEvent e){
			    try {
			    	String name1 = t1.getText();
			    	String phnos1 = t2.getText();
			    	String email1 = t3.getText();
			    	String dob1 = t4.getText();
			    	String password1 = t5.getText();
			    	String confirm_password = t6.getText();
			    	if(password1.equals(confirm_password)) {
			    		System.out.println("name="+dob1);
				    	Class.forName("com.mysql.cj.jdbc.Driver");//class to connection mySQL
				    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dbserver","root","password");
				    	Statement stm = con.createStatement();
				    
				    	PreparedStatement p = null;
				    	p = con.prepareStatement("insert into details (name,phnos,email,dob,passwork) values(?,?,?,?,?)");
				    	p.setString(1,name1);
				    	p.setString(2,phnos1);
				    	p.setString(3,email1);
				    	p.setString(4,dob1);
				    	p.setString(5,password1);
				    	p.executeUpdate();
				    	JOptionPane.showMessageDialog(null, "Resgistered:");
					    friend.main(null);
					    con.close();
			    	}
			    	else {
			    		JOptionPane.showInternalMessageDialog(null, "Password not Matched");
			    		jf.dispose();
			    	}

			    }catch(Exception e1) {
			    	
			    	System.out.println("error="+e1);
			    }
			    
		}	    
}

 public class sinup{
	public static void main(String[] args){
		new swing12();	
 }
}