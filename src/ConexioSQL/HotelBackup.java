package ConexioSQL;

import java.sql.*;

public class HotelBackup {

    private static final String ORIGINAL = "Hotel";
    private static final String BACKUP = "Hotel_Backup";

    public static void realizarBackup() throws SQLException {

        ConexioBD.crearBaseDeDatos(BACKUP);

        HotelDAO.crearTablas(BACKUP);

        copiarDatos(ORIGINAL, BACKUP);

        System.out.println("Backup realizado correctamente.");
    }

    public static void restaurarBackup() throws SQLException {

        copiarDatos(BACKUP, ORIGINAL);

        System.out.println("Restauración realizada correctamente.");
    }


    private static void copiarDatos(String origen, String destino)
            throws SQLException {

        try (Connection cn = ConexioBD.conectar(origen);
             Statement st = cn.createStatement()) {

            cn.setAutoCommit(false);

            try {

                // 1. VACIAR LAS TABLAS DEL DESTINO
                // Primero las tablas que tienen claves foráneas.
                st.executeUpdate("DELETE FROM " + destino + ".estadias");
                st.executeUpdate("DELETE FROM " + destino + ".reservas");
                st.executeUpdate("DELETE FROM " + destino + ".habitaciones");
                st.executeUpdate("DELETE FROM " + destino + ".pasajeros");
                st.executeUpdate("DELETE FROM " + destino + ".administradores");
                st.executeUpdate("DELETE FROM " + destino + ".recepcionistas");

                // 2. COPIAR PASAJEROS
                st.executeUpdate(
                        "INSERT INTO " + destino + ".pasajeros " +
                                "SELECT * FROM " + origen + ".pasajeros"
                );

                // 3. COPIAR HABITACIONES
                st.executeUpdate(
                        "INSERT INTO " + destino + ".habitaciones " +
                                "SELECT * FROM " + origen + ".habitaciones"
                );

                // 4. COPIAR ADMINISTRADORES
                st.executeUpdate(
                        "INSERT INTO " + destino + ".administradores " +
                                "SELECT * FROM " + origen + ".administradores"
                );

                // 5. COPIAR RECEPCIONISTAS
                st.executeUpdate(
                        "INSERT INTO " + destino + ".recepcionistas " +
                                "SELECT * FROM " + origen + ".recepcionistas"
                );

                // 6. COPIAR RESERVAS
                st.executeUpdate(
                        "INSERT INTO " + destino + ".reservas " +
                                "SELECT * FROM " + origen + ".reservas"
                );

                // 7. COPIAR OCUPACIONES
                st.executeUpdate(
                        "INSERT INTO " + destino + ".estadias " +
                                "SELECT * FROM " + origen + ".estadias"
                );
                // 8. CONFIRMAR LOS CAMBIOS
                cn.commit();
            } catch (SQLException e) {
                // SI ALGO FALLA, CANCELAR TODOS LOS CAMBIOS
                cn.rollback();
                throw e;
            }
        }
    }

}
