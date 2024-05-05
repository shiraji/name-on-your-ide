package com.github.shiraji.nameonyouride.enums

import javax.swing.SwingConstants

enum class ValidHorizontalAlignments(val value: Int) {
    LEFT(SwingConstants.LEFT),
    CENTER(SwingConstants.CENTER),
    RIGHT(SwingConstants.RIGHT),
    LEADING(SwingConstants.LEADING),
    TRAILING(SwingConstants.TRAILING),
}