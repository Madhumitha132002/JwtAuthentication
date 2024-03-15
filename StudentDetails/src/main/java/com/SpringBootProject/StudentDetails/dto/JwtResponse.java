package com.SpringBootProject.StudentDetails.dto;

public class JwtResponse {
    private String accessToken;
    private String token;

    public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	// Private constructor to prevent instantiation from outside
    private JwtResponse(String accessToken, String token) {
        this.accessToken = accessToken;
        this.token = token;
    }

    // Getters for the fields

    public JwtResponse() {
		// TODO Auto-generated constructor stub
	}


    // Builder class
    public static class Builder {
        private String accessToken;
        private String token;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(accessToken, token);
        }
    }

	
}
