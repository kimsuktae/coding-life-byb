package com.centraldogma.demo

import com.centraldogma.demo.annotations.FeatureToggle
import com.centraldogma.demo.annotations.FeatureToggleFallback
import org.springframework.stereotype.Service

@Service
class HelloService {
    @FeatureToggle(key = "NEW_HELLO", fallbackMethod = "oldHello")
    fun hello(userId: Long): String {
        return "new hello"
    }

    @FeatureToggleFallback
    fun oldHello(userId: Long): String {
        return "old hello"
    }
}
