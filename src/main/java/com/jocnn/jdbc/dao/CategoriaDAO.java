package com.jocnn.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jocnn.jdbc.modelo.Categoria;

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
}
