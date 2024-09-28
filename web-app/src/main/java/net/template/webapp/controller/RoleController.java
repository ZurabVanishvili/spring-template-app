package net.template.webapp.controller;

import net.template.server.role.model.RoleDTO;
import net.template.server.role.serivce.RoleService;
import net.template.webapp.ApiConstants;
import org.hibernate.annotations.Parameter;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.APP_REST_CONTEXT_PATH + "/role")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }


    @GetMapping
    public List<RoleDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RoleDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping("/save")
    public RoleDTO save(@RequestBody RoleDTO roleDTO) {
        return service.save(roleDTO);
    }

}
