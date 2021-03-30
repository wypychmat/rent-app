package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ConfirmationTokenBuilderTest {


    @Test
    void shouldConfirmationTokenNeverBeTheSameWhenUserCredentialsAreTheSame() {
        String username = "username";
        String email = "email@email.com";
        int testPerUnit = 5000;
        int amountOfThreads = 10;

        List<CompletableFuture<Set<String>>>  result = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < amountOfThreads; i++) {
            result.add(getResult(username, email, testPerUnit));
        }

        Set<String> finalResult = new HashSet<>();
        result.forEach(tokens -> {
            try {
                finalResult.addAll(tokens.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        assertThat(finalResult.size()).isEqualTo(amountOfThreads * testPerUnit);

    }

    private CompletableFuture<Set<String>> getResult(String username, String email, int testPerUnit) {
        return CompletableFuture.supplyAsync(() -> {
            Set<String> tokens = new HashSet<>();
            for (int i = 0; i < testPerUnit; i++) {
                String token = ConfirmationTokenBuilder.build(username, email);
                tokens.add(token);
            }
            return tokens;
        });
    }

}