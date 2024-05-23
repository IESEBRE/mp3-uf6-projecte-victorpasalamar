package org.example.model.impls;

import org.example.model.daos.DAO;
import org.example.model.entities.Jefe;
import org.example.model.exceptions.DAOException;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AlumneDAOJDBCOracleImpl implements DAO<Jefe> {

    //Fem una funcio que crei la taula amb sql
    public void creartaules() throws DAOException {
        ResourceBundle rd
                = ResourceBundle.getBundle("system");
        Connection con = null;
        {
            String url = rd.getString("url");
            String user = rd.getString("userName");
            String password = rd.getString("password");

            try {

                // get the connection
                con = DriverManager.getConnection(
                        url, user, password);
                Statement st = con.createStatement();
                st.executeUpdate("BEGIN crear_taules; END;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertar(Jefe jefe) throws DAOException {
        ResourceBundle rd
                = ResourceBundle.getBundle("system");
        String nom = jefe.getNomJefe();
        double vida = jefe.getVida();
        int dificultat = jefe.isDificultat() ? 1 : 0;
        int numAtacs = jefe.getNumAtacs();
        String localitzacio = jefe.getLocalitzacio();
        {
            String url = rd.getString("url");
            String user = rd.getString("userName");
            String password = rd.getString("password");

            try (Connection con = DriverManager.getConnection(url, user, password);
                 CallableStatement cstmt = con.prepareCall("{ ? = call insertar_jefe(?, ?, ?, ?, ?) }")) {

                cstmt.registerOutParameter(1, Types.INTEGER);
                cstmt.setString(2, nom);
                cstmt.setDouble(3, vida);
                cstmt.setInt(4, dificultat);
                cstmt.setInt(5, numAtacs);
                cstmt.setString(6, localitzacio);

                cstmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void borrar(Jefe jefe) throws DAOException {
        ResourceBundle rd
                = ResourceBundle.getBundle("system");
        String nom = jefe.getNomJefe();
        double vida = jefe.getVida();
        int dificultat = jefe.isDificultat() ? 1 : 0;
        int numAtacs = jefe.getNumAtacs();
        String localitzacio = jefe.getLocalitzacio();
        {
            String url = rd.getString("url");
            String user = rd.getString("userName");
            String password = rd.getString("password");

            try (Connection con = DriverManager.getConnection(url, user, password);
                 CallableStatement cstmt = con.prepareCall("{call borrar_jefe(?, ?, ?, ?, ?)}")) {

                // Establir els paràmetres d'entrada
                cstmt.setString(1, nom);
                cstmt.setDouble(2, vida);
                cstmt.setInt(3, dificultat);
                cstmt.setInt(4, numAtacs);
                cstmt.setString(5, localitzacio);

                // Executar el procediment
                cstmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Jefe> getAll() throws DAOException {
        ResourceBundle rd
                = ResourceBundle.getBundle("system");
        //Declaració de variables del mètode
        List<Jefe> jefes = new ArrayList<>();
        //Accés a la BD usant l'API JDBC
        String url = rd.getString("url");
        String user = rd.getString("userName");
        String password = rd.getString("password");
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement st1 = con.prepareStatement("SELECT * FROM JEFES");
             ResultSet rs1 = st1.executeQuery();
             PreparedStatement st2 = con.prepareStatement("SELECT * FROM DIFICULTAT");
             ResultSet rs2 = st2.executeQuery();
        ) {
            while (rs1.next()) {
                boolean dificultat = false;
                if (rs2.next()) {
                    if (rs2.getInt(2) == 1) {
                        dificultat = true;
                    }
                }
                jefes.add(new Jefe(rs1.getString(2), rs1.getDouble(3), dificultat, rs1.getInt(4), rs1.getString(5)));
            }
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getErrorCode());
            int tipoError = throwables.getErrorCode();
            if (throwables.getErrorCode() == 17002) { //l'he obtingut posant un sout en el throwables.getErrorCode()
                tipoError = 0;
            } else {
                tipoError = 1;  //error desconegut
            }
            throw new DAOException(tipoError);
        }


        return jefes;
    }


}
