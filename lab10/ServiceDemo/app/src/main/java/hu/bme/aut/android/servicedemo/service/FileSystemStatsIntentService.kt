package hu.bme.aut.android.servicedemo.service

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.*

class FileSystemStatsIntentService : IntentService("FileSystemStatsIntentService") {

    companion object {
        const val KEY_MESSENGER = "KEY_MESSENGER"
    }

    override fun onHandleIntent(intent: Intent?) {
        val freeSpace = calculateFreeSpace()
        sendResultFreeSpace(intent, freeSpace)
    }

    private fun calculateFreeSpace(): Long {
        val statFs = StatFs(Environment.getDataDirectory().absolutePath)
        val available = statFs.availableBlocksLong * statFs.blockSizeLong
        return available / 1024 / 1024
    }

    private fun sendResultFreeSpace(intent: Intent?, freeSpace: Long) {
        val extras = intent?.extras ?: return

        val messenger = extras.get(KEY_MESSENGER) as Messenger

        val msg = Message.obtain().apply {
            arg1 = Activity.RESULT_OK
            obj = freeSpace
        }

        try {
            messenger.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

}