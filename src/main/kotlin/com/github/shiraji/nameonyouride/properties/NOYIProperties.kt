package com.github.shiraji.nameonyouride.properties

import com.github.shiraji.nameonyouride.enums.ValidForegroundColors
import com.github.shiraji.nameonyouride.enums.ValidHorizontalAlignments
import com.github.shiraji.nameonyouride.enums.ValidVerticalAlignments
import com.intellij.ide.util.PropertiesComponent
import java.awt.Color
import javax.swing.JLabel

private const val DISABLE = "com.github.shiraji.nameonyouride.properties.NOYIProperties.DISABLE"
private const val FONT_SIZE = "com.github.shiraji.nameonyouride.properties.NOYIProperties.FONT_SIZE"
private const val HORIZONTAL_ALIGNMENT = "com.github.shiraji.nameonyouride.properties.NOYIProperties.HORIZONTAL_ALIGNMENT"
private const val VERTICAL_ALIGNMENT = "com.github.shiraji.nameonyouride.properties.NOYIProperties.VERTICAL_ALIGNMENT"
private const val TEXT = "com.github.shiraji.nameonyouride.properties.NOYIProperties.TEXT"
private const val FOREGROUND_COLOR = "com.github.shiraji.nameonyouride.properties.NOYIProperties.FOREGROUND_COLOR"

const val DEFAULT_IS_DISABLE = false
const val DEFAULT_FONT_SIZE = 100
val DEFAULT_HORIZONTAL_ALIGNMENT = ValidHorizontalAlignments.CENTER
val DEFAULT_VERTICAL_ALIGNMENT = ValidVerticalAlignments.CENTER
val DEFAULT_FOREGROUND_COLOR = ValidForegroundColors.RED

fun PropertiesComponent.isDisable() = getBoolean(DISABLE, DEFAULT_IS_DISABLE)
fun PropertiesComponent.setDisable(value: Boolean) = setValue(DISABLE, value, DEFAULT_IS_DISABLE)

fun PropertiesComponent.fontSize() = getInt(FONT_SIZE, DEFAULT_FONT_SIZE)
fun PropertiesComponent.setFontSize(value: Int) = setValue(FONT_SIZE, value, DEFAULT_FONT_SIZE)

fun PropertiesComponent.text(): String = getValue(TEXT) ?: ""
fun PropertiesComponent.setText(value: String) = setValue(TEXT, value)

fun PropertiesComponent.horizontalAlignment() = getValue(HORIZONTAL_ALIGNMENT, DEFAULT_HORIZONTAL_ALIGNMENT.name)
fun PropertiesComponent.setHorizontalAlignment(value: String) {
    try {
        setValue(HORIZONTAL_ALIGNMENT, ValidHorizontalAlignments.valueOf(value).name, DEFAULT_HORIZONTAL_ALIGNMENT.name)
    } catch (e: IllegalArgumentException) {
        // If the value is invalid, set it to the default value
        setValue(HORIZONTAL_ALIGNMENT, DEFAULT_HORIZONTAL_ALIGNMENT.name, DEFAULT_HORIZONTAL_ALIGNMENT.name)
        return
    }
}
fun PropertiesComponent.horizontalAlignmentValue(): Int {
    val horizontalAlignment = horizontalAlignment()
    return try {
        val validHorizontalAlignment = ValidHorizontalAlignments.valueOf(horizontalAlignment)
        validHorizontalAlignment.value
    } catch (e: IllegalArgumentException) {
        DEFAULT_HORIZONTAL_ALIGNMENT.value
    }
}

fun PropertiesComponent.verticalAlignment() = getValue(VERTICAL_ALIGNMENT, DEFAULT_VERTICAL_ALIGNMENT.name)
fun PropertiesComponent.setVerticalAlignment(value: String) {
    try {
        setValue(VERTICAL_ALIGNMENT, ValidVerticalAlignments.valueOf(value).name, DEFAULT_VERTICAL_ALIGNMENT.name)
    } catch (e: IllegalArgumentException) {
        // If the value is invalid, set it to the default value
        setValue(VERTICAL_ALIGNMENT, DEFAULT_VERTICAL_ALIGNMENT.name, DEFAULT_VERTICAL_ALIGNMENT.name)
        return
    }
}
fun PropertiesComponent.verticalAlignmentValue(): Int {
    val verticalAlignment = verticalAlignment()
    return try {
        val validVerticalAlignment = ValidVerticalAlignments.valueOf(verticalAlignment)
        validVerticalAlignment.value
    } catch (e: IllegalArgumentException) {
        DEFAULT_VERTICAL_ALIGNMENT.value
    }
}

fun PropertiesComponent.foregroundColor() = getValue(FOREGROUND_COLOR, DEFAULT_FOREGROUND_COLOR.name)
fun PropertiesComponent.setForegroundColor(value: String) {
    try {
        setValue(FOREGROUND_COLOR, ValidForegroundColors.valueOf(value).name, DEFAULT_FOREGROUND_COLOR.name)
    } catch (e: IllegalArgumentException) {
        // If the value is invalid, set it to the default value
        setValue(FOREGROUND_COLOR, DEFAULT_FOREGROUND_COLOR.name, DEFAULT_FOREGROUND_COLOR.name)
        return
    }
}
fun PropertiesComponent.foregroundColorValue(): Color {
    val foregroundColor = foregroundColor()
    return try {
        val validForegroundColor = ValidForegroundColors.valueOf(foregroundColor)
        validForegroundColor.value
    } catch (e: IllegalArgumentException) {
        DEFAULT_FOREGROUND_COLOR.value
    }
}

fun PropertiesComponent.applyTo(label: JLabel) {
    label.horizontalAlignment = horizontalAlignmentValue()
    label.verticalAlignment = verticalAlignmentValue()
    label.text = text().ifBlank { label.text }
    label.font = label.font.deriveFont(fontSize().toFloat())
    label.foreground = foregroundColorValue()
}