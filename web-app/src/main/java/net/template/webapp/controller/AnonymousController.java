package net.template.webapp.controller;


import net.template.server.config.model.AnonymousConfigurationModel;
import net.template.webapp.ApiConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.APP_REST_CONTEXT_PATH + "/anonymous")
public class AnonymousController {

    private final String authType;

    public AnonymousController(String authType) {
        this.authType = authType;
    }

    @GetMapping("/config")
    public AnonymousConfigurationModel loadConfiguration() {
        return new AnonymousConfigurationModel(authType);
    }
}
