package net.template.webapp.controller;

import net.template.server.user.model.UserDTO;
import net.template.server.user.service.UserService;
import net.template.webapp.ApiConstants;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ApiConstants.APP_REST_CONTEXT_PATH + "/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<UserDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "25") int limit) {
        return service.loadAll(page, limit);
    }

    @PostMapping("/save")
    public UserDTO save(@RequestBody UserDTO userDTO) {
        return service.save(userDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
