package it.unibo.adozione_animali;

import it.unibo.adozione_animali.view.MainMenu;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.SQLDialect;
import java.sql.Connection;
import java.sql.DriverManager;

public class Start {

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Adozione_Animali",
                "root",
                ""
        )) {

            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainMenu().startMenu();

    }
}
