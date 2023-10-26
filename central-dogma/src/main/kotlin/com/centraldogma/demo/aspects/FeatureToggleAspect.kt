package com.centraldogma.demo.aspects

import com.centraldogma.demo.annotations.FeatureToggle
import com.centraldogma.demo.featureToggleProviders.FeatureToggleProvider
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class FeatureToggleAspect private constructor(
    private val featureToggleProvider: FeatureToggleProvider,
    private val fallbackExecutor: FeatureToggleFallbackExecutor,
) {

    @Pointcut(value = "@within(featureToggle) || @annotation(featureToggle)", argNames = "featureToggle")
    fun matchAnnotatedClassOrMethod(featureToggle: FeatureToggle) {
    }

    @Around(
        value = "matchAnnotatedClassOrMethod(featureToggleAnnotation)",
        argNames = "proceedingJoinPoint, featureToggleAnnotation"
    )
    fun featureToggleAroundAdvice(
        proceedingJoinPoint: ProceedingJoinPoint,
        featureToggleAnnotation: FeatureToggle
    ): Any {
        val userId = proceedingJoinPoint.args.firstOrNull { it is Long } as? Long
        val key = featureToggleAnnotation.key
        val fallbackMethod = featureToggleAnnotation.fallbackMethod

        if (featureToggleProvider.isEnabled(key, userId)) {
            return proceedingJoinPoint.proceed()
        }

        return fallbackExecutor.execute(proceedingJoinPoint, fallbackMethod)
    }

    class Builder {
        private lateinit var featureToggleProvider: FeatureToggleProvider
        private lateinit var fallbackExecutor: FeatureToggleFallbackExecutor

        fun featureToggleProvider(featureToggleProvider: FeatureToggleProvider) =
            apply { this.featureToggleProvider = featureToggleProvider }
        fun fallbackExecutor(fallbackExecutor: FeatureToggleFallbackExecutor) =
            apply { this.fallbackExecutor = fallbackExecutor }
        fun build() = FeatureToggleAspect(featureToggleProvider, fallbackExecutor)
    }
}
