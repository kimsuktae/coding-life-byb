package com.featuretoggle.demo

import org.togglz.core.annotation.EnabledByDefault
import org.togglz.core.annotation.Label
import org.togglz.core.context.FeatureContext

enum class KotlinTestFeatures {
    @EnabledByDefault
    HELLO_WORLD,

    @Label("Old World")
    OLD_WORLD;
    fun isActive(): Boolean {
        return FeatureContext.getFeatureManager().isActive { name }
    }
}
