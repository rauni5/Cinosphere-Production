package com.cinosphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cinosphere.model.TheatreModel;
import com.cinosphere.utils.DBconfig;
/**
 * DAO class responsible for all theatre-related database operations.
 */
public class TheatreDAO {
	/**
	 * Retrieves theatre details using theatre ID.
	 *
	 * @param theatreId
	 * @return TheatreModel object if found, otherwise null
	 * @throws Exception if database operation fails
	 */
	public TheatreModel findById(int theatreId) throws Exception {

        TheatreModel theatre = null;
        Connection con = DBconfig.getConnection();
        String sql = "SELECT * FROM theatre WHERE theatre_id=?";
        PreparedStatement ps =  con.prepareStatement(sql);
        ps.setInt(1, theatreId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            theatre = createTheaterModel(rs);
        }
        rs.close();
        ps.close();
        con.close();

        return theatre;
    }

	/**
	 * Creates a TheatreModel object from ResultSet data.
	 *
	 * @param rs
	 * @return TheatreModel object
	 * @throws Exception if result set processing fails
	 */
	public TheatreModel createTheaterModel(ResultSet rs) throws Exception {	
        TheatreModel theatre = new TheatreModel();
        theatre.setTheatreId(rs.getInt("theatre_id"));
        theatre.setTheatreName(rs.getString("theatre_name"));
        theatre.setCity(rs.getString("city"));
        theatre.setContactNumber(rs.getString("contact_number"));
        theatre.setEmail(rs.getString("email"));
        theatre.setTheatreStatus(rs.getString("theatre_status"));
        theatre.setTotalScreens(rs.getInt("total_screens"));
       
        return theatre;
    }
}
