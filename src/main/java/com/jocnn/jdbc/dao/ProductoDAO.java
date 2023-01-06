package com.jocnn.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jocnn.jdbc.modelo.Producto;

public class ProductoDAO {
	final private Connection cn;
	
	public ProductoDAO(Connection cn) {
		this.cn = cn;
	}
	
	public void guardarProducto(Producto producto) throws SQLException {
		
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
