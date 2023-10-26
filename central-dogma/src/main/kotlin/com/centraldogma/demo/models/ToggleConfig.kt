package com.centraldogma.demo.models

class ToggleConfig(
    val key: String,
    val enabled: Boolean = false,
    val permission: PermissionConfig,
    val canary: CanaryConfig,
) {
    fun isPermittedUser(userId: Long?): Boolean {
        return permission.checkUserIsPermitted(userId)
    }

    fun isCanaryGroupedUser(userId: Long?): Boolean {
        return canary.checkUserIsCanaryGrouped(userId)
    }
}

class PermissionConfig(
    private val enabled: Boolean,
    private val userIds: List<Long>,
) {
    fun checkUserIsPermitted(userId: Long?): Boolean {
        return if (enabled) userIds.contains(userId) else false
    }
}

class CanaryConfig(
    private val enabled: Boolean,
    private val percentage: Int,
) {
    fun checkUserIsCanaryGrouped(userId: Long?): Boolean {
        if (userId == null) {
            return false
        }

        return if (enabled) userId % 100 > percentage else false
    }
}
