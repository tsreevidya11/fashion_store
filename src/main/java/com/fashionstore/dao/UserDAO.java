package com.fashionstore.dao;

import com.fashionstore.model.User;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

public boolean registerUser(User user) {

    String sql =
            "INSERT INTO users " +
            "(full_name,email,phone,password,address,city,state,pincode) " +
            "VALUES(?,?,?,?,?,?,?,?)";

    try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        ps.setString(1, user.getFullName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getAddress());
        ps.setString(6, user.getCity());
        ps.setString(7, user.getState());
        ps.setString(8, user.getPincode());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

public User loginUser(
        String email,
        String password) {

    String sql =
            "SELECT * FROM users " +
            "WHERE email=? AND password=?";

    try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs =
                ps.executeQuery();

        if (rs.next()) {

            User user =
                    new User();

            user.setUserId(
                    rs.getInt("user_id"));

            user.setFullName(
                    rs.getString("full_name"));

            user.setEmail(
                    rs.getString("email"));

            user.setPhone(
                    rs.getString("phone"));

            user.setAddress(
                    rs.getString("address"));

            user.setCity(
                    rs.getString("city"));

            user.setState(
                    rs.getString("state"));

            user.setPincode(
                    rs.getString("pincode"));

            return user;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

}
