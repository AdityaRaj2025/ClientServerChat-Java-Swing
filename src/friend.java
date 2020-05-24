import javax.swing.*; 
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.ActionListener;

class table implements ActionListener{
	JFrame jf;
	JButton b1;
	JTextField t1;
	JLabel l1 ;
	JScrollPane sp;
	JTable jt;
	table(String email_login) throws  SQLException, ClassNotFoundException{
		Object[][] nam = new Object[30][3];
		int  i = 0;
		int port = 4245;
		Class.forName("com.mysql.cj.jdbc.Driver");//class to connection mySQL
    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dbserver","root","password");
    	System.out.println("connected to database:");
    	// For connection....
    	Statement stm = con.createStatement();
    	
    	String count_query = "select count(*) from details where name <>'"+email_login+"';";
    	ResultSet rs1 = stm.executeQuery(count_query);
    	int cnt = 0;
    	while(rs1.next()) {
    		cnt = rs1.getInt(1);
    	}
    	System.out.println(cnt);
		System.out.println("email rec:"+email_login);


    	//select data from DataBase
    	String sql = "select * from details;";
    	ResultSet rs2 = stm.executeQuery(sql);
    	
    	while(rs2.next()) {
    		String name = rs2.getString(1);
    		nam[i][0] = name;
    		nam[i][1] = port++;
    		nam[i][2] = "127.0.0.1";
    		i++;
    		System.out.println(name);
    		
    	}		
		jf=new JFrame("Table Demo");
		
		
		String [] col={"NAME","port","IP Adress"}; 

		jt=new JTable(nam,col); 
		sp=new JScrollPane(jt);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		jf.add(sp);
		
		l1 = new JLabel("enter port No to whome you want chat");
		jf.add(l1);
		
		t1 = new JTextField("**********");
		t1.setSize(100,40);
		jf.add(t1);
		
		b1 = new JButton("Enter");
		jf.add(b1);
		
		jf.setVisible(true);
		jf.setSize(500,500); jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//		jf.setLayout(null);
		jf.setLayout(new FlowLayout());	
		
		b1.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e1){
		String str = t1.getText();
		int prt_no = Integer.parseInt(str);
		System.out.println(prt_no);
		try {
			multiRun(prt_no);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	    public static void multiRun(int prt_no1) throws InterruptedException {
	    	int prt_no = prt_no1;
	    	new Thread() {
	        public void run() {
				try {
					java_server.main(prt_no);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		}
	    	}.start();
	    	Thread.sleep(1000);
	    	Thread.currentThread().interrupt();

	     
	    	new Thread() {
	        	public void run() {
	    			try {
						java_client.main("127.0.0.1",prt_no);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    	}.start();
	    	Thread.currentThread().interrupt();
	    }
}
public class friend {

	public static void main(String email_login) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		new table(email_login);
	}

}
