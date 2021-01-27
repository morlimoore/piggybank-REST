package com.morlimoore.piggybankapi.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
	private Long id;
	private String email;
	private String role;
	private String type = "Bearer";
	private String token;


	public JwtResponse(String token, Long id, String email, String role) {
		this.token = token;
		this.id = id;
		this.email = email;
		this.role = role;
	}
}
