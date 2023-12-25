package com.scheduler.demo.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FirstScheduler {

    @Scheduled(fixedRate = 2000)
    fun first() {
        var b = 0

        for (a: Long in 0 .. 10000000000) {
            b += 1
        }

        println("hello from first")
    }
}
