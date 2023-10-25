package com.toggle.demo.aspects

interface FeatureToggleProvider {
    fun isEnabled(key: String, userId: Long?): Boolean
}
