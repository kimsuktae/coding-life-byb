package com.toggle.demo.models

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class ToggleConfig(
    @Column(name = "toggle_key")
    val key: String,

    @Column(name = "toggle_enabled")
    val enabled: Boolean = false,

    @Embedded
    val permission: PermissionConfig,

    @Embedded
    val canary: CanaryConfig,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    fun isPermittedUser(userId: Long?): Boolean {
        return permission.checkUserIsPermitted(userId)
    }

    fun isCanaryGroupedUser(userId: Long?): Boolean {
        return canary.checkUserIsCanaryGrouped(userId)
    }
}

@Embeddable
class PermissionConfig(
    @Column(name = "permission_enabled")
    val enabled: Boolean,

    @ElementCollection(fetch = FetchType.EAGER)
    val userIds: List<Long>,
) {
    fun checkUserIsPermitted(userId: Long?): Boolean {
        return if (enabled) userIds.contains(userId) else false
    }
}

@Embeddable
class CanaryConfig(
    @Column(name = "canary_enabled")
    val enabled: Boolean,
    val percentage: Int,
) {
    fun checkUserIsCanaryGrouped(userId: Long?): Boolean {
        if (userId == null) {
            return false
        }

        return if (enabled) userId % 100 > percentage else false
    }
}
