package com.krakedev.inventariosf.entidades;

public class UnidadDeMedida {
	private String codigo;
	private String descripcion;
	private CategoriaUDM categoriaUnidadMedida;
	
	
	public UnidadDeMedida() {
	}
	public UnidadDeMedida(String codigo, String descripcion, CategoriaUDM categoriaUnidadMedida) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.categoriaUnidadMedida = categoriaUnidadMedida;
	}
	public String getNombre() {
		return codigo;
	}
	public void setNombre(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CategoriaUDM getCategoriaUnidadMedida() {
		return categoriaUnidadMedida;
	}
	public void setCategoriaUnidadMedida(CategoriaUDM categoriaUnidadMedida) {
		this.categoriaUnidadMedida = categoriaUnidadMedida;
	}
	@Override
	public String toString() {
		return "UnidadDeMedida [nombre=" + codigo + ", descripcion=" + descripcion + ", categoriaUnidadMedida="
				+ categoriaUnidadMedida + "]";
	}
	
	
}
