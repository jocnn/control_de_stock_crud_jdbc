package com.jocnn.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import con.jocnn.jdbc.factory.ConnectionFactory;

public class ProductoController {
	
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection cn = factory.recuperaConexion();
		
		PreparedStatement statement = cn.prepareStatement("UPDATE producto SET"
			+ " nombre = ?"
			+ ", descripcion = ?"
			+ ", cantidad = ?"
			+ " WHERE id = ?");
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.setInt(4, id);
		statement.execute();

		int updateCount = statement.getUpdateCount();
		
		cn.close();
		return updateCount;
	}
	
	public int eliminar(Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection cn = factory.recuperaConexion();
		
		PreparedStatement statement = cn.prepareStatement("DELETE FROM producto WHERE id = ?");
		statement.setInt(1, id);
		statement.execute();

		int updateCount = statement.getUpdateCount();
		
		cn.close();
		return updateCount;
	}
	
	public List<Map<String, String>> listar() throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection cn = factory.recuperaConexion();
		
		PreparedStatement statement = cn.prepareStatement(
			"SELECT id, nombre, descripcion, cantidad FROM producto");
		statement.execute();
		
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
	
    public void guardar(Map<String, String> producto) throws SQLException {
		String nombre = producto.get("nombre");
		String descripcion = producto.get("descripcion");
		Integer cantidad = Integer.valueOf(producto.get("cantidad"));
		Integer maxCantidad = 50;
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection cn = factory.recuperaConexion();
		cn.setAutoCommit(false);
		
		PreparedStatement statement = cn.prepareStatement("INSERT INTO producto " 
			+ "(nombre, descripcion, cantidad)" 
			+ " VALUES (?, ?, ?)", 
			Statement.RETURN_GENERATED_KEYS);

		try {
			do {
				int cantidadParaGuardar = Math.min(cantidad, maxCantidad);
				ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);
				cantidad -= maxCantidad;
			} while (cantidad > 0);
			cn.commit();
			System.out.println("commit");
		} catch (Exception e) {
			cn.rollback();
			System.out.println("rollback");
		} finally {
			statement.close();
			cn.close();
		}
	}

	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		// código de prueba para commit y rollback en el método guardar
		// if (cantidad < 50) {
		// 	throw new RuntimeException("Ocurrio un error");
		// }
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.execute();
		
		ResultSet resultSet = statement.getGeneratedKeys();
		
		while( resultSet.next()) {
			System.out.println(
				String.format("Fue insertado el producto de ID %d",
				resultSet.getInt(1)
			));
		}
	}
}
