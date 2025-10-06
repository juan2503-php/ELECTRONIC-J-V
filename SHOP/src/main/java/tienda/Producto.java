package tienda;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    private String nombre;
    private double precio;
    private int stock;

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(Categoria categoria, String nombre, double precio, int stock) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
