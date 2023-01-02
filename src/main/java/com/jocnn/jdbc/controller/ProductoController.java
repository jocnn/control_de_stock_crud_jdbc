package com.jocnn.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {
	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection cn = DriverManager.getConnection(
			"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
			"root",
			"ijann90210");
		
		Statement statement = cn.createStatement();
		boolean result = statement.execute("SELECT id, nombre, descripcion, cantidad FROM producto");
		
		ResultSet resultSet = statement.getResultSet();
		
		List<Map<String, String>> resultado = new ArrayList<>();
		
		while (resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("id", String.valueOf(resultSet.getInt("id")));
			fila.put("nombre", resultSet.getString("nombre"));
			fila.put("descripcion", resultSet.getString("descripcion"));
			fila.put("cantidad", String.valueOf(resultSet.getInt("cantidad")));

			resultado.add(fila);
		}
		
		cn.close();
		
		return resultado;
	}

    public void guardar(Object producto) {
		// TODO
	}
}
