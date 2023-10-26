package com.centraldogma.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController(
    private val helloService: HelloService
) {

    @GetMapping("/{userId}")
    fun hello(
        @PathVariable userId: Long
    ): String {
        return helloService.hello(userId)
    }
}
