/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohdah.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohdah.model.Ohdah;
import ohdah.utils.DBConnection;

/**
 *
 * @author Omar
 */
public class cOhdah {

    Connection conn;

    /**
     *
     * @throws InstantiationException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public cOhdah() throws InstantiationException, SQLException, ClassNotFoundException, IllegalAccessException {

        conn = DBConnection.get();

    }

    /**
     *
     * @return
     */
    public List<Ohdah> getAll() {
        return null;
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Optional<Ohdah> get(int id) throws SQLException {
        Ohdah ohdah = null;
        String query = "SELECT * FROM ohdah WHERE id=?";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        if (rs.isBeforeFirst()) {
            ohdah = new Ohdah();
            while (rs.next()) {
                ohdah.setId(rs.getInt("id"));
                ohdah.setName(rs.getString("name"));
                ohdah.setAmount(rs.getDouble("amount"));
                ohdah.setReason(rs.getString("reason"));
                ohdah.setDate(LocalDate.parse(rs.getString("ohdahDate")));
            }
        }
        return Optional.ofNullable(ohdah);
    }

    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public Optional<Ohdah> get(String name) throws SQLException {
        Ohdah ohdah = null;
        String query = "SELECT * FROM ohdah WHERE name LIKE ?";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, name);
        ResultSet rs = prep.executeQuery();
        if (rs.isBeforeFirst()) {
            ohdah = new Ohdah();
            while (rs.next()) {
                ohdah.setId(rs.getInt("id"));
                ohdah.setName(rs.getString("name"));
                ohdah.setAmount(rs.getDouble("amount"));
                ohdah.setReason(rs.getString("reason"));
                ohdah.setDate(LocalDate.parse(rs.getString("ohdahDate")));
            }
        }

        return Optional.ofNullable(ohdah);
    }

    /**
     * Add new Ohdah row to the db.
     *
     * @param ohdah to added
     * @return the generated id of inserted ohdah.
     */
    public int Add(Ohdah ohdah) {
        String query = "INSERT INTO ohdah (name, amount, reason, ohdahDate)"
                + " VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement prep = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, ohdah.getName());
            prep.setDouble(2, ohdah.getAmount());
            prep.setString(3, ohdah.getReason());
            prep.setDate(4, new Date(ohdah.getDate().toEpochDay()));
            int res = prep.executeUpdate();
            if (res > 0) {
                return prep.getGeneratedKeys().getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(cOhdah.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    /**
     *
     * @param ohdah
     * @param id
     * @return
     * @throws SQLException
     */
    public int update(Ohdah ohdah, int id) throws SQLException {
        String query = "UPDATE ohdah SET name=?, amount=?, reason=?, ohdahDate=? "
                + "WHERE id=?";

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, ohdah.getName());
        prep.setDouble(2, ohdah.getAmount());
        prep.setString(3, ohdah.getReason());
        prep.setDate(4, new Date(ohdah.getDate().toEpochDay()));
        prep.setInt(5, id);
        
        int res = prep.executeUpdate();

        return res;
    }
}
