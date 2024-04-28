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
import javax.swing.*

class NOYIActivity : StartupActivity {
    override fun runActivity(project: Project) {
        val frame = WindowManager.getInstance().getFrame(project)
        val parent = frame?.rootPane
        val rootPane: JRootPane = UIUtil.findComponentOfType(parent, JRootPane::class.java) ?: return

        // add background color
//        val label = object : JLabel(project.name) {
//            override fun paintComponent(g: Graphics) {
//                g.color = background
//                g.fillRect(0, 0, width, height)
//                super.paintComponent(g)
//            }
//
//            override fun isOpaque(): Boolean {
//                return false
//            }
//        }
        val label = JLabel(project.name)
        rootPane.add(label, BorderLayout.CENTER, 0)
        label.isVisible = false
        label.setSize(rootPane.size.width, rootPane.size.height)
        label.foreground = JBColor.RED
        label.font = Font("Arial", Font.PLAIN, 100)
        label.horizontalAlignment = SwingConstants.RIGHT
        label.verticalAlignment = SwingConstants.TOP

        if (frame?.isFocused == true) {
            label.isVisible = false
        } else {
            label.isVisible = true
        }

        frame?.addWindowFocusListener(object : WindowFocusListener {
            val visibleTimer = Timer(300) {
                println("visible timer")
                label.isVisible = true
                label.repaint()
                (it.source as? Timer)?.stop()
            }
            val nonVisibleTimer = Timer(300) {
                println("non visible timer")
                label.isVisible = false
                label.repaint()
                (it.source as? Timer)?.stop()
            }

            override fun windowGainedFocus(p0: WindowEvent?) {
                println("windowGainedFocus ${p0?.paramString()}")
                label.isVisible = false
                visibleTimer.stop()
                nonVisibleTimer.start()
            }

            override fun windowLostFocus(p0: WindowEvent?) {
                println("windowLostFocus ${p0?.paramString()}")

                val opposite = p0?.oppositeWindow
                if (opposite == null || SwingUtilities.getAncestorOfClass(IdeFrame::class.java, opposite) != frame) {
                    label.setSize(rootPane.size.width, rootPane.size.height)
                    label.isVisible = true
                    visibleTimer.start()
                    nonVisibleTimer.stop()
                }
            }
        })
    }
}