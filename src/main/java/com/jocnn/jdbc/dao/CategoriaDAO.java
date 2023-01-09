package com.jocnn.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jocnn.jdbc.modelo.Categoria;
import com.jocnn.jdbc.modelo.Producto;

public class CategoriaDAO {
	private Connection cn;
	
	public CategoriaDAO(Connection cn) {
		this.cn = cn;
	}

	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<>();

		try {
			final PreparedStatement statement = cn.prepareStatement(
				"SELECT id, nombre FROM categoria");
				
			try (statement) {
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						resultado.add(new Categoria(
							resultSet.getInt("id"),
							resultSet.getString("nombre")
						));	
					}
				}
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return resultado;
	}

	public List<Categoria> listarConProductos() {
		List<Categoria> resultado = new ArrayList<>();

		try {
			final PreparedStatement statement = cn.prepareStatement(
				"SELECT c.id, c.nombre, p.id, p.nombre, p.cantidad "
				+ "FROM categoria c "
				+ "INNER JOIN producto p ON c.id = p.categoria_id");
				
			try (statement) {
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						Integer categoriaId = resultSet.getInt("c.id");
						String categoriaNombre = resultSet.getString("c.nombre");

						var categoria = resultado
							.stream()
							.filter(cat -> cat.getId().equals(categoriaId))
							.findAny().orElseGet(() -> {
								Categoria cat = new Categoria(categoriaId, categoriaNombre);
								resultado.add(cat);
								return cat;
								});
						var producto = new Producto(
							resultSet.getInt("p.id"),
							resultSet.getString("p.nombre"),
								resultSet.getInt("p.cantidad"));
							
						categoria.agregar(producto);
					}
				}
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return resultado;
	}
}
