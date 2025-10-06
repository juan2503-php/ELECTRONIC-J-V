package tienda;

import jakarta.persistence.*;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int idFactura;

    @Column(name = "id_producto")
    private int idProducto;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    @Column(name = "cantidad")
    private int cantidad;

    // --- Getters y Setters ---
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setIdPedido(int i) {
    }

    public int getIdPedido() {
        return 0;
    }
}
