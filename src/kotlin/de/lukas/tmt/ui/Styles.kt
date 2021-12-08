/*
 * Copyright 2021 Lukas Eisenhauer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.lukas.tmt.ui

import javafx.geometry.Pos
import tornadofx.*

class Styles : Stylesheet() {
    init {
        val main = mixin {
            backgroundColor += background
            fill = foreground
            textFill = foreground
        }

        text {
            +main
        }

        textField {
            +main
        }

        textArea {
            +main
        }

        root {
            +main
        }

        taskCard {
            +main
            borderColor += box(foreground)
            borderWidth += box(2.px)
            padding = box(10.px)
        }

        saveButton {
            backgroundColor += green
            textFill = background
            borderRadius += box(5.px)
        }

        heading {
            fontSize = 3.em
            alignment = Pos.BASELINE_CENTER
        }

        greenButton {
            fill = strings
        }

        redButton {
            fill = error
        }

        jfxTabPane {
            tabHeaderArea {
                tabHeaderBackground {
                    backgroundColor += secondBackground
                    text {
                        fill = foreground
                    }
                }
            }
        }

        tabSelectedLine {
            backgroundColor += orange
            text {
                fill = foreground
            }
        }

        tab {
            padding = box(10.px)
        }

        tabContentArea {
            padding = box(10.px)
        }
    }

    companion object {
        val taskCard by cssclass()
        val heading by cssclass()
        val greenButton by cssclass()
        val redButton by cssclass()
        val root by cssclass()
        val saveButton by cssclass()

        val jfxTabPane by cssclass("jfx-tab-pane")
        val tabSelectedLine by cssclass("tab-selected-line")
        val cellFormat by cssclass()

        // material oceanic
        val background = c("#263238")
        val foreground = c("#B0BEC5")
        val textColor = c("#607D8B")
        val selectionBackground = c("#546E7A")
        val selectionForeground = c("#FFFFFF")
        val buttons = c("#2E3C43")
        val secondBackground = c("#32424A")
        val disabled = c("#415967")
        val contrast = c("#1E272C")
        val active = c("#314549")
        val border = c("#2A373E")
        val highlight = c("#425B67")
        val tree = c("#546E7A70")
        val notifications = c("#1E272C")
        val accent = c("#009688")
        val excluded = c("#2E3C43")
        val green = c("#c3e88d")
        val yellow = c("#ffcb6b")
        val blue = c("#82aaff")
        val red = c("#f07178")
        val purple = c("#c792ea")
        val orange = c("#f78c6c")
        val cyan = c("#89ddff")
        val gray = c("#546e7a")
        val error = c("#ff5370")
        val comments = c("#546e7a")
        val variables = c("#eeffff")
        val links = c("#80cbc4")
        val functions = c("#82aaff")
        val keywords = c("#c792ea")
        val tags = c("#f07178")
        val strings = c("#c3e88d")
        val operators = c("#89ddff")
        val attributes = c("#ffcb6b")
        val numbers = c("#f78c6c")
        val parameters = c("#f78c6c")
    }
}