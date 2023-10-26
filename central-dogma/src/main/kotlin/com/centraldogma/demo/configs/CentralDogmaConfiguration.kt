package com.centraldogma.demo.configs

import com.centraldogma.demo.aspects.FeatureToggleAspect
import com.centraldogma.demo.aspects.FeatureToggleFallbackExecutor
import com.centraldogma.demo.featureToggleProviders.FeatureToggleProvider
import com.centraldogma.demo.featureToggleProviders.FeatureToggleProviderImpl
import com.linecorp.centraldogma.client.CentralDogma
import com.linecorp.centraldogma.client.armeria.ArmeriaCentralDogmaBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CentralDogmaConfiguration(
    @Value("\${centraldogma.project}")
    private val project: String,

    @Value("\${centraldogma.repository}")
    private val repository: String,

    @Value("\${centraldogma.filePath}")
    private val filePath: String,

    @Value("\${centraldogma.url}")
    private val url: String,

    @Value("\${centraldogma.port}")
    private val port: Int,
) {
    @Bean
    fun featureToggleAspect(): FeatureToggleAspect {
        return FeatureToggleAspect.Builder()
            .featureToggleProvider(featureToggleProvider())
            .fallbackExecutor(FeatureToggleFallbackExecutor())
            .build()
    }

    @Bean
    fun featureToggleProvider(): FeatureToggleProvider {
        return FeatureToggleProviderImpl.Builder()
            .centralDogma(centralDogma())
            .project(project)
            .repository(repository)
            .filePath(filePath)
            .build()
    }

    @Bean
    fun centralDogma(): CentralDogma {
        return ArmeriaCentralDogmaBuilder()
            .host(url, port)
            .build()
    }
}
