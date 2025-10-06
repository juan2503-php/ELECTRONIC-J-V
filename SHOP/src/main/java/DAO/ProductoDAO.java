package DAO;

import org.hibernate.Session;
import tienda.HibernateUtil;
import tienda.Producto;

import java.util.List;

public class ProductoDAO extends GenericDAO<Producto> {

    public ProductoDAO() {
        super(Producto.class);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Producto where nombre like :nombre", Producto.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .list();
        }
    }
}
