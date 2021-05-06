package top.lclong.config.bean;

import lombok.Data;

/**
 * @Author: zlatanlong
 * @Date: 2021/4/19 19:29
 * 秒与毫秒在这里转化
 */
@Data
public class JWTProperties {

    private String header;

    private String tokenStartWith;

    private String base64Secret;

    private Long tokenValidityInSeconds;

    private Long tokenValidityInSecondsForRememberMe;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenStartWith() {
        return tokenStartWith;
    }

    public void setTokenStartWith(String tokenStartWith) {
        this.tokenStartWith = tokenStartWith;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public Long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    public void setTokenValidityInSeconds(Long tokenValidityInSeconds) {
        this.tokenValidityInSeconds = tokenValidityInSeconds * 1000;
    }

    public Long getTokenValidityInSecondsForRememberMe() {
        return tokenValidityInSecondsForRememberMe;
    }

    public void setTokenValidityInSecondsForRememberMe(Long tokenValidityInSecondsForRememberMe) {
        this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe * 1000;
    }
}
