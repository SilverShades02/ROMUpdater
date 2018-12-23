package io.github.uditkarode.updater

import okhttp3.OkHttpClient
import okhttp3.Request

enum class RequestStatus {SUCCESSFUL, FAILED}

class UpdateChecker() {

    private var response : String = "init"

    fun getJson(): RequestStatus {
        Thread {
            response = OkHttpClient().newCall(Request.Builder().url(Constants.JSON_URL).build()).execute().body()?.string().toString()
        }.run()

         return if(response == "init") RequestStatus.FAILED else RequestStatus.SUCCESSFUL
    }

    private fun checkUpdate(){
        getJson()
    }

    fun updateAvailable(): Boolean {
        return true
    }
}