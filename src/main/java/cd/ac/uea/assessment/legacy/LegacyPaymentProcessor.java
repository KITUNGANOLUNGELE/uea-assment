package cd.ac.uea.assessment.legacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class LegacyPaymentProcessor {

    public void processPayment(Long orderId, Double amount) throws SQLException {

        /*
        nous allons utiliser JPA pour ce faire alors on va:
        - flanquer tout ce qui est chqine de connexion dans les properties de l'app
        - Utiliser notre chaine de connexion selon ce que l'on a prevu dans docker compose
        - commenter tout ce qui est sql ici
        - concevoir un model pour le paiement que je vais nommer Paie
        - creer un repository pour Paie que je vais nommer PaieRepository
        - Creer un service pour ce repository
        - cette classe ne sera plus utilisé mais la classe service va prendre le relai
        */
        /*Connection conn = null;
        PreparedStatement stmt = null;*/
        try {

            //remove the commentedblock
            /*String url = "jdbc:postgresql://localhost:5432/mydb";
            String user = "postgres";
            String password = "secret";

            conn = DriverManager.getConnection(url, user, password);
            
            String sql = "INSERT INTO payments (order_id, amount) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, orderId);
            stmt.setDouble(2, amount);
            
            stmt.executeUpdate();*/
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            //remove comented block
            /* try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } */
        }
    }
}
