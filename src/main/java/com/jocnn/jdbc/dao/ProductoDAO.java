package com.jocnn.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jocnn.jdbc.modelo.Producto;

public class ProductoDAO {
	final private Connection cn;
	
	public ProductoDAO(Connection cn) {
		this.cn = cn;
	}
	
	public int modificar(Producto producto) {
		
		try (cn) {
			final PreparedStatement statement = cn.prepareStatement("UPDATE producto SET"
				+ " nombre = ?"
				+ ", descripcion = ?"
				+ ", cantidad = ?"
				+ " WHERE id = ?");
				
			try (statement) {
				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getId());
				statement.execute();
				int updateCount = statement.getUpdateCount();
			
				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int eliminar(Integer id) {

		try (cn) {
			final PreparedStatement statement = cn.prepareStatement("DELETE FROM producto WHERE id = ?");
			
			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void guardar(Producto producto) {
		try (cn) {
			final PreparedStatement statement = cn.prepareStatement("INSERT INTO producto "
					+ "(nombre, descripcion, cantidad)"
					+ " VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			try (statement) {
				ejecutaRegistro(producto, statement);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
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

	public List<Producto> listar() {		
		List<Producto> resultado = new ArrayList<>();
		
		try (cn) {
			final PreparedStatement statement = cn.prepareStatement(
				"SELECT id, nombre, descripcion, cantidad FROM producto");
				
			try (statement) {
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						resultado.add(new Producto(
							resultSet.getInt("id"),
							resultSet.getString("nombre"),
							resultSet.getString("descripcion"),
							resultSet.getInt("cantidad")
						));	
					}
				}
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
	
}
