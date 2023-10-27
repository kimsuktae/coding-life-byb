package com.centraldogma.demo.featureToggleProviders

import com.centraldogma.demo.models.ToggleConfig
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.linecorp.centraldogma.client.CentralDogma
import com.linecorp.centraldogma.common.Change
import com.linecorp.centraldogma.common.Query
import com.linecorp.centraldogma.common.Revision
import jakarta.annotation.PostConstruct
import java.util.concurrent.atomic.AtomicReference

class FeatureToggleProviderImpl private constructor(
    private val centralDogma: CentralDogma,
    private val project: String,
    private val repository: String,
    private val filePath: String,
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

    @PostConstruct
    fun init() {
        createRepositoryIfNotExists()
        watchFile()
    }

    private fun watchFile() {
        centralDogma.forRepo(project, repository)
            .watcher(Query.ofJson(filePath))
            .start()
            .watch { _, value ->
                val configs = value.fieldNames().asSequence().associateWith {
                    jacksonObjectMapper().convertValue(value[it], ToggleConfig::class.java)
                }

                toggleConfigMap.set(configs)
            }
    }

    private fun createRepositoryIfNotExists() {
        try {
            centralDogma.forRepo(project, repository)
                .file(Query.ofJson(filePath))
                .get(Revision.HEAD)
                .get()
        } catch (exception: Exception) {
            centralDogma.createRepository(project, repository).also {
                val defaultToggle = ToggleConfig.default()

                centralDogma.forRepo(project, repository)
                    .commit(
                        "First Commit", Change.ofJsonUpsert(
                            filePath,
                            jacksonObjectMapper().writeValueAsString(
                                mapOf(defaultToggle.key to defaultToggle)
                            )
                        )
                    )
                    .push(Revision.HEAD)
            }
        }
    }

    class Builder {
        private lateinit var centralDogma: CentralDogma
        private lateinit var project: String
        private lateinit var repository: String
        private lateinit var filePath: String

        fun centralDogma(centralDogma: CentralDogma) = apply { this.centralDogma = centralDogma }
        fun project(project: String) = apply { this.project = project }
        fun repository(repository: String) = apply { this.repository = repository }
        fun filePath(filePath: String) = apply { this.filePath = filePath }
        fun build() = FeatureToggleProviderImpl(centralDogma, project, repository, filePath)
    }
}
