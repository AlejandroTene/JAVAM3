package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventariosf.entidades.TipoDocumentos;
import com.krakedev.inventariosf.excepciones.KrakeDevException;
import com.krakedev.inventariosf.utils.ConexionBDD;

public class TipoDocumentosBDD {
	public ArrayList<TipoDocumentos> recuperar() throws KrakeDevException{
		ArrayList<TipoDocumentos> tiposDocumentos=new ArrayList<TipoDocumentos>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TipoDocumentos tipoDoc=null;
		try {
			con=ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select * from tipo_de_documentos");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String codigo=rs.getString("codigo");
				String descripcion=rs.getString("descripcion");
				
				tipoDoc=new TipoDocumentos(codigo,descripcion);
				tiposDocumentos.add(tipoDoc);	
			}
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: "+e.getMessage());
		}
		
		return tiposDocumentos;
	}
}
