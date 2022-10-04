package main;

import connect.Connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SignUpStaff extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel SignUpStaffTitle = new JLabel("Sign Up as Staff");
	private JLabel emailLabel = new JLabel("Email : ");
	private JLabel passwordLabel = new JLabel("Password : ");
	private JLabel userLabel = new JLabel("Name : ");
	private JTextField userEmailField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton registerButton = new JButton("Register");
	private JPanel sidePanel = new JPanel();
	private JTextField nameField = new JTextField();
	private JLabel phoneNumberLabel = new JLabel("Phone Number : "); 
	private JTextField phoneNumberField = new JTextField();

	Connect con;
	
	SignUpStaff() {
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
		SignUpStaffTitle.setBounds(30, 10, 150, 30);
		emailLabel.setBounds(70, 90, 250, 30);
		passwordLabel.setBounds(70, 125, 250, 30);
		phoneNumberLabel.setBounds(70, 160, 250, 30);
		userEmailField.setBounds(280, 95, 285, 20);
		passwordField.setBounds(280, 130, 285, 20);
		registerButton.setBounds(350, 200, 135, 40);
		nameField.setBounds(280, 60, 285, 20);
		userLabel.setBounds(70, 55, 250, 30);
		sidePanel.setBounds(0, 0, 250, 400);
		phoneNumberField.setBounds(280, 165, 285, 20);;

	}

	private void Style() {
		SignUpStaffTitle.setForeground(Color.BLACK);
		SignUpStaffTitle.setFont(new Font("Times New Roman",  Font.PLAIN, 13));
		SignUpStaffTitle.setHorizontalAlignment( SwingConstants.CENTER );

		container.setBackground(new Color(170,	150, 126));
		sidePanel.setBackground(new Color(223, 215, 204));

		emailLabel.setForeground(Color.BLACK);
		emailLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		phoneNumberLabel.setForeground(Color.BLACK);
		phoneNumberLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		userLabel.setForeground(Color.BLACK);
		userLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		registerButton.setBackground(new Color(223, 215, 204));
		registerButton.setForeground(Color.BLACK);
		registerButton.setFont(new Font("Times New Roman", Font.BOLD, 10));
	}
	
	public void cleanUpData( ) {
		nameField.setText("");
		userEmailField.setText("");
		passwordField.setText("");
		phoneNumberField.setText("");
	}

	private void addComponentsToContainer() {
		container.add(SignUpStaffTitle);
		container.add(emailLabel);
		container.add(passwordLabel);
		container.add(userEmailField);
		container.add(passwordField);
		container.add(registerButton);
		container.add(nameField);
		container.add(userLabel);
		container.add(phoneNumberLabel);
		container.add(phoneNumberField);
		container.add(sidePanel);

	}

	private void action() {
		registerButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			String userEmail;
			String userPass;
			String userName;
			String userPhone;

			userName = nameField.getText();
			userPhone = phoneNumberField.getText();
			userEmail = userEmailField.getText();
			userPass = passwordField.getText();


			if (userName.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Name can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			}
			if (userEmail.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Email can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			} // buat yg email gw blm bikin email is already registered, karena blm konekin dbnya jadi bingung
			if (userPass.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Password can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			}
			if (userPhone.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Phone Number can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			} else if (!userName.isEmpty() && !userEmail.isEmpty() && !userPass.isEmpty() && !userPhone.isEmpty()) {
				String query = String.format("INSERT INTO staff (staff_name, staff_email, staff_password, staff_phone_number) VALUES ('%s', '%s', '%s', '%s')", userName, userEmail, userPass, userPhone);

				boolean success = con.executeUpdate(query);
				if (success) {
					JOptionPane.showMessageDialog(this, "Registration Successful!");   
					new LoginStaff();
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Registration Failed!");   
					cleanUpData();
					return;
				}
			}
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

