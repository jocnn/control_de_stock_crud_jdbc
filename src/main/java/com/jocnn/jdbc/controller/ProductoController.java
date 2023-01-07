package com.jocnn.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.jocnn.jdbc.dao.ProductoDAO;
import com.jocnn.jdbc.modelo.Producto;

import con.jocnn.jdbc.factory.ConnectionFactory;

public class ProductoController {
	private ProductoDAO productoDAO;
	
	public ProductoController() {
		var factory = new ConnectionFactory();
		this.productoDAO = new ProductoDAO(factory.recuperaConexion());
	}
	
	public int modificar(Producto producto) {
		return productoDAO.modificar(producto);
	}
	
	public int eliminar(Integer id){
		return productoDAO.eliminar(id);
	}
	
	public List<Producto> listar() {
		return productoDAO.listar();
	}
	
    public void guardar(Producto producto) {
		productoDAO.guardar(producto);
	}
}
