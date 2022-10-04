package Customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import connect.Connect;

public class History extends JFrame{
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 50));	
	JPanel westPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	JPanel topPanel = new JPanel();
	
	JPanel botPanel = new JPanel(new GridLayout(2, 2, 30, 0));

	JTable header;
	JTable detail;
	DefaultTableModel dtmHeader;
	DefaultTableModel dtmDetail;
	JScrollPane scrollHeader;
	JScrollPane scrollDetail;

	TableRowSorter<TableModel> sortHeader;
	TableRowSorter<TableModel> sortDetail;

	JButton searchButton = new JButton("Search");
	JTextField searchField = new JTextField(15);

	JLabel title = new JLabel("History");
	JLabel x = new JLabel();
	JLabel y = new JLabel();
	Connect con;
	private int customerid;

	public History(int customerid) {
		this.customerid = customerid;
		con = new Connect();
		tableInit();
		init();
		style();
		addComponent();
		addListener();
		headerLoadData(con.executeQuery("SELECT * FROM order_header"));
	}

	private void init() {
		setTitle("Cooking Papa");
		setSize(1000, 550);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);
	}

	private void tableInit() {
		String headerColumn[] = {"ID", "Total Transaction", "Payment Method"};
		dtmHeader = new DefaultTableModel(null, headerColumn);
		sortHeader = new TableRowSorter<>(dtmHeader);
		header = new JTable(dtmHeader);
		header.setRowSorter(sortHeader);
		scrollHeader = new JScrollPane(header);

		String detailColumn[] = {"Item", "Price", "Quantity"};
		dtmDetail = new DefaultTableModel(null, detailColumn);
		sortDetail = new TableRowSorter<>(dtmDetail);
		detail = new JTable(dtmDetail);
		detail.setRowSorter(sortDetail);
		scrollDetail = new JScrollPane(detail);
	}

	public void search(String s)
	{
		if (s.length() == 0) {
			sortHeader.setRowFilter(null);
		} else {
			sortHeader.setRowFilter(RowFilter.regexFilter(s));
		}
	}

	private void addComponent() {
		topPanel.add(title);
		centerPanel.add(scrollHeader);
		centerPanel.add(scrollDetail);
		botPanel.add(x);
		botPanel.add(y);
	
		botPanel.add(searchField);
		botPanel.add(searchButton);

		mainPanel.add(eastPanel, BorderLayout.EAST);
		mainPanel.add(westPanel, BorderLayout.WEST);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(botPanel, BorderLayout.SOUTH);
		add(mainPanel);
	}

	public void headerLoadData(ResultSet rs) {
		String headerColumn[] = {"ID", "Total Transaction", "Payment Method"};
		dtmHeader.setDataVector(null, headerColumn);
		try {
			while(rs.next()) {
				int orderId = rs.getInt("order_id");
				String totalTransaction = rs.getString("total_price");
				String paymentMethod = rs.getString("payment_method");


				Vector<Object> rowData = new Vector<>();

				rowData.add(orderId);
				rowData.add(totalTransaction);
				rowData.add(paymentMethod);

				dtmHeader.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void style() {
		topPanel.setBackground(new Color(170, 150, 126));
		centerPanel.setBackground(new Color(170, 150, 126));
		westPanel.setBackground(new Color(170, 150, 126));
		eastPanel.setBackground(new Color(170, 150, 126));
		botPanel.setBackground(new Color(170, 150, 126));
		mainPanel.setBackground(new Color(170, 150, 126));

		title.setFont(new Font("Serif", Font.BOLD, 30));
	}

	public void detailLoadData(ResultSet rs) {
		String detailColumn[] = {"Item", "Price", "Quantity"};
		dtmDetail.setDataVector(null, detailColumn);
		try {
			while(rs.next()) {
				String item = rs.getString("food_name");
				int price = rs.getInt("food_price");
				int qty = rs.getInt("qty");

				Vector<Object> rowData = new Vector<>();

				rowData.add(item);
				rowData.add(price);
				rowData.add(qty);

				dtmDetail.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addListener() {
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search(searchField.getText());
			}
		});
		
		header.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				detailLoadData(con.executeQuery(String.format("SELECT f.food_name, f.food_price, od.qty "
						+ "FROM food f "
						+ "INNER JOIN order_details od "
						+ "ON f.food_id = od.food_id "
						+ "INNER JOIN order_header oh "
						+ "ON od.order_id = oh.order_id "
						+ "WHERE oh.order_id = %d", header.getValueAt(header.getSelectedRow(), 0))));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}


}
