package com.wypychmat.rentals.rentapp.app.core.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;


class KeyProvider {
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    KeyProvider(RsaKeyConfig rsaKeyConfig) {
        publicKey = keyGenerator(this::generatePublic,
                (data) -> new X509EncodedKeySpec(decode(data)),
                rsaKeyConfig.getPublicPath(), rsaKeyConfig.getPublicHeader());
        privateKey = keyGenerator(this::generatePrivate,
                (data) -> new PKCS8EncodedKeySpec(decode(data)),
                rsaKeyConfig.getPrivatePath(), rsaKeyConfig.getPrivateHeader());
    }


    RSAPublicKey getPublicKey() {
        return publicKey;
    }

    RSAPrivateKey getPrivateKey() {
        return privateKey;
    }


    private <T extends Key> T keyGenerator(
            Function<EncodedKeySpec, T> keyGeneratorFunction,
            Function<String, EncodedKeySpec> encodedKeySpecFunction,
            String filename,
            String header) {
        Optional<String> data = getDataFromFile(filename, header);
        if (data.isPresent()) {
            return keyGeneratorFunction.apply(encodedKeySpecFunction.apply(data.get()));
        }
        throw new RuntimeException("ERROR WHEN READING DATA");
    }


    private RSAPublicKey generatePublic(EncodedKeySpec spec) {
        try {
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during generate public key", e);
        }
    }

    private RSAPrivateKey generatePrivate(EncodedKeySpec spec) {
        try {
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during generate private key", e);
        }
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }


    private Optional<String> getDataFromFile(String fileName, String headerType) {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (resourceAsStream != null) {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))) {
                String readLine;
                while ((readLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(readLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
            return Optional.of(stringBuilder.toString()
                    .replace("-----BEGIN " + headerType + " KEY-----", "")
                    .replace("-----END " + headerType + " KEY-----", ""));
        }
        return Optional.empty();
    }
}