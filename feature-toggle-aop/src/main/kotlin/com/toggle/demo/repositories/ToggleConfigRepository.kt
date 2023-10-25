package com.toggle.demo.repositories

import com.toggle.demo.models.ToggleConfig
import org.springframework.data.jpa.repository.JpaRepository

interface ToggleConfigRepository : JpaRepository<ToggleConfig,Long>
