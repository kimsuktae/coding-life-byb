package com.toggle.demo.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureToggle(
    val key: String,
    val fallbackMethod: String = "",
)
