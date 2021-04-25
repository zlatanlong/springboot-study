package top.lclong.config.bean;

import lombok.Data;

/**
 * @Author: zlatanlong
 * @Date: 2021/4/19 19:29
 */
@Data
public class JWTProperties {

    private String header;

    private String tokenStartWith;

    private String base64Secret;

    private Long tokenValidityInSeconds;

    private Long tokenValidityInSecondsForRememberMe;
}
