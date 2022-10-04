package main;

import connect.Connect;
import javax.swing.*;

import Customer.MenuForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

public class LoginCustomer extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel loginCustTitle = new JLabel("Login as Customer");
	private JLabel emailLabel = new JLabel("Email : ");
	private JLabel passwordLabel = new JLabel("Password : ");
	private JTextField userEmailField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("Login");
	private JButton noAccButton = new JButton("I don't have an account");
	private JPanel sidePanel = new JPanel();

	Connect con;
	ResultSet rs;
	
	LoginCustomer() {
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
		loginCustTitle.setBounds(30, 10, 150, 30);
		emailLabel.setBounds(70, 60, 250, 30);
		passwordLabel.setBounds(70, 95, 250, 30);
		userEmailField.setBounds(280, 60, 285, 30);
		passwordField.setBounds(280, 95, 285, 30);
		loginButton.setBounds(280, 180, 135, 40);
		noAccButton.setBounds(430, 180, 135, 40);
		sidePanel.setBounds(0, 0, 250, 300);
	}

	private void Style() {
		loginCustTitle.setForeground(Color.BLACK);
		loginCustTitle.setFont(new Font("Times New Roman",  Font.PLAIN, 13));
		loginCustTitle.setHorizontalAlignment( SwingConstants.CENTER );

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
		container.add(loginCustTitle);
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
			}

			if (userPass.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Please input password","Alert",JOptionPane.WARNING_MESSAGE); 
				
			} else {
				try {
					boolean emailCorrect = false;
					boolean passCorrect = false;
					String queryEmail = "SELECT customer_email FROM customer";
					rs = con.executeQuery(queryEmail);
					while (rs.next()) {
						String email = rs.getString("customer_email");
						if (userEmail.equals(email)) {
							emailCorrect = true;
							break;
						}
					}
					if (emailCorrect) {
						String queryPassword = "SELECT customer_password FROM customer WHERE customer_email='%s'";
						queryPassword = String.format(queryPassword, userEmail);
						rs = con.executeQuery(queryPassword);
						rs.next();
						String password = rs.getString("customer_password");
						if (userPass.equals(password)) {
							passCorrect = true;
						}
					}
					if (emailCorrect && passCorrect) {
						String queryName = "SELECT customer_id, customer_name FROM customer WHERE customer_email='%s'";
						queryName = String.format(queryName, userEmail);
						rs = con.executeQuery(queryName);
						rs.next();
						String name = rs.getString("customer_name");
						int customerid = rs.getInt("customer_id");
						JOptionPane.showMessageDialog(this, "Welcome " + name + "!"); // tambahin +name sebelum !

						new MenuForm(customerid);
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Incorrect Email or Password", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}			
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else if (e.getSource() == noAccButton) {
			new SignUpCust();
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