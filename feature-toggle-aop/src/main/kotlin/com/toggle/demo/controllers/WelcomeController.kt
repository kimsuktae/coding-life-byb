package com.toggle.demo.controllers

import com.toggle.demo.models.CanaryConfig
import com.toggle.demo.models.PermissionConfig
import com.toggle.demo.models.ToggleConfig
import com.toggle.demo.repositories.ToggleConfigRepository
import com.toggle.demo.services.WelcomeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomeController(
    private val welcomeService: WelcomeService,
    private val toggleConfigRepository: ToggleConfigRepository,
) {

    @GetMapping("/{userId}")
    fun hello(
        @PathVariable userId: Long
    ): String {
        return welcomeService.hello(userId)
    }

    @PostMapping("/configs")
    fun addConfig() : String {
        val newConfig = ToggleConfig(
            key = "NEW_HELLO",
            enabled = true,
            permission = PermissionConfig(
                enabled = false,
                userIds = listOf(99)
            ),
            canary = CanaryConfig(
                enabled = false,
                percentage = 0
            )
        )

        toggleConfigRepository.save(newConfig)
        return "ok"
    }
}
