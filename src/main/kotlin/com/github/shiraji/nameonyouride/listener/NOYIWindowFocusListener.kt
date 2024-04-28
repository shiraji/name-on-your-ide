package com.github.shiraji.nameonyouride.listener

import com.intellij.openapi.project.Project
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import javax.swing.JFrame

class NOYIWindowFocusListener(private val project: Project, private val frame: JFrame) : WindowFocusListener {
    override fun windowGainedFocus(p0: WindowEvent?) {
        TODO("Not yet implemented")
    }

    override fun windowLostFocus(p0: WindowEvent?) {
        TODO("Not yet implemented")
    }
}