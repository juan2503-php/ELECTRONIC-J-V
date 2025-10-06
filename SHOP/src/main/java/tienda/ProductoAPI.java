package tienda;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProductoAPI {

    // Clase interna para mapear los datos del JSON
    public static class ProductoRemoto {
        int id;
        String title;
        double price;
        String description;
        String category;
        String image;

        @Override
        public String toString() {
            return "\n🛒 Producto: " + title +
                    "\n💲 Precio: $" + price +
                    "\n📦 Categoría: " + category +
                    "\n📝 Descripción: " + description +
                    "\n";
        }
    }

    // Método para consumir la API
    public static List<ProductoRemoto> obtenerProductos() {
        try {
            URL url = new URL("https://fakestoreapi.com/products");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea);
            }

            // Parsear JSON a lista de objetos
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<ProductoRemoto>>() {}.getType();
            List<ProductoRemoto> productos = gson.fromJson(sb.toString(), tipoLista);

            conn.disconnect();
            return productos;

        } catch (Exception e) {
            System.err.println("⚠️ Error al consumir la API: " + e.getMessage());
            return null;
        }
    }
}
