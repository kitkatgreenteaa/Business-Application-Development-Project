package main;

import connect.Connect;
import javax.swing.*;

import Staff.*;
import Customer.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

public class LoginStaff extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel loginStaffTitle = new JLabel("Login as Staff");
	private JLabel emailLabel = new JLabel("Email : ");
	private JLabel passwordLabel = new JLabel("Password : ");
	private JTextField userEmailField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("Login");
	private JButton noAccButton = new JButton("I don't have an account");
	private JPanel sidePanel = new JPanel();

	UpdateMenu um;
	Connect con;
	ResultSet rs;

	LoginStaff() {
		con = new Connect();
		init();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		Style();
		action();
	}

	private void setLayoutManager() {
		container.setLayout(null);
	}

	private void setLocationAndSize() {
		loginStaffTitle.setBounds(30, 10, 150, 30);
		emailLabel.setBounds(70, 60, 250, 30);
		passwordLabel.setBounds(70, 95, 250, 30);
		userEmailField.setBounds(280, 60, 285, 30);
		passwordField.setBounds(280, 95, 285, 30);
		loginButton.setBounds(280, 180, 135, 40);
		noAccButton.setBounds(430, 180, 135, 40);
		sidePanel.setBounds(0, 0, 250, 300);
	}

	private void Style() {
		loginStaffTitle.setForeground(Color.BLACK);
		loginStaffTitle.setFont(new Font("Times New Roman",  Font.PLAIN, 13));
		loginStaffTitle.setHorizontalAlignment( SwingConstants.CENTER );

		container.setBackground(new Color(170,	150, 126));
		sidePanel.setBackground(new Color(223, 215, 204));

		emailLabel.setForeground(Color.BLACK);
		emailLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));


		loginButton.setBackground(new Color(223, 215, 204));
		loginButton.setForeground(Color.BLACK);
		loginButton.setFont(new Font("Times New Roman", Font.BOLD, 10));

		noAccButton.setBackground(new Color(223, 215, 204));
		noAccButton.setForeground(Color.BLACK);
		noAccButton.setFont(new Font("Times New Roman", Font.BOLD, 10));

	}

	private void addComponentsToContainer() {
		container.add(loginStaffTitle);
		container.add(emailLabel);
		container.add(passwordLabel);
		container.add(userEmailField);
		container.add(passwordField);
		container.add(loginButton);
		container.add(noAccButton);
		container.add(sidePanel);

	}

	private void action() {
		loginButton.addActionListener(this);
		noAccButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String userEmail;
			String userPass;
			userEmail = userEmailField.getText();
			userPass = String.valueOf(passwordField.getPassword());

			if (userEmail.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Please input email","Alert",JOptionPane.WARNING_MESSAGE); 
			} else if (userPass.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Please input password","Alert",JOptionPane.WARNING_MESSAGE); 
			} else  {
				try {
					boolean emailCorrect = false;
					boolean passCorrect = false;
					String queryEmail = "SELECT staff_email FROM staff";
					rs = con.executeQuery(queryEmail);
					while (rs.next()) {
						String email = rs.getString("staff_email");
						if (userEmail.equals(email)) {
							emailCorrect = true;
							break;
						}
					}
					if (emailCorrect) {
						String queryPassword = "SELECT staff_password FROM staff WHERE staff_email='%s'";
						queryPassword = String.format(queryPassword, userEmail);
						rs = con.executeQuery(queryPassword);
						rs.next();
						String password = rs.getString("staff_password");
						if (userPass.equals(password)) {
							passCorrect = true;
						}
					}
					if (emailCorrect && passCorrect) {
						String queryName = "SELECT staff_id, staff_name FROM staff WHERE staff_email='%s'";
						queryName = String.format(queryName, userEmail);
						rs = con.executeQuery(queryName);
						rs.next();
						String name = rs.getString("staff_name");
						int staffid = rs.getInt("staff_id");
						JOptionPane.showMessageDialog(this, "Welcome staff " + name + "!"); // tambahin +name sebelum !

						new UpdateMenu(staffid);
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Incorrect Email or Password", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}			
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			} 
		}

		if (e.getSource() == noAccButton) {
			new SignUpStaff();
			this.dispose();
		}
	}

	public void init() {
		setTitle("Cooking Papa");
		setVisible(true);
		setBounds(10, 10, 600, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);

	}



}

