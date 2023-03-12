package dbteste;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * Autor: Alexandre Gomes de Farias
 * Data: 12/03/2023
 */

public class DataBaseGui {

	Connection con;
	Statement st;
	String query;

	private JFrame frame;
	private JTextField t1;
	private JTextField t2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataBaseGui window = new DataBaseGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DataBaseGui() throws ClassNotFoundException, SQLException {
		initialize();
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sycs", "root", "454240123Nc#");
		st = con.createStatement();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Student Entry");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(151, 11, 114, 27);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Enter Roll Number:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 68, 137, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Enter Name:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 132, 131, 14);
		frame.getContentPane().add(lblNewLabel_2);

		t1 = new JTextField();
		t1.setBounds(10, 95, 400, 20);
		frame.getContentPane().add(t1);
		t1.setColumns(10);

		t2 = new JTextField();
		t2.setBounds(10, 157, 400, 20);
		frame.getContentPane().add(t2);
		t2.setColumns(10);

		JButton btnNewButton_1 = new JButton("UPDATE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int rollNumber = Integer.parseInt(JOptionPane.showInputDialog("Informe um ID para modificar o nome da tabela."));
					String newName = JOptionPane.showInputDialog("Informe um novo nome:");
					query = "update student set name = '" + newName + "' where rollno = " + rollNumber;
					int result = st.executeUpdate(query);
					JOptionPane.showMessageDialog(null,"Nome alterado com sucesso.");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(123, 199, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("DELETE");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int rollNumber = Integer.parseInt(JOptionPane.showInputDialog("Informe o id que deseja eliminar da tabela."));
					query = "delete from student where rollno = " + rollNumber + ";";

					st.execute(query);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(222, 199, 89, 23);
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("CLEAR");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opcao;
				do {
					opcao = JOptionPane.showInputDialog("Atenção, prosseguir com essa ação vai limpar todos os registros da sua tabela. Tem certeza"
							+ " que deseja prosseguir com esta ação? (sim/nao)");

					if (opcao.contains("nao")) {
						JOptionPane.showMessageDialog(null, "Operação cancelada com êxito. Registros mantidos na tabela.");
						break;
					} else if (opcao.contains("sim")){
						query = "delete from student;";
						try {
							st.execute(query);
							JOptionPane.showMessageDialog(null, "Todos os registros foram apagados com sucesso.");
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						
					}
				} while ((opcao != "nao" || opcao != "sim"));

			}
		});
		btnNewButton_3.setBounds(321, 199, 89, 23);
		frame.getContentPane().add(btnNewButton_3);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int rollNumber = Integer.parseInt(t1.getText());
					String name = t2.getText();
					query = "insert into student values(" + rollNumber + ",'" + name + "');";
					int result = st.executeUpdate(query);
					JOptionPane.showMessageDialog(null, result + " records added!!", name, result, null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(24, 199, 89, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_4 = new JButton("Obter Tabela");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				query = "SELECT * FROM student";
				try {
					ResultSet rs = st.executeQuery(query);
					ResultSetMetaData metadata = rs.getMetaData();
					int contarColunas = metadata.getColumnCount();
					for (int i = 1; i <= contarColunas; i++) {
						System.out.print(metadata.getColumnName(i) + "\t");
					}
					System.out.println();

					while (rs.next()) {
						for (int i = 1; i <= contarColunas; i++) {
							System.out.print(rs.getString(i) + "\t");
						}
						System.out.println();
					}

					rs.close();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnNewButton_4.setBounds(24, 227, 386, 23);
		frame.getContentPane().add(btnNewButton_4);
	}
}
