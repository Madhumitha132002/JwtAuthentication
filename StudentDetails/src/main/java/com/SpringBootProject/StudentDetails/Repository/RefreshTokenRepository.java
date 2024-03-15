package com.SpringBootProject.StudentDetails.Repository;



import com.SpringBootProject.StudentDetails.model.RefreshToken;

import java.sql.Timestamp;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    public RefreshTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        String insertQuery = "INSERT INTO refresh_token (token, expiry_date, student_id) VALUES (?, ?, ?)";
        Timestamp expiryTimestamp = Timestamp.from(refreshToken.getExpiryDate()); // Convert Instant to Timestamp
        jdbcTemplate.update(insertQuery, refreshToken.getToken(), expiryTimestamp, refreshToken.getId());
        return refreshToken;
    }


    public RefreshToken findByToken(String token) {
        String selectQuery = "SELECT * FROM refresh_token WHERE token = ?";
        try {
        	RefreshToken refreshToken = jdbcTemplate.queryForObject(selectQuery, new BeanPropertyRowMapper<>(RefreshToken.class), token);
            System.out.println("heeelo"+refreshToken); // Print the result
            return refreshToken;
        } catch (EmptyResultDataAccessException e) {
            // If no data found, return null or handle differently based on your requirement
            return null;
        } 
    }

    public void deleteRefreshToken(RefreshToken token) {
        String deleteQuery = "DELETE FROM refresh_token WHERE token = ?";
        jdbcTemplate.update(deleteQuery, token.getToken());
    }
}
