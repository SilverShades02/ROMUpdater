/*
   * Copyright 2018 Udit Karode
   *
   * Licensed under the Apache License, Version 2.0 (the "License");
   * you may not use this file except in compliance with the License.
   * You may obtain a copy of the License at:
   *
   * http://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   * See the License for the specific language governing permissions and
   * limitations under the License.
 */

package io.github.uditkarode.updater

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import co.revely.gradient.RevelyGradient
import com.airbnb.lottie.LottieAnimationView
import com.androidnetworking.AndroidNetworking
import com.tonyodev.fetch2.*
import com.topjohnwu.superuser.Shell
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import com.airbnb.lottie.network.NetworkFetcher.fetch
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2core.DownloadBlock
import org.jetbrains.annotations.NotNull


class Main : AppCompatActivity() {

    private lateinit var fetch: Fetch
    private val tvHeader: TextView by lazy { findViewById<TextView>(R.id.header_title) }
    private val lavNoNotifs: LottieAnimationView by lazy { findViewById<LottieAnimationView>(R.id.nonotifs) }

    private lateinit var downUrl: String // @todo: obtain from json

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()

        AndroidNetworking.initialize(applicationContext)
        Toast.makeText(this@Main, getProp("ro.build.date.utc"), Toast.LENGTH_LONG).show() //@todo: do something with this

        if(updateAvailable()){
            fetch = Fetch.getInstance(FetchConfiguration.Builder(this).setDownloadConcurrentLimit(1).build())
            val request = Request(downUrl, Constants.UPDATE_PACKAGE)
            request.priority = Priority.HIGH
            request.networkType = NetworkType.ALL

            val fetchListener = object : FetchListener {
                override fun onCancelled(download: Download) {
                    TODO("add cancel button to UI")
                }

                override fun onCompleted(download: Download) {
                    TODO("show 'install now?' option to user") 
                }

                override fun onDeleted(download: Download) {
                    TODO("Show dialog based error") 
                }

                override fun onError(download: Download, error: Error, throwable: Throwable?) {
                    TODO("show dialog based error") 
                }

                override fun onPaused(download: Download) {
                    TODO("show 'paused' status on UI") 
                }

                override fun onProgress(download: Download, etaInMilliSeconds: Long, downloadedBytesPerSecond: Long) {
                    TODO("increment progress in app/notification") 
                }

                override fun onQueued(download: Download, waitingOnNetwork: Boolean) {}

                override fun onRemoved(download: Download) {
                    TODO("update cancelled toast")
                }

                override fun onResumed(download: Download) {
                    TODO("resuming download toast")
                }

                override fun onStarted(download: Download, downloadBlocks: List<DownloadBlock>, totalBlocks: Int) {
                    TODO("show user update downloading ui and notification")
                }

                override fun onWaitingNetwork(download: Download) {
                    TODO("show user 'waiting for network' message")
                }

                override fun onDownloadBlockUpdated (
                    download: Download,
                    downloadBlock: DownloadBlock,
                    totalBlocks: Int
                ) {
                    TODO("show user number of blocks downloaded")
                }

                override fun onAdded(download: Download) {}
            }

            fetch.addListener(fetchListener)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun setupUi(){
        RevelyGradient
            .sweep()
            .colors(intArrayOf(Color.parseColor("#FF2525"), Color.parseColor("#6078EA"))) //@todo: find a better gradient ffs
            .on(tvHeader)

        tvHeader.setOnClickListener {
            startActivity(Intent(this@Main, Flasher::class.java))
        }

        lavNoNotifs.setOnClickListener {
            lavNoNotifs.speed = 8f
            lavNoNotifs.playAnimation()
        }
    }

    private fun getProp(key: String): String {
        return Shell.sh("getprop $key").exec().out[0]
    }

    private fun updateAvailable() : Boolean {
        // @todo: check for updates using json file on github (raw)
        return true
    }
}
