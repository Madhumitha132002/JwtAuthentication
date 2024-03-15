package com.SpringBootProject.StudentDetails.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBootProject.StudentDetails.Model.StudentModel;
import com.SpringBootProject.StudentDetails.Repository.RefreshTokenRepository;
import com.SpringBootProject.StudentDetails.Repository.StudentDAOImpl;
import com.SpringBootProject.StudentDetails.model.RefreshToken;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private StudentDAOImpl studentrepo;

    public RefreshToken createRefreshToken(String username) {
        StudentModel studentModel = studentrepo.findByUsername(username); // Fetch StudentModel using username
        System.out.println(studentModel);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setStudentmodel(studentModel);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(600000)); // 10 minutes expiry

        return refreshTokenRepository.saveRefreshToken(refreshToken);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.deleteRefreshToken(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
