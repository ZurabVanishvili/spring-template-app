package net.template.webapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.template.server.user.model.UserDTO;
import net.template.server.user.service.UserService;
import net.template.webapp.ApiConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(ApiConstants.APP_REST_CONTEXT_PATH + "/public")
public class PublicController {

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDTO dto, HttpServletResponse response) throws IOException {
        UserDTO userDTO = userService.save(dto);
        if (userDTO != null) {
            response.sendRedirect("/login");
        }
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.logoutHandler.logout(request, response, authentication);
        response.sendRedirect("/login");
    }


}
