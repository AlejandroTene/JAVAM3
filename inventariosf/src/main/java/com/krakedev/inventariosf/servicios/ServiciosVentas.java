package com.krakedev.inventariosf.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.VentasBDD;
import com.krakedev.inventariosf.entidades.Ventas;
import com.krakedev.inventariosf.excepciones.KrakeDevException;

@Path("ventas")
public class ServiciosVentas {
	
	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Ventas venta){
		VentasBDD ventaBDD=new VentasBDD();
		try {
			ventaBDD.insertar(venta);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
