package com.toggle.demo.services

import com.toggle.demo.annotations.FeatureToggle
import com.toggle.demo.annotations.FeatureToggleFallback
import org.springframework.stereotype.Service

@Service
class WelcomeService {
    @FeatureToggle(key = "NEW_HELLO", fallbackMethod = "oldHello")
    fun hello(userId: Long): String {
        return "new hello"
    }

    @FeatureToggleFallback
    fun oldHello(userId: Long): String {
        return "old hello"
    }
}
