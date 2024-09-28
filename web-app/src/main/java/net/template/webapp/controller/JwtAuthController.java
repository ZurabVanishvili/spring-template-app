package net.template.webapp.controller;

import net.template.server.auth.model.AuthenticationRequest;
import net.template.server.auth.model.AuthenticationResponse;
import net.template.server.auth.service.AuthenticationService;
import net.template.webapp.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiConstants.APP_REST_CONTEXT_PATH + "/jwt")
public class JwtAuthController {

    private final AuthenticationService authenticationService;

    public JwtAuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
