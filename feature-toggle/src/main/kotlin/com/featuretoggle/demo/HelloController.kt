package com.featuretoggle.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HelloController {

    @GetMapping
    fun hello(): String {
        if (KotlinTestFeatures.HELLO_WORLD.isActive()) {
            return "New Hello"
        }

        return "Old Hello"
    }
}
