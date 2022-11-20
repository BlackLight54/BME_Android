package hu.bme.aut.android.servicedemo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import hu.bme.aut.android.servicedemo.service.LocationService

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, FragmentSettingsBasic())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)

        super.onStop()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when(key) {
            KEY_START_SERVICE -> {
                startOrStopServiceAsNecessary(sharedPreferences, applicationContext)
            }
        }
    }

    companion object {
        const val KEY_START_SERVICE = "start_service"

        fun startOrStopServiceAsNecessary(sharedPreferences: SharedPreferences, context: Context) {
            val startService = sharedPreferences.getBoolean(KEY_START_SERVICE, false)

            val intent = Intent(context, LocationService::class.java)

            if (startService) {
                context.startService(intent)
            } else {
                context.stopService(intent)
            }
        }
    }

    class FragmentSettingsBasic : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, key: String?) {
            addPreferencesFromResource(R.xml.preferences)
        }
    }

}