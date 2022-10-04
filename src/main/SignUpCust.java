package main;

import connect.Connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SignUpCust extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel SignUpCustTitle = new JLabel("Sign Up as Customer");
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
	private JLabel addressLabel = new JLabel ("Address : ");
	private JTextArea addressField = new JTextArea ();

	Connect con;
	
	SignUpCust() {
		con = new Connect();
		init();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		action();
		Style();
	}

	private void setLayoutManager() {
		container.setLayout(null);
	}

	private void setLocationAndSize() {
		SignUpCustTitle.setBounds(30, 10, 150, 30);
		emailLabel.setBounds(64, 153, 250, 30);
		passwordLabel.setBounds(64, 188, 250, 30);
		phoneNumberLabel.setBounds(64, 223, 250, 30);
		userEmailField.setBounds(274, 158, 285, 20);
		passwordField.setBounds(274, 193, 285, 20);
		registerButton.setBounds(350, 300, 150, 40);
		nameField.setBounds(274, 87, 285, 20);
		userLabel.setBounds(64, 82, 250, 30);
		sidePanel.setBounds(0, 0, 250, 400);
		phoneNumberField.setBounds(274, 228, 285, 20);
		addressLabel.setBounds(64, 118, 250, 30);
		addressField.setBounds(274, 123 , 285, 20);

	}

	private void Style() {
		SignUpCustTitle.setForeground(Color.BLACK);
		SignUpCustTitle.setFont(new Font("Times New Roman",  Font.PLAIN, 13));
		SignUpCustTitle.setHorizontalAlignment( SwingConstants.CENTER );

		container.setBackground(new Color(170,	150, 126));
		sidePanel.setBackground(new Color(223, 215, 204));

		emailLabel.setForeground(Color.BLACK);
		emailLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		phoneNumberLabel.setForeground(Color.BLACK);
		phoneNumberLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		addressLabel.setForeground(Color.BLACK);
		addressLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

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
		addressField.setText("");
		phoneNumberField.setText("");
	}
	

	private void addComponentsToContainer() {
		container.add(SignUpCustTitle);
		container.add(emailLabel);
		container.add(passwordLabel);
		container.add(userEmailField);
		container.add(passwordField);
		container.add(registerButton);
		container.add(nameField);
		container.add(userLabel);
		container.add(phoneNumberLabel);
		container.add(phoneNumberField);
		container.add(addressLabel);
		container.add(addressField);
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
			String userAddress;
			String userDob;

			userName = nameField.getText();
			userPhone = phoneNumberField.getText();
			userEmail = userEmailField.getText();
			userPass = passwordField.getText();
			userAddress = addressField.getText();

			if (userName.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Name can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			}
			if (userAddress.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Address can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			}
			if (userEmail.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Email can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			} // buat yg email gw blm bikin email is already registered, karena blm konekin dbnya jadi bingung
			if (userPass.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Password can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			}
			if (userPhone.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Phone Number can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
			} else if (!userName.isEmpty() && !userAddress.isEmpty() && !userEmail.isEmpty() && !userPass.isEmpty() && !userPhone.isEmpty()) {
				String query = String.format("INSERT INTO customer (customer_name, customer_email, customer_password, customer_address, customer_phone_number) VALUES ('%s', '%s', '%s', '%s', '%s')", userName, userEmail, userPass, userAddress, userPhone);
				
				boolean success = con.executeUpdate(query);
				if (success) {
					JOptionPane.showMessageDialog(this, "Registration Successful!");   
					new LoginCustomer();
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
		setBounds(10, 10, 600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);
	}
}