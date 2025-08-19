package it.unibo.adozione_animali;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.SQLDialect;
import java.sql.Connection;
import java.sql.DriverManager;

public class Start {
        public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Adozione_Animali"
            )) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            // Solo per test iniziale o setup
            System.out.println("Persone presenti nel DB:");
            create.selectFrom(Persona.PERSONA)
                        .fetch()
                        .forEach(System.out::println);

            // Da qui in poi, passa create a un service o controller

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
