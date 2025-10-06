package tienda;

import DAO.ProductoDAO;
import carrito.Carrito;
import carrito.ItemCarrito;
import sendemail.EmailService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {     // ciclo while para hacer una lista
            System.out.println("\n===== MENÚ PRINCIPAL  =====");
            System.out.println("1. CRUD Clientes");
            System.out.println("2. CRUD Pedidos");
            System.out.println("3. CRUD Facturas");
            System.out.println("4. Tienda / Carrito");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> crudClientes(sc);
                case 2 -> crudPedidos(sc);
                case 3 -> crudFacturas(sc);
                case 4 -> menuTienda(sc);
                case 0 -> System.out.println("Saliendo▫️");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);

        HibernateUtil.shutdown();
    }

    // ------------------- CRUD CLIENTES -------------------
    private static void crudClientes(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== CRUD CLIENTES =====");
            System.out.println("1. Crear cliente");
            System.out.println("2. leer clientes");
            System.out.println("3. Actualizar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("0. Volver");
            System.out.print("Seleccionar otra opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearCliente(sc);
                case 2 -> listarClientes();
                case 3 -> actualizarCliente(sc);
                case 4 -> eliminarCliente(sc);
                case 0 -> System.out.println("Volviendo");
                default -> System.out.println("❌Opción inválida");
            }
        } while (opcion != 0);
    }

    // crear cliente
    private static void crearCliente(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // los registros que insertaremos de acuerdo a los campos de mysql
        Cliente cliente = new Cliente();
        System.out.print("Nombre: ");
        cliente.setNombre(sc.nextLine());
        System.out.print("Correo: ");
        cliente.setCorreo(sc.nextLine());
        System.out.print("Teléfono: ");
        cliente.setTelefono(sc.nextLine());

        session.persist(cliente);
        tx.commit();
        session.close();

        System.out.println("Cliente creado en la base de datos exitosamente✅");
    }

    // leer clientes
    private static void listarClientes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Cliente> clientes = session.createQuery("from Cliente", Cliente.class).list();

        System.out.println("\n--- Lista de Clientes ---");
        for (Cliente c : clientes) {
            System.out.println(c.getIdCliente() + " | " + c.getNombre() + " | " + c.getCorreo() + " | " + c.getTelefono());
        }
        session.close();
    }

    // actualizar cliente
    private static void actualizarCliente(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Ingrese ID del cliente a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Cliente cliente = session.get(Cliente.class, id);
        if (cliente != null) {
            System.out.print("Nuevo nombre (" + cliente.getNombre() + "): ");
            cliente.setNombre(sc.nextLine());
            System.out.print("Nuevo correo (" + cliente.getCorreo() + "): ");
            cliente.setCorreo(sc.nextLine());
            System.out.print("Nuevo teléfono (" + cliente.getTelefono() + "): ");
            cliente.setTelefono(sc.nextLine());

            session.merge(cliente);
            tx.commit();
            System.out.println("Cliente actualizado exitosamente en la base de datos✅");
        } else {
            System.out.println("Cliente no encontrado❌");
        }
        session.close();
    }

    // delete cliente
    private static void eliminarCliente(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Ingrese ID del cliente a eliminar: ");
        int id = sc.nextInt();

        Cliente cliente = session.get(Cliente.class, id);
        if (cliente != null) {
            session.remove(cliente);
            tx.commit();
            System.out.println("Cliente eliminado exitosamente en la base de datos✅");
        } else {
            System.out.println("Cliente no encontrado❌");
        }
        session.close();
    }

    // ------------------- CRUD PEDIDOS -------------------
    private static void crudPedidos(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== CRUD PEDIDOS =====");
            System.out.println("1. Crear pedido");
            System.out.println("2. leer pedidos");
            System.out.println("3. Actualizar pedido");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccionar otra opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearPedido(sc);
                case 2 -> listarPedidos();
                case 3 -> actualizarPedido(sc);
                case 4 -> eliminarPedido(sc);
                case 0 -> System.out.println("Volviendo");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    // crear pedido
    private static void crearPedido(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Pedido pedido = new Pedido();
        System.out.print("ID Usuario: ");
        pedido.setIdUsuario(sc.nextInt());
        sc.nextLine();
        System.out.print("Estado: ");
        pedido.setEstadoPedido(sc.nextLine());
        System.out.print("Fecha (YYYY-MM-DD): ");
        pedido.setFecha(java.sql.Date.valueOf(sc.nextLine()));

        session.persist(pedido);
        tx.commit();
        session.close();

        System.out.println("Pedido creado en exitosamente en la base de datos✅");
    }

    // leer pedidos
    private static void listarPedidos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Pedido> pedidos = session.createQuery("from Pedido", Pedido.class).list();

        System.out.println("\n--- Lista de Pedidos ---");
        for (Pedido p : pedidos) {
            System.out.println(p.getIdPedido() + " | " + p.getIdUsuario() + " | " + p.getEstadoPedido() + " | " + p.getFecha());
        }
        session.close();
    }

    // actualizar pedido
    private static void actualizarPedido(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Ingrese ID del pedido a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Pedido pedido = session.get(Pedido.class, id);
        if (pedido != null) {
            System.out.print("Nuevo estado (" + pedido.getEstadoPedido() + "): ");
            pedido.setEstadoPedido(sc.nextLine());
            System.out.print("Nueva fecha (" + pedido.getFecha() + "): ");
            pedido.setFecha(java.sql.Date.valueOf(sc.nextLine()));

            session.merge(pedido);
            tx.commit();
            System.out.println("Pedido actualizado exitosamente en la base de datos✅");
        } else {
            System.out.println("Pedido no encontrado❌");
        }
        session.close();
    }

    // eliminar pedido
    private static void eliminarPedido(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Ingrese ID del pedido a eliminar: ");
        int id = sc.nextInt();

        Pedido pedido = session.get(Pedido.class, id);
        if (pedido != null) {
            session.remove(pedido);
            tx.commit();
            System.out.println("Pedido eliminado exitosamente en la base de datos✅");
        } else {
            System.out.println("Pedido no encontrado❌");
        }
        session.close();
    }

    // ------------------- CRUD FACTURAS -------------------
    private static void crudFacturas(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== CRUD FACTURAS =====");
            System.out.println("1. Crear factura");
            System.out.println("2. leer facturas");
            System.out.println("3. Actualizar factura");
            System.out.println("4. Eliminar factura");
            System.out.println("0. Volver");
            System.out.print("Seleccionar otra opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearFactura(sc);
                case 2 -> listarFacturas();
                case 3 -> actualizarFactura(sc);
                case 4 -> eliminarFactura(sc);
                case 0 -> System.out.println("Volviendo");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    // crear factura
    private static void crearFactura(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Factura factura = new Factura();
        System.out.print("ID Producto: ");
        factura.setIdProducto(sc.nextInt());
        System.out.print("ID Pedido: ");
        factura.setIdPedido(sc.nextInt());
        System.out.print("Precio unitario: ");
        factura.setPrecioUnitario(sc.nextDouble());
        System.out.print("Cantidad: ");
        factura.setCantidad(sc.nextInt());

        session.persist(factura);
        tx.commit();
        session.close();

        System.out.println("Factura creada exitosamente en la base de datos✅");
    }

    // leer facturas
    private static void listarFacturas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Factura> facturas = session.createQuery("from Factura", Factura.class).list();

        System.out.println("\n--- Lista de Facturas ---");
        for (Factura f : facturas) {
            System.out.println(f.getIdFactura() + " | " + f.getIdProducto() + " | " + f.getIdPedido() + " | " + f.getPrecioUnitario() + " | " + f.getCantidad());
        }
        session.close();
    }

    // actualizar factura
    private static void actualizarFactura(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Ingrese ID de la factura a actualizar: ");
        int id = sc.nextInt();

        Factura factura = session.get(Factura.class, id);
        if (factura != null) {
            System.out.print("Nuevo precio unitario (" + factura.getPrecioUnitario() + "): ");
            factura.setPrecioUnitario(sc.nextDouble());
            System.out.print("Nueva cantidad (" + factura.getCantidad() + "): ");
            factura.setCantidad(sc.nextInt());

            session.merge(factura);
            tx.commit();
            System.out.println("Factura actualizada exitosamente en la base de datos✅");
        } else {
            System.out.println("Factura no encontrada❌");
        }
        session.close();
    }

    // eliminar factura
    private static void eliminarFactura(Scanner sc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("Ingrese ID de la factura a eliminar: ");
        int id = sc.nextInt();

        Factura factura = session.get(Factura.class, id);
        if (factura != null) {
            session.remove(factura);
            tx.commit();
            System.out.println("Factura eliminada exitosamente en la base de datos✅");
        } else {
            System.out.println("Factura no encontrada❌");
        }
        session.close();
    }

    // ------------------- MENÚ TIENDA / CARRITO -------------------
    private static void menuTienda(Scanner sc) {
        ProductoDAO productoDAO = new ProductoDAO();
        Carrito carrito = new Carrito();

        int opcion;
        do {
            System.out.println("\n===== MENÚ TIENDA =====");
            System.out.println("1. Ver productos disponibles");
            System.out.println("2. Buscar producto");
            System.out.println("3. Añadir producto al carrito");
            System.out.println("4. Ver carrito");
            System.out.println("5. Procesar pedido");
            System.out.println("6. Ver productos externos (API)"); //
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    productoDAO.findAll().forEach(p ->
                            System.out.println(p.getId_producto() + " - " + p.getNombre() + " ($" + p.getPrecio() + ") Stock: " + p.getStock()));
                }
                case 2 -> {
                    System.out.print("Nombre del producto a buscar: ");
                    String nombre = sc.nextLine();
                    productoDAO.buscarPorNombre(nombre)
                            .forEach(p -> System.out.println(p.getId_producto() + " - " + p.getNombre() + " ($" + p.getPrecio() + ")"));
                }
                case 3 -> {
                    System.out.print("ID del producto: ");
                    int id = sc.nextInt();
                    System.out.print("Cantidad: ");
                    int cantidad = sc.nextInt();

                    Producto p = productoDAO.findById((long) id);
                    if (p != null && p.getStock() >= cantidad) {
                        carrito.agregarProducto(p, cantidad);
                        System.out.println("Producto añadido al carrito exitosamente✅");
                    } else {
                        System.out.println("No hay suficiente stock o el producto no existe❌");
                    }
                }
                case 4 -> carrito.mostrarCarrito();
                case 5 -> procesarPedido(carrito, productoDAO);
                case 6 -> mostrarProductosAPI(); //
            }
        } while (opcion != 0);
    }

    // ------------------- PROCESAR PEDIDO -------------------
    private static void procesarPedido(Carrito carrito, ProductoDAO productoDAO) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Ingresa tu correo electrónico para recibir la confirmación: ");
            String clienteEmail = sc.nextLine();

            String asunto = "COMFIRMACION DE TU PEDIDO";
            String cuerpo = "Gracias por tu compra.\n\nResumen del pedido:\n\n"
                    + carrito.listarItems()
                    + "\n\nTotal: $" + carrito.getTotal();


            EmailService emailService = new EmailService();
            emailService.enviarCorreo(clienteEmail, asunto, cuerpo);

            System.out.println("📧✅Correo enviado correctamente a " + clienteEmail);
        } catch (Exception e) {
            System.err.println("⚠️ Error al enviar el correo: " + e.getMessage());
        }

        System.out.println("✅ Pedido procesado correctamente. Total: $" + carrito.getTotal());
        carrito.vaciar();
    }

    // ------------------- MOSTRAR PRODUCTOS DESDE API -------------------
    private static void mostrarProductosAPI() {
        System.out.println("\n=== PRODUCTOS DESDE LA API ===");
        List<ProductoAPI.ProductoRemoto> productos = ProductoAPI.obtenerProductos();

        if (productos != null && !productos.isEmpty()) {
            for (ProductoAPI.ProductoRemoto p : productos) {
                System.out.println(p);
            }
        } else {
            System.out.println("⚠️ No se pudieron cargar los productos de la API.");
        }
    }
}
