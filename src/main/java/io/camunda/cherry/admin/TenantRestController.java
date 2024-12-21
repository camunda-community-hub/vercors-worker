package io.camunda.cherry.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TenantRestController {
    @GetMapping(value = "/api/tenants/list", produces = "application/json")
    public Map<String, Object> getOperation() {
        Map<String, Object> info = new HashMap<>();
        return info;
    }
}
