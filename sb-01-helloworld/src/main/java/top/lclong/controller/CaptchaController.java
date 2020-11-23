package top.lclong.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/16 21:40
 */
@RestController
@RequestMapping("/cap")
public class CaptchaController {


    @RequestMapping(value = "get", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] cap(HttpSession session) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 1000);
        session.setAttribute("code", lineCaptcha.getCode());
        return lineCaptcha.getImageBytes();
    }

    @RequestMapping(value = "verify/{code}")
    public boolean verify(@PathVariable String code, HttpSession session) {

        return code.equals(session.getAttribute("code"));
    }
}
