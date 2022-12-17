package com.yologger.h2h.chatserver.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ProfileController constructor(
    @Autowired val env: Environment
) {
    @GetMapping("/profile")
    fun profile(): String {
        val profiles = env.activeProfiles.toList()
        if (profiles.contains("alpha1")) {
            return "alpha1"
        } else if (profiles.contains("alpha2")) {
            return "alpha2"
        } else {
            return "default";
        }
    }
}