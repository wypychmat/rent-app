package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
class ScheduledService {
    private final DropUnregisteredService dropUnregisteredService;

    ScheduledService(DropUnregisteredService dropUnregisteredService) {
        this.dropUnregisteredService = dropUnregisteredService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Warsaw")
    public void dropUnregisteredAtMidnight() {
        dropUnregisteredService.drop();
    }
}
