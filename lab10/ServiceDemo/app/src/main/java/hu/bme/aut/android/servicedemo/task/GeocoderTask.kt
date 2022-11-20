package hu.bme.aut.android.servicedemo.task

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Handler
import android.widget.Toast
import java.util.*

class GeoCoderTask(private val context: Context, private vararg val params: Location) : Runnable {
    override fun run() {
        val result = StringBuilder()

        val location = params[0]

        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address: Address? = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )?.firstOrNull()

            if (address == null) {
                throw RuntimeException("No address found")
            }

            for (i in 0..address.maxAddressLineIndex) {
                result.append(address.getAddressLine(i))

                if (i != address.maxAddressLineIndex) {
                    result.append("\n")
                }
            }
        } catch (e: Exception) {
            result.append("No address: ")
            result.append(e.message)
        }

        val mainHandler = Handler(context.mainLooper)
        mainHandler.post { Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show() }
    }
}