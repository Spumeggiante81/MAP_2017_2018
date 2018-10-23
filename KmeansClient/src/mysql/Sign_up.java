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

public class Sign_up extends JFrame {

	private JPanel pContentPane;
	private JTextField txFirstName;
	private JTextField txLastName;
	private JTextField txUsername;
	private JTextField txEmail;
	private JPasswordField pfPassword;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sign_up frame = new Sign_up();
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
	public Sign_up() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pContentPane = new JPanel();
		pContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pContentPane);
		pContentPane.setLayout(null);
		
		JLabel lblSignupPage = new JLabel("Sign_up Page");
		lblSignupPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignupPage.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		lblSignupPage.setBounds(10, 11, 414, 38);
		pContentPane.add(lblSignupPage);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(30, 60, 75, 14);
		pContentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(30, 97, 75, 14);
		pContentPane.add(lblLastName);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(30, 133, 75, 14);
		pContentPane.add(lblUsername);
		
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(30, 168, 75, 14);
		pContentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 203, 46, 14);
		pContentPane.add(lblPassword);
		
		txFirstName = new JTextField();
		txFirstName.setBounds(115, 57, 157, 20);
		pContentPane.add(txFirstName);
		txFirstName.setColumns(10);
		
		txLastName = new JTextField();
		txLastName.setBounds(115, 94, 157, 20);
		pContentPane.add(txLastName);
		txLastName.setColumns(10);
		
		txUsername = new JTextField();
		txUsername.setBounds(115, 130, 157, 20);
		pContentPane.add(txUsername);
		txUsername.setColumns(10);
		
		txEmail = new JTextField();
		txEmail.setBounds(115, 165, 157, 20);
		pContentPane.add(txEmail);
		txEmail.setColumns(10);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(115, 200, 157, 20);
		pContentPane.add(pfPassword);
		
		JButton btSignup = new JButton("Sign_up");
		btSignup.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
			try {  
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
					Statement stmt=con.createStatement();
					String query = "insert into tblogin values ('"+txFirstName.getText()+"','"+txLastName.getText()+"','"+txUsername.getText()+"','"+txEmail.getText()+"','"+pfPassword.getText().toString()+"')"; 
					stmt.executeUpdate(query);
					
				}
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btSignup.setBounds(286, 227, 89, 23);
		pContentPane.add(btSignup);
	}

}
