package com.github.shiraji.nameonyouride.service

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class NOYIDisposableService : Disposable {
    companion object {
        fun getInstance(project: Project): NOYIDisposableService = project.getService(NOYIDisposableService::class.java)
    }

    override fun dispose() {
    }
}