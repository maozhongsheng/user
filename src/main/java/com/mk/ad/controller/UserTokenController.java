package com.mk.ad.controller;

import com.mk.ad.utils.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
public class UserTokenController {
    @PostMapping("/userIdByToken")
    public String getUserId(@RequestBody String  request) {
      //  String id= JwtTokenUtil.getUserId(request);
        String vd = JWTHelper.vd(request);
        return vd;
    }
}
