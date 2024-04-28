package com.github.shiraji.nameonyouride.activity

import com.github.shiraji.nameonyouride.listener.NOYIWindowFocusListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.wm.IdeFrame
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.Font
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import javax.swing.JLabel
import javax.swing.JRootPane
import javax.swing.SwingUtilities

class NOYIActivity : StartupActivity {
    override fun runActivity(project: Project) {
        val frame = WindowManager.getInstance().getFrame(project)
        frame?.addWindowFocusListener(NOYIWindowFocusListener(project, frame))


        val parent = frame?.rootPane
        val rootPane: JRootPane = UIUtil.findComponentOfType(parent, JRootPane::class.java) ?: return

        val label = JLabel(project.name)
        rootPane.add(label, BorderLayout.CENTER, 1)
        label.isVisible = false
        label.setSize(rootPane.size.width, rootPane.size.height)
        label.foreground = JBColor.RED
        label.font = Font("Arial", Font.PLAIN, 100)
        label.horizontalAlignment = JLabel.CENTER

        // in case the frame is never focused
        label.isVisible = frame?.isFocused != true

        frame?.addWindowFocusListener(object : WindowFocusListener {
            override fun windowGainedFocus(p0: WindowEvent?) {
                label.isVisible = false
            }

            override fun windowLostFocus(p0: WindowEvent?) {
                val opposite = p0?.oppositeWindow
                if (opposite == null || SwingUtilities.getAncestorOfClass(IdeFrame::class.java, opposite) != frame) {
                    label.isVisible = true
                    label.setSize(rootPane.size.width, rootPane.size.height)
                }
            }
        })
    }
}