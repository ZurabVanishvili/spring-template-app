package net.template.server.auth.service;

import net.template.server.auth.model.AuthenticationRequest;
import net.template.server.auth.model.AuthenticationResponse;
import net.template.server.user.model.UserDTO;
import net.template.server.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthenticationResponse register(AuthenticationRequest request) {
        UserDTO dto = new UserDTO();
        dto.setLogin(request.getUsername());
        dto.setPassword(request.getPassword());

        try {
            dto = userService.save(dto);
        } catch (Exception e) {
            dto = null;
        }

        if (dto != null) {
            return authSuccessResponse(dto.getLogin());
        }
        return new AuthenticationResponse(null);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            return authSuccessResponse(request.getUsername());
        }

        return new AuthenticationResponse(null);
    }

    private AuthenticationResponse authSuccessResponse(String login) {
        return new AuthenticationResponse(jwtService.generateToken(login));
    }
}
