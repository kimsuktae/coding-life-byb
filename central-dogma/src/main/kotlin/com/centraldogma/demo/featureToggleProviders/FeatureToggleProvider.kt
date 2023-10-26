package com.centraldogma.demo.featureToggleProviders

interface FeatureToggleProvider {
    fun isEnabled(key: String, userId: Long?): Boolean
}
