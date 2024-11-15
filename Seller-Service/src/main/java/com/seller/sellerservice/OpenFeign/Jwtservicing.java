package com.seller.sellerservice.OpenFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@FeignClient(name="VIRTUALBOOKSTORE",url="http://localhost:8080/user")
public interface Jwtservicing {
    @PostMapping("/verify")
    String verification(@RequestParam String jwt);



}

