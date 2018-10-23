package mysql;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel pContentPane;
	private JTextField txUsername;
	private JPasswordField pfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pContentPane = new JPanel();
		pContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pContentPane);
		pContentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login Page ");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		lblLogin.setBounds(10, 11, 414, 31);
		pContentPane.add(lblLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 61, 71, 14);
		pContentPane.add(lblUsername);
		
		txUsername = new JTextField();
		txUsername.setBounds(10, 82, 120, 20);
		pContentPane.add(txUsername);
		txUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 126, 71, 14);
		pContentPane.add(lblPassword);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(10, 151, 120, 20);
		pContentPane.add(pfPassword);
		
		JButton btLogin = new JButton("Login");
		btLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root",""); 
					Statement stmt=con.createStatement();
					String sql="Select * from tblogin where UserName='"+txUsername.getText()+"' and Password='"+pfPassword.getText().toString()+"'";
					ResultSet rs=stmt.executeQuery(sql);
					if(rs.next()) {
						String user = rs.getString("Username");
						JOptionPane.showMessageDialog(null, user + " has been connected");
					}
					else
						JOptionPane.showMessageDialog(null, "Incorrent username and Password...");
					con.close();
				}catch(Exception e1) {System.out.print(e1);}
			}
		});
		btLogin.setBounds(10, 200, 89, 23);
		pContentPane.add(btLogin);
		
		JButton btSignup = new JButton("Sign_up");
		btSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				Sign_up nw = new Sign_up(); 
				nw.NewScreen();
			}
		});
		btSignup.setBounds(191, 200, 89, 23);
		pContentPane.add(btSignup);
	}
}
