package com.jocnn.jdbc.test;

import java.sql.Connection;
import java.sql.SQLException;

import con.jocnn.jdbc.factory.ConnectionFactory;

public class PruebaPoolConexiones {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
	
		for (int i = 0; i < 20; i++) {
			Connection cn = connectionFactory.recuperaConexion();
			
			System.out.println("abriendo conexion " + (i+1));
		}

	}
}
