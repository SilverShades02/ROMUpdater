/*
Copyright 2018 Udit Karode

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at:

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package io.github.uditkarode.updater

import android.os.Bundle
import android.view.View
import com.topjohnwu.superuser.BusyBox
import com.topjohnwu.superuser.CallbackList
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.topjohnwu.superuser.Shell

class Flasher : AppCompatActivity() {

    private val tvConsole : TextView by lazy { findViewById<TextView>(R.id.txtLog) }
    private val svRoot : ScrollView by lazy { findViewById<ScrollView>(R.id.sv) }
    private val conlist = AppendCallbackList()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        BusyBox.setup(this@Flasher)
        setContentView(R.layout.activity_flash)
        
        // @todo: change these to actually do something
        Shell.sh("echo hi", "whoami", "pwd").to(conlist).exec()

        writeDone()
    }

    private inner class AppendCallbackList : CallbackList<String>() {
        override fun onAddElement(s: String) {
            tvConsole.append(s)
            tvConsole.append("\n")
            svRoot.postDelayed({ svRoot.fullScroll(ScrollView.FOCUS_DOWN) }, 10)
        }
    }

    fun writeDone(){
        findViewById<View>(R.id.button_panel).visibility = View.VISIBLE
    }
}