package dbteste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExemplo {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
				
			Class.forName("com.mysql.cj.jdbc.Driver"); // Esse é o caminho onde a classe Driver se encontra. em Referenced Libraries, mysql-connector: com.mysql.cj.jdbc
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sycs","root","454240123Nc#"); // Essa interface permite a conexão com o Banco de Dados:
								// O método estático getConnection permite que você passe 3 parâmetros que serão usados para se conectar ao banco de dados:
								// Parâmetro 1 "é o endereço para conexão com o banco de dados, perceba que ao final do endereço tem o nome do Banco youtube, caso seja outro banco de dados, use outro banco de dados
								// Parâmetro 2 "nome de usuário" no caso, root
								// Parâmetro 3 a senha do usuário root 
						
			System.out.println("Conectado.");
			
			String query = "create table student(rollno int,name varchar(20))"; // criar uma consulta.
			
			Statement st = con.createStatement(); // Cria um objeto Statement para enviar consulas ao DB.
			System.out.println("Statement created.");
			
			st.execute(query);
			System.out.println("Tabela criada.");
		
			
	
	}

}
