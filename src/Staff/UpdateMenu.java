package Staff;

import connect.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.Main;
import connect.Connect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UpdateMenu extends JFrame implements ActionListener, MouseListener{

	private Container container = getContentPane();
	private JLabel UpdateMenuTitle = new JLabel("Update Menu");
	private JLabel menuNameLabel = new JLabel("Menu Name : ");
	private JTextField menuNameField = new JTextField();
	private JLabel priceLabel = new JLabel("Price : "); 
	private JTextField priceField = new JTextField();
	private JPanel topPanel = new JPanel();
	private JButton logOutButton = new JButton("LOG OUT");
	private JButton addButton = new JButton("ADD");
	private JButton deleteButton = new JButton("DELETE");
	private JButton updateButton = new JButton("UPDATE");
	//	private Object [][] data = {{"Siomay", "25K"}, {"Hakau", "20K"}, {"Lumpia Udang", "25K"}};
	private JTable menu = new JTable();
	private JPanel sidePanel = new JPanel();
	private int staffid;
	private String tempId;
	private boolean dataSelected = false;

	Connect con;
	ResultSet rs;

	public UpdateMenu(int staffid) {
		this.staffid = staffid;
		con = new Connect();
		init();
		refreshTable();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		Style();
		action();
	}

	private void refreshTable( ) {
		Object [] columnNames = {"ID", "Menu Name", "Price"};

		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);

		rs = con.executeQuery("SELECT * FROM food");

		try {
			while (rs.next()) {
				int foodId = rs.getInt("food_id");
				String foodName = rs.getString("food_name");
				String foodPrice = rs.getString("food_price");

				Vector<Object> rowData = new Vector<Object>();
				rowData.add(foodId);
				rowData.add(foodName);
				rowData.add(foodPrice);

				dtm.addRow(rowData);
			}
			menu.setModel(dtm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setLayoutManager() {
		container.setLayout(null);
	}

	private void setLocationAndSize() {
		UpdateMenuTitle.setBounds(30, 10, 200, 30);
		menuNameLabel.setBounds(460, 102, 326, 30);
		priceLabel.setBounds(460, 152, 291, 30);
		menuNameField.setBounds(460, 132, 326, 20);
		addButton.setBounds(460, 217, 90, 40);
		deleteButton.setBounds(578,217, 90, 40);
		updateButton.setBounds(696, 217, 90, 40);
		topPanel.setBounds(0, 0, 796, 50);
		priceField.setBounds (460, 177, 326,20);
		menu.setBounds(5, 85, 215, 152);
		sidePanel.setBounds(0, 51, 450, 422);
		logOutButton.setBounds(686, 61, 100, 30);

	}

	private void Style() {
		UpdateMenuTitle.setForeground(new Color(223, 215, 204));
		UpdateMenuTitle.setFont(new Font("Serif",  Font.BOLD, 25));
		UpdateMenuTitle.setHorizontalAlignment( SwingConstants.CENTER );

		container.setBackground(new Color(170,	150, 126));
		topPanel.setBackground(new Color(130, 126, 119));
		sidePanel.setBackground(new Color(223, 215, 204));

		priceLabel.setForeground(Color.BLACK);
		priceLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		menuNameLabel.setForeground(Color.BLACK);
		menuNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		addButton.setBackground(new Color(223, 215, 204));
		addButton.setForeground(Color.BLACK);
		addButton.setFont(new Font("Times New Roman", Font.BOLD, 10));

		deleteButton.setBackground(new Color(223, 215, 204));
		deleteButton.setForeground(Color.BLACK);
		deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 10));

		updateButton.setBackground(new Color(223, 215, 204));
		updateButton.setForeground(Color.BLACK);
		updateButton.setFont(new Font("Times New Roman", Font.BOLD, 10));

		logOutButton.setBackground(new Color(223, 215, 204));
		logOutButton.setForeground(Color.BLACK);
		logOutButton.setFont(new Font("Times New Roman", Font.BOLD, 10));

		menu.setForeground(Color.BLACK);
		menu.setFont(new Font("Times New Roman", Font.PLAIN, 10));
	}

	private void addComponentsToContainer() {
		container.add(UpdateMenuTitle);
		container.add(menuNameLabel);
		container.add(menuNameField);
		container.add(priceLabel);
		container.add(priceField);
		container.add(addButton);
		container.add(deleteButton);
		container.add(updateButton);
		container.add(topPanel);
		container.add(sidePanel);
		container.add(logOutButton);

		JScrollPane scrollPane = new JScrollPane(menu);
		sidePanel.add(scrollPane);
		
		menu.addMouseListener(this);

	}

	private void action() {
		addButton.addActionListener(this);
		deleteButton.addActionListener(this);
		updateButton.addActionListener(this);
		logOutButton.addActionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == menu) {
			dataSelected = true;
			tempId = menu.getValueAt(menu.getSelectedRow(), 0).toString();
			String fName = menu.getValueAt(menu.getSelectedRow(), 1).toString();
			String fPrice = menu.getValueAt(menu.getSelectedRow(), 2).toString();

			menuNameField.setText(fName);
			priceField.setText(fPrice);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			String menu;
			String price;

			price = priceField.getText();
			menu = menuNameField.getText();

			int foodId = 0;
			String querycheck = "SELECT * FROM food";
			rs = con.executeQuery(querycheck);
			try {
				foodId = rs.getRow();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (menu.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Name can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			} else if (price.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Price can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			}  else {
				String query = String.format("INSERT INTO food VALUES('%s', '%s', '%s')", foodId, menu, price);

				boolean success = con.executeUpdate(query);

				if (success) {
					refreshTable();
					JOptionPane.showMessageDialog(this, "Menu Successfully Added!"); 
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Insert Failed", "Alert", JOptionPane.ERROR_MESSAGE);	
				}
			}
		}
		
		if (e.getSource() == deleteButton) {
			String menu;
			String price;

			price = priceField.getText();
			menu = menuNameField.getText();

			if (dataSelected == false) {
				JOptionPane.showMessageDialog(this,"Please select a row!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			} else if (menu.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Name can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			} else if (price.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Price can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			} else {
				String query = String.format("DELETE FROM food WHERE food_id = '%s'", tempId);

				boolean success = con.executeUpdate(query);

				if (success) {
					refreshTable();
					JOptionPane.showMessageDialog(this, "Menu Successfully Delete!"); 
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Delete Failed", "Alert", JOptionPane.ERROR_MESSAGE);	
					return;
				}
			}
		}
		if (e.getSource() == updateButton) {
			String menu;
			String price;

			price = priceField.getText();
			menu = menuNameField.getText();

			if (dataSelected == false) {
				JOptionPane.showMessageDialog(this,"Please select a row!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			} else if (menu.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Name can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			} else if (price.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Price can't be empty!","Alert",JOptionPane.WARNING_MESSAGE); 
				return;
			} else {
				String query = String.format("UPDATE food SET food_name = '%s', food_price = '%s' WHERE food_id = '%s'", menu, price, tempId);

				boolean success = con.executeUpdate(query);

				if (success) {
					refreshTable();
					JOptionPane.showMessageDialog(this, "Menu Successfully Update!"); 
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Update Failed", "Alert", JOptionPane.ERROR_MESSAGE);	
				}
			}
		}

		if (e.getSource() == logOutButton) {
			new Main();
			this.dispose();
		}
	}

	public void init() {
		setTitle("Cooking Papa");
		setVisible(true);
		setBounds(10, 10, 800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}