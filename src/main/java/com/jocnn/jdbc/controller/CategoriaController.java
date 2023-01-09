package com.jocnn.jdbc.controller;

import java.util.List;

import com.jocnn.jdbc.dao.CategoriaDAO;
import com.jocnn.jdbc.modelo.Categoria;

import con.jocnn.jdbc.factory.ConnectionFactory;

public class CategoriaController {

	private CategoriaDAO categoriaDAO;

	public CategoriaController() {
		var factory = new ConnectionFactory();
		this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion());
	}

	public List<Categoria> listar() {
		return categoriaDAO.listar();
	}

    public List<Categoria> cargaReporte() {
        return this.categoriaDAO.listarConProductos();
    }
}
