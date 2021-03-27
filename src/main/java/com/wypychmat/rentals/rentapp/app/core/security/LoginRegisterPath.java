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

    @Value("${api.path.register}")
    private String registerPath;

    @Value("${api.path.confirm}")
    private String confirm;


    @Value("${api.param.register.token}")
    private String token;

    @Value("${api.version.newest}")
    private String currentVersion;


    public String getMatcherLoginPath() {
        return getPath(baseUrl + loginPath);
    }

    public String getMatcherRegisterPath() {
        return getPath(baseUrl + registerPath);
    }

    public String getConfirm() {
        return confirm;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getUriWithTokenParam() {
        return baseUrl + registerPath + confirm + token + "=";
    }

    public String getRegexForConfirmPath() {
        return "\\/" + removeSlashes(baseUrl) + "\\/" + removeSlashes(registerPath) + "\\/" + prefix + "\\d*\\/" +
                confirm + "\\?" + token + "\\=.*";
    }

    public String getConfirmPath() {
        return baseUrl + registerPath + currentVersion +"/" + confirm + "?" + token + "=";
    }

    private String removeSlashes(String toReplace) {
        return toReplace.replaceAll("/", "");
    }


    private String getPath(String basePath) {
        return basePath + prefix + "{\\d*}";
    }
}
