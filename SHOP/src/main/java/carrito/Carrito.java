package carrito;

import tienda.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Carrito {
    private List<ItemCarrito> items = new ArrayList<>();

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId_producto() == producto.getId_producto()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(int idProducto) {
        items.removeIf(item -> item.getProducto().getId_producto() == idProducto);
    }

    public void vaciar() {
        items.clear();
    }

    public double getTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }


    public List<ItemCarrito> getItems() {
        return items;
    }

    public void mostrarCarrito() {
        if (items.isEmpty()) {
            System.out.println("🛒 El carrito está vacío.");
        } else {
            System.out.println("\n--- Carrito ---");
            for (ItemCarrito item : items) {
                System.out.println(item);
            }
            System.out.println("Total: $" + getTotal());
        }
    }

    public Map<Object, Object> getProductos() {
        return Map.of();
    }

    public String listarItems() {
        if (items == null || items.isEmpty()) {
            return "No hay productos en el carrito.";
        }
        StringBuilder sb = new StringBuilder();
        for (ItemCarrito item : items) {
            sb.append("- ").append(item.getProducto().getNombre())
                    .append(" x ").append(item.getCantidad())
                    .append(" = $").append(item.getProducto().getPrecio())
                    .append("\n");
        }
        return sb.toString();
    }


}
