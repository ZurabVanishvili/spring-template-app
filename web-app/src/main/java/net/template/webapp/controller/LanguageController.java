package net.template.webapp.controller;

import net.template.server.language.model.LanguageDTO;
import net.template.server.language.service.LanguageService;
import net.template.webapp.ApiConstants;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ApiConstants.APP_REST_CONTEXT_PATH + "/languages")
public class LanguageController {

    private final LanguageService service;

    public LanguageController(LanguageService service) {
        this.service = service;
    }

    @GetMapping
    public List<LanguageDTO> loadAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public LanguageDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping(value = "/save")
    public LanguageDTO save(@RequestBody LanguageDTO dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
