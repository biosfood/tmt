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

import de.lukas.tmt.Tmt
import javafx.event.EventHandler
import kfoenix.jfxbutton
import tornadofx.View
import tornadofx.text
import tornadofx.vbox

class EditTaskView : View("edit task") {
    override val root = vbox {
        text("Edit Task ")
        jfxbutton("save") {
            onAction = EventHandler {
                Tmt.config.tasks += MainView.currentlyEditingTask
                Tmt.config.save()
                close()
            }
        }
    }
}