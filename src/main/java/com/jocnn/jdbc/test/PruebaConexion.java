package com.jocnn.jdbc.test;

import java.sql.Connection;
import java.sql.SQLException;

import con.jocnn.jdbc.factory.ConnectionFactory;

public class PruebaConexion {
	public static void main(String[] args) throws SQLException {
		Connection cn = new ConnectionFactory().recuperaConexion();

		System.out.println("La conexión a la db esta abierta");
		
		cn.close();
		System.out.println("La conexión a la db esta cerrada");
	}
}
