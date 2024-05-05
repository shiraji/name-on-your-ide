package com.github.shiraji.nameonyouride.activity

import com.github.shiraji.nameonyouride.listener.NOYIWindowFocusListener
import com.github.shiraji.nameonyouride.service.NOYIDisposableService
import com.github.shiraji.nameonyouride.service.NOYIUIService
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.util.Disposer

class NOYIActivity : StartupActivity {

    private lateinit var service: NOYIUIService
    private lateinit var listener: NOYIWindowFocusListener

    override fun runActivity(project: Project) {
        service = NOYIUIService(project)
        Disposer.register(NOYIDisposableService.getInstance(project), service)

        listener = NOYIWindowFocusListener(project, service)
        service.addWindowFocusListener(listener)
        Disposer.register(NOYIDisposableService.getInstance(project), listener)
    }
}