package com.scheduler.demo.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SecondScheduler {

    @Scheduled(fixedRate = 2000)
    fun second() {
        println("hello from second")
    }
}
