package com.jocnn.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexion {
	public static void main(String[] args) throws SQLException {
		Connection cn = DriverManager.getConnection(
				"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"ijann90210");
		System.out.println("La conexión a la db esta abierta");
		
		cn.close();
		System.out.println("La conexión a la db esta cerrada");
	}
}
