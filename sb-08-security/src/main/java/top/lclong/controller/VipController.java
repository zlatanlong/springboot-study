package top.lclong.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 23:15
 */
@RestController
@RequestMapping("/vip")
public class VipController {


    @RequestMapping("/1")
    @PreAuthorize("hasAnyAuthority('vip1')")
    public String vip1(){
        return "vip1";
    }

    @RequestMapping("/r1")
    @PreAuthorize("hasRole('vip1')")
    public String vipr1(){
        return "vipr1";
    }

    @RequestMapping("/2")
    @PreAuthorize("hasRole('vip2')")
    public String vip2(){
        return "vipr2";
    }

    @RequestMapping("/3")
    @PreAuthorize("hasRole('vip3')")
    public String vip3(){
        return "vipr3";
    }

}
