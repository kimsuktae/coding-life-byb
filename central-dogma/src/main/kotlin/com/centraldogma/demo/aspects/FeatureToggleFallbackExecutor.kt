package com.centraldogma.demo.aspects

import java.lang.reflect.Method
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature

class FeatureToggleFallbackExecutor {
    fun execute(proceedingJoinPoint: ProceedingJoinPoint, fallbackMethod: String): Any {
        val method = getMethod(proceedingJoinPoint, fallbackMethod)
        return method.invoke(proceedingJoinPoint.`this`, *proceedingJoinPoint.args)
    }

    private fun getMethod(proceedingJoinPoint: ProceedingJoinPoint, methodName: String): Method {
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        return proceedingJoinPoint.target::class.java.getMethod(methodName, *methodSignature.parameterTypes)
    }
}
