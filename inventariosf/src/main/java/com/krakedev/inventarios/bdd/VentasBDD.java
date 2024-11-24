package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventariosf.entidades.DetallePedido;
import com.krakedev.inventariosf.entidades.DetalleVentas;
import com.krakedev.inventariosf.entidades.Ventas;
import com.krakedev.inventariosf.excepciones.KrakeDevException;
import com.krakedev.inventariosf.utils.ConexionBDD;

public class VentasBDD {
	public void insertar(Ventas venta) throws KrakeDevException{
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		PreparedStatement psHis = null;
		ResultSet rsClave=null;
		int codigoCabecera=0;
		
		Date fechaActual=new Date();
		Timestamp fechaHoraActual=new Timestamp(fechaActual.getTime());
		
		try {
			con=ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("insert into cabecera_ventas (fecha,total_sin_iva,iva,total) "
					+ "values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, fechaHoraActual);
			ps.setBigDecimal(2, new BigDecimal(0) );
			ps.setBigDecimal(3, new BigDecimal(0));
			ps.setBigDecimal(4, new BigDecimal(0));
			
			ps.executeUpdate();
			
			rsClave=ps.getGeneratedKeys();
			if(rsClave.next()) {
				codigoCabecera=rsClave.getInt(1);
			}
			ArrayList<DetalleVentas> detallesVenta=venta.getDetalles();
			DetalleVentas det;
			BigDecimal totalSinIva = BigDecimal.ZERO;
			BigDecimal totalIva = BigDecimal.ZERO;
			BigDecimal totalConIva = BigDecimal.ZERO;
			for(int i=0;i<detallesVenta.size();i++) {
				det=detallesVenta.get(i);
				psDet=con.prepareStatement("insert into detalle_ventas (cabecera_ventas,producto,cantidad,precio_venta,subtotal,subtotal_con_iva) "
						+ "values(?,?,?,?,?,?)");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidad());
				psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());
				BigDecimal pv=det.getProducto().getPrecioVenta();
				BigDecimal cantidad=new BigDecimal(det.getCantidad());
				System.out.println(pv);
				System.out.println(cantidad);
				BigDecimal subtotal=pv.multiply(cantidad);
				psDet.setBigDecimal(5, subtotal);
				
				
				if(det.getProducto().isTieneIva()==true) {
					BigDecimal porIva = new BigDecimal(1.12);
					BigDecimal subtotalConIva = subtotal.multiply(porIva);
					BigDecimal iva = subtotalConIva.subtract(subtotal);
					psDet.setBigDecimal(6, subtotalConIva);
					totalConIva=totalConIva.add(subtotalConIva);
					totalIva=totalIva.add(iva);		
				}else {
					psDet.setBigDecimal(6, subtotal);
					totalSinIva=totalSinIva.add(subtotal);
				}
				
				BigDecimal total = totalConIva.add(totalSinIva);
				BigDecimal iva = totalIva;
				BigDecimal totalS = total.subtract(iva);
				System.out.println(total);
				System.out.println(iva);
				System.out.println(totalS);
				ps = con.prepareStatement("update cabecera_ventas set total_sin_iva=?, iva=?, total=? where codigo=?");
				ps.setBigDecimal(1, totalS);
				ps.setBigDecimal(2, iva);
				ps.setBigDecimal(3, total);
				ps.setInt(4, codigoCabecera);
				
				psDet.executeUpdate();
				ps.executeUpdate();
				
				psHis = con.prepareStatement("insert into historial_stock (fecha,referencia,producto,cantidad) "
						+ "values(?,?,?,?)");
				psHis.setTimestamp(1, fechaHoraActual);
				psHis.setString(2, "VENTA" + codigoCabecera);
				psHis.setInt(3, det.getProducto().getCodigo());
				psHis.setInt(4, det.getCantidad() * -1);
				psHis.executeUpdate();
			}
			
			
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar ventas. Detalle: "+e.getMessage());
		}
	}
}
