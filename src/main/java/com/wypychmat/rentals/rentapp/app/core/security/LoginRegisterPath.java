package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoginRegisterPath {
    @Value("${api.base}")
    private String baseUrl;

    @Value("${api.version.prefix}")
    private String prefix;

    @Value("${api.path.login}")
    private String loginPath;

    @Value("${api.path.register.base}")
    private String registerPath;

    @Value("${api.path.register.confirm}")
    private String confirm;

    @Value("${api.path.register.refresh}")
    private String refresh;


    @Value("${api.param.register.token}")
    private String token;


    public String getMatcherLoginPath() {
        return baseUrl + loginPath + "/" + prefix + "{\\d*}";
    }

    public String getMatcherRegisterPath() {
        return baseUrl + registerPath;
    }

    public String getMatcherRegisterRefreshPath() {
        return baseUrl + registerPath + "/" + refresh;
    }

    public String getConfirm() {
        return confirm;
    }

    public String getPrefix() {
        return prefix;
    }


    public String getUriWithTokenParam() {
        return baseUrl + registerPath + confirm + token + "=";
    }

    public String getRegexForConfirmPath() {
        return "\\/" + removeSlashes(baseUrl) + "\\/" + removeSlashes(registerPath) + "\\/" + prefix + "\\d*\\/" +
                confirm + "\\?" + token + "\\=.*";
    }

    public String getConfirmPathV1() {
        return getConfirmPathByVersion("/v1/");
    }

    public String getConfirmPathV2() {
        return getConfirmPathByVersion("/v2/");
    }

    private String getConfirmPathByVersion(String version) {
        return baseUrl + registerPath + version + confirm + "?" + token + "=";
    }

    private String removeSlashes(String toReplace) {
        return toReplace.replaceAll("/", "");
    }


}
