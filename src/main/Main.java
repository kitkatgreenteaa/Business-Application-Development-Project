package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Main extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel MainTitle = new JLabel("COOKING PAPA");
	private JLabel orLabel = new JLabel("or");
	private JButton loginButton = new JButton("Login As");
	private JButton signUpButton = new JButton("Sign Up");
	private JButton staffButtonLI = new JButton ("Staff");
	private JButton custButtonLI = new JButton ("Customer");
	private JButton staffButtonSU = new JButton ("Staff");
	private JButton custButtonSU = new JButton ("Customer");

	public Main() {
		init();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		Style();
		action();
	}

	private void setLayoutManager() {
		container.setLayout(null);
		staffButtonLI.setVisible(false);
		custButtonLI.setVisible(false);
		staffButtonSU.setVisible(false);
		custButtonSU.setVisible(false);
	}

	private void setLocationAndSize() {
		MainTitle.setBounds(200, 60, 200, 30);
		orLabel.setBounds(290, 112, 65, 30);

		loginButton.setBounds(130, 110, 135, 40);
		staffButtonLI.setBounds(130, 155, 135, 40);
		custButtonLI.setBounds(130, 200, 135, 40);

		signUpButton.setBounds(330, 110, 135, 40);
		staffButtonSU.setBounds(330, 155, 135, 40);
		custButtonSU.setBounds(330, 200, 135, 40);
	}

	private void Style() {
		MainTitle.setForeground(Color.BLACK);
		MainTitle.setFont(new Font("Times New Roman",  Font.BOLD, 20));
		MainTitle.setHorizontalAlignment( SwingConstants.CENTER );

		container.setBackground(new Color(170,	150, 126));

		orLabel.setForeground(Color.BLACK);
		orLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		loginButton.setBackground(new Color(223, 215, 204));
		loginButton.setForeground(Color.BLACK);
		loginButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

		staffButtonLI.setBackground(new Color(223, 215, 204));
		staffButtonLI.setForeground(Color.BLACK);
		staffButtonLI.setFont(new Font("Times New Roman", Font.BOLD, 15));

		custButtonLI.setBackground(new Color(223, 215, 204));
		custButtonLI.setForeground(Color.BLACK);
		custButtonLI.setFont(new Font("Times New Roman", Font.BOLD, 15));

		staffButtonSU.setBackground(new Color(223, 215, 204));
		staffButtonSU.setForeground(Color.BLACK);
		staffButtonSU.setFont(new Font("Times New Roman", Font.BOLD, 15));

		custButtonSU.setBackground(new Color(223, 215, 204));
		custButtonSU.setForeground(Color.BLACK);
		custButtonSU.setFont(new Font("Times New Roman", Font.BOLD, 15));

		signUpButton.setBackground(new Color(223, 215, 204));
		signUpButton.setForeground(Color.BLACK);
		signUpButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

	}

	private void addComponentsToContainer() {
		container.add(MainTitle);
		container.add(orLabel);
		container.add(loginButton);
		container.add(signUpButton);
		container.add(staffButtonLI);
		container.add(custButtonLI);
		container.add(staffButtonSU);
		container.add(custButtonSU);
	}

	private void action() {
		loginButton.addActionListener(this);
		signUpButton.addActionListener(this);
		staffButtonLI.addActionListener(this);
		custButtonLI.addActionListener(this);
		staffButtonSU.addActionListener(this);
		custButtonSU.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == loginButton) {
			staffButtonLI.setVisible(true);
			custButtonLI.setVisible(true);
			staffButtonSU.setVisible(false);
			custButtonSU.setVisible(false);
		} 

		if (e.getSource() == signUpButton) {
			staffButtonSU.setVisible(true);
			custButtonSU.setVisible(true);
			staffButtonLI.setVisible(false);
			custButtonLI.setVisible(false);
		}

		if (e.getSource() == staffButtonLI) {
			new LoginStaff();
			this.dispose();
		} else if (e.getSource() == custButtonLI){
			new LoginCustomer();
			this.dispose();
		} else if (e.getSource() == staffButtonSU) {
			new SignUpStaff();
			this.dispose();
		} else if (e.getSource() == custButtonSU) {
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
	
	public static void main(String[] args) {
		new Main();
	}
}

