package net.template.webapp.controller;

import net.template.server.permission.model.PermissionDto;
import net.template.server.permission.service.PermissionService;
import net.template.webapp.ApiConstants;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(ApiConstants.APP_REST_CONTEXT_PATH + "/permissions")
public class PermissionController {

    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }


    @GetMapping
    public Set<PermissionDto> findAll() {
        return service.findAll();
    }

    @PostMapping("/save")
    public PermissionDto save(@RequestBody PermissionDto dto) {
        return service.save(dto);
    }

    @PostMapping("/save/multi")
    public List<PermissionDto> saveAll(@RequestBody List<PermissionDto> dtos) {
        return service.saveAll(dtos);
    }

}
