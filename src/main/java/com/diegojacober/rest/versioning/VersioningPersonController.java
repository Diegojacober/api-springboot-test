package com.diegojacober.rest.versioning;

import org.springframework.web.bind.annotation.GetMapping;

public class VersioningPersonController {
    
    @GetMapping("/v1/persons")
    public String personV1(){
        return "V1";
    }

    @GetMapping("/v2/persons")
    public String personV2(){
        return "V2";
    }
}
