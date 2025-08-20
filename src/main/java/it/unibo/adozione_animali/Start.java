package it.unibo.adozione_animali;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.SQLDialect;
import java.sql.Connection;
import java.sql.DriverManager;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Desktop;
import java.net.URI;

public class Start {

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Adozione_Animali"
        )) {

            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

            server.createContext("/", exchange -> {
                byte[] response = Files.readAllBytes(Paths.get("view/index.html"));

                exchange.sendResponseHeaders(200, response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            });

            server.setExecutor(null);
            server.start();

            System.out.println("Server avviato su http://localhost:8081");

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:8081"));
            } else {
                System.out.println("Impossibile aprire il browser automaticamente.");
            }

            server.createContext("/static", exchange -> {
                String path = exchange.getRequestURI().getPath().replace("/static/", "");
                java.nio.file.Path filePath = Paths.get("view/static/" + path);

                if (Files.exists(filePath)) {
                    String contentType = "application/octet-stream";
                    if (path.endsWith(".css")) contentType = "text/css";
                    else if (path.endsWith(".js")) contentType = "application/javascript";
                    else if (path.endsWith(".html")) contentType = "text/html";
                    else if (path.endsWith(".png")) contentType = "image/png";
                    else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) contentType = "image/jpeg";
                    else if (path.endsWith(".ico")) contentType = "image/x-icon";

                    byte[] response = Files.readAllBytes(filePath);
                    exchange.getResponseHeaders().add("Content-Type", contentType);
                    exchange.sendResponseHeaders(200, response.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response);
                    }
                } else {
                    String error = "File non trovato: " + path;
                    exchange.sendResponseHeaders(404, error.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(error.getBytes());
                    }
                }
            });

        } catch (Exception e) {
            System.err.println("Errore nell'avvio del server: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
