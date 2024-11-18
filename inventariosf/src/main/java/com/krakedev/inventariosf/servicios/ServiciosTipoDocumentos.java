package com.krakedev.inventariosf.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.TipoDocumentosBDD;
import com.krakedev.inventariosf.entidades.TipoDocumentos;
import com.krakedev.inventariosf.excepciones.KrakeDevException;

@Path("tiposdocumento")
public class ServiciosTipoDocumentos {

	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(){
		TipoDocumentosBDD tipoDBDD=new TipoDocumentosBDD();
		ArrayList<TipoDocumentos> tiposDocumentos=null;
		try {
			tiposDocumentos = tipoDBDD.recuperar();
			return Response.ok(tiposDocumentos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
