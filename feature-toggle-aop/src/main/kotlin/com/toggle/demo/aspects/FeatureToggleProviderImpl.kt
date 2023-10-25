package com.toggle.demo.aspects

import com.toggle.demo.models.ToggleConfig
import com.toggle.demo.repositories.ToggleConfigRepository
import java.util.concurrent.atomic.AtomicReference
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FeatureToggleProviderImpl(
    private val toggleConfigRepository: ToggleConfigRepository
) : FeatureToggleProvider {
    private val toggleConfigMap: AtomicReference<Map<String, ToggleConfig>> = AtomicReference(emptyMap())
    override fun isEnabled(key: String, userId: Long?): Boolean {
        return runCatching {
            val toggleConfig = this.toggleConfigMap.get()[key] ?: return false

            if (toggleConfig.isPermittedUser(userId)) {
                return true
            }

            if (toggleConfig.isCanaryGroupedUser(userId)) {
                return true
            }

            return toggleConfig.enabled
        }.getOrDefault(false)
    }

    @Scheduled(fixedDelay = 5000)
    fun syncToggleConfiguration() {
        val newToggleConfigMap = toggleConfigRepository.findAll().associateBy { it.key }
        toggleConfigMap.set(newToggleConfigMap)
    }
}
