package com.dika.view.component.factory

import com.alee.extended.image.WebImage
import com.alee.extended.layout.VerticalFlowLayout
import com.alee.extended.panel.GroupPanel
import com.alee.extended.panel.GroupingType
import com.alee.laf.progressbar.WebProgressBar
import com.dika.Factory
import com.dika.res.BigIconRes
import com.dika.res.SmallIconRes
import com.dika.view.component.PopOver
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.SwingConstants

object PopOverFactory : Factory<PopOver>() {
    override fun create(): PopOver {
        return PopOver()
    }

    val warningPopOver
        get() = fun(title: String, message: String, type: WarningType): PopOver {
            return create().apply {
                isCloseOnFocusLoss = true
                val logoWebImage = WebImage(SmallIconRes.appsIcon)
                val warnWebImage = WebImage()
                val titleLabel = LabelFactory.create(title).apply {
                    horizontalAlignment = SwingConstants.CENTER
                    setBoldFont()
                }
                val messageField = TextAreaFactory.uneditableField.apply {
                    text = message
                }
                val closeButton = ButtonFactory.cancelButton

                when (type) {
                    WarningType.SUCCEED -> warnWebImage.setIcon(BigIconRes.suksesIcon)
                    WarningType.FAILED -> warnWebImage.setIcon(BigIconRes.blockIcon)
                    WarningType.INFO -> warnWebImage.setIcon(BigIconRes.infoIcon)
                }

                this.title = title
                layout = VerticalFlowLayout().apply {
                    vgap = 10
                }
                add(GroupPanel(GroupingType.fillMiddle, 2, logoWebImage, titleLabel, closeButton))
                add(messageField)
                add(warnWebImage)
                closeButton.text = ""
                closeButton.addActionListener({ dispose() })
                requestFocus()
                setMargin(10)
            }
        }

    val notificationPopOver
        get() = fun(message: String): PopOver {
            return create().apply {
                isCloseOnFocusLoss = true
                val lblMessage = LabelFactory.create(message)
                val closeButton = ButtonFactory.cancelButton

                layout = BorderLayout(10, 0)
                add(lblMessage, BorderLayout.CENTER)
                add(closeButton, BorderLayout.EAST)
                closeButton.text = ""
                closeButton.addActionListener { dispose() }
                requestFocus()
                setMargin(10)
            }
        }

    val progressPopOver get() = fun(title: String, message: String): PopOver {
        return create().apply {
            val logoWebImage = WebImage(SmallIconRes.appsIcon)
            val titleLabel = LabelFactory.create(title).apply {
                horizontalAlignment = SwingConstants.CENTER
                setBoldFont()
            }
            val messageField = TextAreaFactory.uneditableField.apply {
                text = message
            }
            val progressBar =  WebProgressBar().apply {
                isIndeterminate = true
                progressBottomColor = Color(0, 0, 153)
                minimumHeight = 30
            }

            this.title = title
            layout = VerticalFlowLayout().apply {
                vgap = 10
            }
            add(GroupPanel(GroupingType.fillLast, 5, logoWebImage, titleLabel))
            add(GroupPanel(2, false, messageField, progressBar))
            setMargin(20)
        }
    }

    enum class WarningType {
        SUCCEED, FAILED, INFO
    }
}