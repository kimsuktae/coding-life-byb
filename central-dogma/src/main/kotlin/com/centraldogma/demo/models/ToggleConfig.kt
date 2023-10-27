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

    companion object {
        fun default(): ToggleConfig {
            return ToggleConfig(
                key = ToggleKey.DEFAULT.name,
                enabled = true,
                permission = PermissionConfig.default(),
                canary = CanaryConfig.default(),
            )
        }
    }
}

class PermissionConfig(
    val enabled: Boolean,
    val userIds: List<Long>,
) {
    fun checkUserIsPermitted(userId: Long?): Boolean {
        return if (enabled) userIds.contains(userId) else false
    }

    companion object {
        fun default(): PermissionConfig {
            return PermissionConfig(
                enabled = false,
                userIds = emptyList(),
            )
        }
    }
}

class CanaryConfig(
    val enabled: Boolean,
    val percentage: Int,
) {
    fun checkUserIsCanaryGrouped(userId: Long?): Boolean {
        if (userId == null) {
            return false
        }

        return if (enabled) userId % 100 > percentage else false
    }

    companion object {
        fun default(): CanaryConfig {
            return CanaryConfig(
                enabled = false,
                percentage = 0,
            )
        }
    }
}

enum class ToggleKey {
    DEFAULT,
}
