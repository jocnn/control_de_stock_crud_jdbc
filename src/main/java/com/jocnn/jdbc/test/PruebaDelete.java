package com.jocnn.jdbc.test;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import con.jocnn.jdbc.factory.ConnectionFactory;

public class PruebaDelete {
	public static void main(String[] args) throws SQLException {
		Connection cn = new ConnectionFactory().recuperaConexion();

		Statement statement = cn.createStatement();

		statement.execute("DELETE FROM producto WHERE id = 99");

		System.out.println(statement.getUpdateCount());
	}
}
