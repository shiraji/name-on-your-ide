package com.github.shiraji.nameonyouride.listener

import com.github.shiraji.nameonyouride.service.NOYIUIService
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import java.util.*
import javax.swing.Timer

class NOYIWindowFocusListener(
        private val project: Project,
        private val service: NOYIUIService,
) : WindowFocusListener, Disposable {

    // I really hate this timer but IDE tool windows show front even though the label has the highest z-order.
    // If you open multiple windows with same JVM process, say IDE-A and IDE-B, if you focus IDEA-A, then IDEA-B's tool window will be shown front.
    // There is no listener to detect the tool window is shown front. So I need to keep repainting the label.
    private val visibleTimer = Timer(100) {
        println("${Date().time} - visibleTimer - ${project.name}")
        service.showLabel()
        (it.source as Timer).delay = 1000
    }

    override fun windowGainedFocus(p0: WindowEvent?) {
        println("windowGainedFocus - ${project.name}")
        visibleTimer.stop()
        service.hideLabel()
    }

    override fun windowLostFocus(p0: WindowEvent?) {
        println("windowLostFocus - ${project.name}")
        val opposite = p0?.oppositeWindow
        // opposite == null means the previous focused window is not an IDE window
        // opposite has the same ancestor with the current frame means the focus is still in the IDE (e.g. dialog, tool window, etc.)
        if (opposite == null || service.isInSameFrame(opposite)) {
            service.prepareLabel()
            visibleTimer.delay = 100
            visibleTimer.start()
        }
    }

    override fun dispose() {
        visibleTimer.stop()
    }
}