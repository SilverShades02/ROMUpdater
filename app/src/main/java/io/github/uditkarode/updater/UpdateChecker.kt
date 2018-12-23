package io.github.uditkarode.updater

import android.annotation.SuppressLint
import com.topjohnwu.superuser.Shell
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

enum class RequestStatus {SUCCESSFUL, FAILED}

fun Int.toBoolean() = this > 0

class UpdateChecker {

    private var sResponse : String = "init"
    private lateinit var joResponse: JSONObject

    fun getJson(): RequestStatus {
        Thread {
            sResponse = OkHttpClient().newCall(Request.Builder().url(Constants.JSON_URL).build()).execute().body()?.string().toString()
        }.run()

        return if(sResponse == "init"){
            joResponse = JSONObject(sResponse)
            RequestStatus.FAILED
        } else RequestStatus.SUCCESSFUL
    }

    @SuppressLint("SimpleDateFormat")
    fun updateAvailable(): Boolean {
        return if(joResponse.getString("type") == "OTA")
            SimpleDateFormat("dd/MM/yyyy").parse(joResponse.getString("date")).compareTo(SimpleDateFormat("dd/MM/yyyy").parse(getProp("ro.ota.date"))).toBoolean()
        else
            SimpleDateFormat("dd/MM/yyyy").parse(joResponse.getString("date")).compareTo(Date(Timestamp(getProp("ro.build.date.utc").toLong()).time)).toBoolean()
    }

    private fun getProp(key: String): String {
        return Shell.sh("getprop $key").exec().out[0]
    }
}