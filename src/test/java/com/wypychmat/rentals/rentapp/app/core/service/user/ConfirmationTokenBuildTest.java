package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ConfirmationTokenBuildTest {


    @Test
    void shouldConfirmationTokenNeverBeTheSameWhenUserCredentialsAreTheSame() throws InterruptedException, ExecutionException {
        //given
        String username = "username";
        String email = "email@email.com";
        int amountOfThreads = 5000;
        ExecutorService executorService = Executors.newFixedThreadPool(amountOfThreads);
        Set<String> finalResult = new HashSet<>();
        List<Callable<String>> threads = new ArrayList<>();

        //when
        for (int i = 0; i < amountOfThreads; i++)
            threads.add(() -> ConfirmationTokenBuilder.build(username, email));
        List<Future<String>> futures = executorService.invokeAll(threads);

        for (Future<String> stringFuture : futures) {
            finalResult.add(stringFuture.get());
        }
        executorService.shutdown();

        //then
        assertThat(finalResult.size()).isEqualTo(amountOfThreads);
    }


}