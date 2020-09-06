package com.inma.invest.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.inma.invest.config.SocialConfig;
import com.inma.invest.config.secuirty.CurrentUser;
import com.inma.invest.config.secuirty.JwtTokenProvider;
import com.inma.invest.config.secuirty.UserPrincipal;
import com.inma.invest.dto.LoginRq;
import com.inma.invest.dto.SignUpRq;
import com.inma.invest.dto.UserData;
import com.inma.invest.entity.Role;
import com.inma.invest.entity.RoleName;
import com.inma.invest.entity.User;
import com.inma.invest.exceptions.CustomException;
import com.inma.invest.exceptions.InvalidUsernamePasswordException;
import com.inma.invest.exceptions.ResourceAlreadyExistException;
import com.inma.invest.repository.RoleRepository;
import com.inma.invest.repository.UserRepository;
import com.inma.invest.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	SocialConfig socialConfig;

	private String getJwtFromRequest(String bearerToken) {
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public UserData getCurrentUser(@CurrentUser UserPrincipal currentUser,
			@RequestHeader(value = "Authorization") String authorization) {
		UserData userSummary = new UserData(getJwtFromRequest(authorization), currentUser);
		log.info("User name:{} , and ID :{}", userSummary.getProfile().getEmail(), userSummary.getProfile().getId());
		return userSummary;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRq loginRequest) {
		String jwt = null;
		UserPrincipal userPrincipal = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			userPrincipal = (UserPrincipal) authentication.getPrincipal();
			jwt = tokenProvider.generateToken(authentication);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InvalidUsernamePasswordException("user " + ex.getMessage(), "Email or Password",
					loginRequest.getUsername());
		}
		return ResponseEntity.ok(new UserData(jwt, userPrincipal));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRq signUpRequest, BindingResult bindResult) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new ResourceAlreadyExistException("Username", signUpRequest.getUsername());
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new ResourceAlreadyExistException("Email", signUpRequest.getEmail());
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new CustomException(Constant.INTERNAL_ERROR));

		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.ok(new UserData(tokenProvider.toToken(result), UserPrincipal.create(result)));
	}

}
