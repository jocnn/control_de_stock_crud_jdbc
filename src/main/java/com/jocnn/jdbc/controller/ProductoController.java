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

import com.jocnn.jdbc.modelo.Producto;

import con.jocnn.jdbc.factory.ConnectionFactory;

public class ProductoController {
	
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection cn = factory.recuperaConexion();
		
		try (cn) {
			final PreparedStatement statement = cn.prepareStatement("UPDATE producto SET"
				+ " nombre = ?"
				+ ", descripcion = ?"
				+ ", cantidad = ?"
				+ " WHERE id = ?");
			statement.setString(1, nombre);
			statement.setString(2, descripcion);
			statement.setInt(3, cantidad);
			statement.setInt(4, id);
			
			try (statement) {
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		}
	}
	
	public int eliminar(Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection cn = factory.recuperaConexion();
		
		try (cn) {
			final PreparedStatement statement = cn.prepareStatement("DELETE FROM producto WHERE id = ?");
			
			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		}
	}
	
	public List<Map<String, String>> listar() throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection cn = factory.recuperaConexion();
		
		try (cn) {
			final PreparedStatement statement = cn.prepareStatement(
				"SELECT id, nombre, descripcion, cantidad FROM producto");

			try (statement) {
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
				return resultado;
			}
		}
	}
	
    public void guardar(Producto producto) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection cn = factory.recuperaConexion();
		
		try (cn) {
			cn.setAutoCommit(false);
			
			final PreparedStatement statement = cn.prepareStatement("INSERT INTO producto " 
				+ "(nombre, descripcion, cantidad)" 
				+ " VALUES (?, ?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				ejecutaRegistro(producto, statement);
				cn.commit();
				System.out.println("commit");
			} catch (Exception e) {
				e.printStackTrace();
				cn.rollback();
				System.out.println("rollback");
			}
		}
		
	}

	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
			
		try (resultSet) {
			while (resultSet.next()) {
				producto.setId(resultSet.getInt(1));
				System.out.println(
						String.format("Fue insertado el producto %s", producto));
			}
		}
	}
}
