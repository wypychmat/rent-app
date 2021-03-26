package com.wypychmat.rentals.rentapp.app.core.service.user;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class ConfirmationTokenBuilder {

    public static String build(String username, String email) {
        String token = username +
                System.currentTimeMillis() +
                email +
                getRandomBaseASCII();
        return Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

    private static String getRandomBaseASCII() {
        Random rnd = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int j = rnd.nextInt(3);
            switch (j) {
                case 0:
                    stringBuilder.append((char) (rnd.nextInt(10) + 48));
                    break;
                case 1:
                    stringBuilder.append((char) (rnd.nextInt(25) + 65));
                    break;
                case 2:
                    stringBuilder.append((char) (rnd.nextInt(25) + 97));
                    break;
            }
        }
        return stringBuilder.toString();
    }

}
