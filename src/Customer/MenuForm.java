package Customer;

import connect.*;
import main.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class MenuForm extends JFrame implements ActionListener, MouseListener{

	private JPanel mainPanel = new JPanel(new BorderLayout(5,10));
	private JPanel centerPanel = new JPanel(new GridLayout(1, 4, 100, 15));
	private JPanel southPanel = new JPanel(new GridLayout(4,2, 0, 5));
	private JPanel northPanel = new JPanel(new GridLayout(2,2));

	private JPanel eastPanel = new JPanel();
	private JPanel westPanel = new JPanel();

	private JLabel MenuFormTitle = new JLabel("Menu Form");
	private JLabel dimsumLabel = new JLabel("Dimsum Paradise");
	private JLabel mpTitle = new JLabel ("Metode Pembayaran", SwingConstants.CENTER);
	private JLabel qtyTitle= new JLabel ("Quantity", SwingConstants.CENTER);
	private JLabel space1 = new JLabel();
	private JLabel space2 = new JLabel();
	private JLabel space3 = new JLabel();
	private JLabel space4 = new JLabel();


	private JTable menu = new JTable();
	private JTable cart = new JTable();

	private JSpinner quantity = new JSpinner();

	private Vector metodePembayaran = new Vector <> ();
	private JComboBox mpComboBox = new JComboBox (metodePembayaran);

	private JButton addButton = new JButton ("Add To Cart");
	private JButton buyButton = new JButton ("Buy");
	private JButton updateButton = new JButton ("Update");
	private JButton removeButton = new JButton ("Remove From Cart");
	private JButton logOutButton = new JButton("LOG OUT");

	private JScrollPane scrollMenu = new JScrollPane(menu);
	private JScrollPane scrollCart = new JScrollPane(cart);

	private DefaultTableModel dtm1, dtm2;

	private int customerid, total;
	private String tempId, fName, fPrice, fQty, fPayment;
	private boolean menuSelected = false;
	private boolean cartSelected = false;

	Connect con;
	ResultSet rs;
	private final JPanel bottomPanel = new JPanel();

	public MenuForm(int customerid) {
		this.customerid = customerid;
		con = new Connect();
		refreshTable();
		mp();
		Style();
		actionListener();

		northPanel.add(MenuFormTitle);
		northPanel.add(space1);
		northPanel.add(dimsumLabel);
		northPanel.add(logOutButton);

		centerPanel.add(scrollMenu, BorderLayout.CENTER);
		centerPanel.add(scrollCart, BorderLayout.CENTER);

		southPanel.add(qtyTitle);
		southPanel.add(quantity);
		southPanel.add(mpTitle);
		southPanel.add(mpComboBox);
		southPanel.add(addButton);
		southPanel.add(removeButton);
		southPanel.add(updateButton);
		southPanel.add(buyButton);

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(eastPanel, BorderLayout.EAST);
		mainPanel.add(westPanel, BorderLayout.WEST);
		add(mainPanel);

		setTitle("Cooking Papa");
		setVisible(true);
		setSize(new Dimension(1200, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);

	}
	private void mp() {
		metodePembayaran.add("Cash");
		metodePembayaran.add("BCA");
		metodePembayaran.add("Gopay");
	}

	private void refreshTable( ) {
		Object [] columnNames1 = {"ID", "Menu Name", "Price"};

		dtm1 = new DefaultTableModel(columnNames1, 0);

		rs = con.executeQuery("SELECT * FROM food");

		try {
			while (rs.next()) {
				int foodId = rs.getInt("food_id");
				String foodName = rs.getString("food_name");
				String foodPrice = rs.getString("food_price");

				Vector<Object> rowData1 = new Vector<Object>();
				rowData1.add(foodId);
				rowData1.add(foodName);
				rowData1.add(foodPrice);

				dtm1.addRow(rowData1);
			}
			menu.setBackground(Color.WHITE);
			menu.setModel(dtm1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Object [] columnNames2 = {"ID", "Menu Name", "Price", "Quantity"};

		dtm2 = new DefaultTableModel(columnNames2, 0);

		cart.setBackground(Color.WHITE);
		cart.setModel(dtm2);

	}

	private void Style() {
		MenuFormTitle.setForeground(Color.BLACK);
		MenuFormTitle.setFont(new Font("Serif",  Font.BOLD, 12));
		MenuFormTitle.setHorizontalAlignment( SwingConstants.CENTER);

		mainPanel.setBackground(new Color(170,	150, 126));
		centerPanel.setBackground(new Color(170,150, 126));
		eastPanel.setBackground(new Color(170,	150, 126));
		westPanel.setBackground(new Color(170,	150, 126));
		northPanel.setBackground(new Color(223, 215, 204));
		southPanel.setBackground(new Color(170,	150, 126));

		dimsumLabel.setForeground(Color.BLACK);
		dimsumLabel.setFont(new Font("Serif", Font.BOLD, 30));
		dimsumLabel.setHorizontalAlignment( SwingConstants.CENTER);

		mpTitle.setForeground(Color.BLACK);
		mpTitle.setFont(new Font("Times New Roman",  Font.BOLD, 14));

		quantity.setForeground(Color.BLACK);
		quantity.setFont(new Font("Times New Roman",  Font.BOLD, 14));

		menu.setForeground(Color.BLACK);
		menu.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		cart.setForeground(Color.BLACK);
		cart.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		addButton.setBackground(new Color(223, 215, 204));
		addButton.setForeground(Color.BLACK);
		addButton.setBorder(new EtchedBorder(10));
		addButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

		removeButton.setBackground(new Color(223, 215, 204));
		removeButton.setForeground(Color.BLACK);
		removeButton.setBorder(new EtchedBorder(10));
		removeButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

		updateButton.setBackground(new Color(223, 215, 204));
		updateButton.setForeground(Color.BLACK);
		updateButton.setBorder(new EtchedBorder(10));
		updateButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

		buyButton.setBackground(new Color(223, 215, 204));
		buyButton.setForeground(Color.BLACK);
		buyButton.setBorder(new EtchedBorder(10));
		buyButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

		logOutButton.setBackground(new Color(223, 215, 204));
		logOutButton.setForeground(Color.BLACK);
		logOutButton.setBorder(new EtchedBorder(10));
		logOutButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
	}

	private void actionListener() {
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		updateButton.addActionListener(this);
		buyButton.addActionListener(this);
		menu.addMouseListener(this);
		cart.addMouseListener(this);
		logOutButton.addActionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == menu) {
			menuSelected = true;
			tempId = menu.getValueAt(menu.getSelectedRow(), 0).toString();
			fName = menu.getValueAt(menu.getSelectedRow(), 1).toString();
			fPrice = menu.getValueAt(menu.getSelectedRow(), 2).toString();
		} else if (e.getSource() == cart) {
			cartSelected = true;
			tempId = cart.getValueAt(cart.getSelectedRow(), 0).toString();
			fName = cart.getValueAt(cart.getSelectedRow(), 1).toString();
			fPrice = cart.getValueAt(cart.getSelectedRow(), 2).toString();
			fQty = cart.getValueAt(cart.getSelectedRow(), 2).toString();
			//			fPayment = cart.getValueAt(cart.getSelectedRow(), 4).toString();	
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() ==  addButton) {
			if (menuSelected == false) {
				JOptionPane.showMessageDialog(null, "Please select a row!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if (quantity.getValue().equals(0)) {
				JOptionPane.showMessageDialog(null, "Please input the quantity!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				int qty = (Integer) quantity.getValue();
				//				String paymentMethod = (String) mpComboBox.getSelectedItem();

				menu.getSelectionModel().clearSelection();
				menuSelected = false;

				int subTotal = Integer.parseInt(fPrice) * qty;
				total += subTotal;
				Object [] newData = {tempId, fName, fPrice, qty};
				dtm2.addRow(newData);
				JOptionPane.showMessageDialog(null, "Added to cart", "Success", JOptionPane.INFORMATION_MESSAGE);

			}
		} else if (e.getSource() == removeButton) {
			if (cartSelected == false) {
				JOptionPane.showMessageDialog(null, "Please select a row!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				if (cart.getSelectedRow() != -1) {
					total += Integer.parseInt(fPrice);
					dtm2.removeRow(cart.getSelectedRow());
					JOptionPane.showMessageDialog(null, "Remove from cart", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
				cartSelected = false;
			}
		} else if (e.getSource() == updateButton) {
			if (cartSelected == false) {
				JOptionPane.showMessageDialog(null, "Please select a row to update!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if (quantity.getValue().equals(0)) {
				JOptionPane.showMessageDialog(null, "Please input the quantity!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, "Update success", "Success", JOptionPane.INFORMATION_MESSAGE);
				cart.setValueAt(quantity.getValue(), cart.getSelectedRow(), 3);
			}
		} else if (e.getSource() == buyButton) {
			if (cart.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "Please select an item!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else if(mpComboBox.getSelectedItem() == null ) {
				JOptionPane.showMessageDialog(null, "Please select a Payment Method!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else {
				int totalPrice = 0, orderId;
				String paymentMethod = (String) mpComboBox.getSelectedItem();
				for ( int i = 0; i < cart.getRowCount(); i++) {
					int price = Integer.parseInt((String) cart.getValueAt(i, 2));
					int qtt = (int) cart.getValueAt(i, 3);
					totalPrice = totalPrice + (price * qtt);
				}

				//				String q2 = "SELECT ORDER_ID FROM ORDER_HEADER  "


				String q1 = String.format("INSERT INTO order_header(customer_id, total_price, payment_method) VALUES ('%d', '%d' , '"+ paymentMethod+"')", customerid, totalPrice);
				String getId = "SELECT order_id FROM order_header ORDER BY order_id DESC LIMIT 1";
				PreparedStatement ps1 = con.preparedStatement(q1);
				PreparedStatement psGetId = con.preparedStatement(getId);
				try {
					ps1.executeUpdate();

					ResultSet rs1 = psGetId.executeQuery();

					rs1.next();
					orderId = rs1.getInt("order_id");

					for(int i = 0; i < cart.getRowCount() ;i++) {
						int foodId = Integer.parseInt((String) cart.getValueAt(i, 0));
						int qty = (int) cart.getValueAt(i, 3);
						String q2 = String.format("INSERT INTO `order_details`(`order_id`, `food_id`, `qty`) VALUES (%d , %d , %d)", orderId, foodId, qty );
						PreparedStatement ps2 = con.preparedStatement(q2);
						ps2.executeUpdate();
					}
					JOptionPane.showMessageDialog(null, "Purchase success, thankyou for ordered!", "Success", JOptionPane.INFORMATION_MESSAGE);
					refreshTable();
					new History(customerid);
					this.dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == logOutButton) {
			new Main();
			this.dispose();
		}
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