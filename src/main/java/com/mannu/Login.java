package com.mannu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.mannu.model.User;
import com.mannu.utility.DataUtility;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField passtxt;
	DataUtility utility=new DataUtility();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);
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
		setUndecorated(true);
		setBounds(100, 100, 253, 129);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Etds Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(20, 30, 67, 14);
		contentPane.add(lblUserName);
		
		username = new JTextField();
		username.setBounds(97, 27, 133, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(20, 55, 67, 14);
		contentPane.add(lblPassword);
		
		passtxt = new JPasswordField();
		passtxt.setBounds(97, 52, 133, 20);
		contentPane.add(passtxt);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(username.getText().trim().equals("") || username.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Username");
					username.requestFocus();
				} else if (passtxt.getText().trim().equals("") || passtxt.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Password");
					passtxt.requestFocus();
				} else {
					User user=utility.getUser(username.getText().trim(),passtxt.getText().trim());
					if(user.getSta().equals("0")) {
						JOptionPane.showMessageDialog(null, "invalid username or password");
						username.setText("");
						passtxt.setText("");
						username.requestFocus();
					} else if (user.getSta().equals("1")){
						JOptionPane.showMessageDialog(null, "Deactivate Id");
						username.setText("");
						passtxt.setText("");
						username.requestFocus();
					} else {
						WorkPage wp=new WorkPage(user.getUsername(),user.getFullname(),user.getRole());
						wp.start();
						dispose();
					}
					
					
				}
				
			}
		});
		btnLogin.setBounds(30, 80, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnClose.setBounds(129, 80, 89, 23);
		contentPane.add(btnClose);
	}
}
