package com.files.buyerservice.OpenFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="VIRTUALBOOKSTORE",url="http://VIRTUALBOOKSTORE:8080/user")
public interface Jwtservicing {
    @PostMapping("/verify")
    Map<String,String> verification(@RequestParam String jwt);

   public default String verify(String jwt){
        Map m=verification(jwt);
        String s= (String) m.get("jwt");
        return s;
    }


}

