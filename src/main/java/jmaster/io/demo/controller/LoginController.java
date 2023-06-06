package jmaster.io.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.service.JwtTokenService;

@RestController
public class LoginController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenService jwtTokenService;
//	
//	@GetMapping("/login")
//	public String login() {
//		//Map url vào 1 hàm, trả về tên file view
//		return "login.html";
//	}
//	
	@PostMapping("/login")
	public ResponseDTO<String> login(
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		//authen fail throw exception above
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		List<String> authorities = authentication.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList());
		
		return ResponseDTO.<String>builder().status(200).data(jwtTokenService.createToken(username
				, authorities
				)).build();
	}
}
