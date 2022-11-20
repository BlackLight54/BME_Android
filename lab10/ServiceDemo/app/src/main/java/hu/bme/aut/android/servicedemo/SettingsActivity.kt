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
            KEY_START_SERVICE, KEY_WITH_FLOATING -> {
                startOrStopServiceAsNecessary(sharedPreferences, applicationContext)
            }
        }
    }

    companion object {
        const val KEY_START_SERVICE = "start_service"
        const val KEY_WITH_FLOATING = "with_floating"

        fun startOrStopServiceAsNecessary(sharedPreferences: SharedPreferences, context: Context) {
            val startService = sharedPreferences.getBoolean(KEY_START_SERVICE, false)
            val withFloating = sharedPreferences.getBoolean(KEY_WITH_FLOATING, false)

            val intent = Intent(context, LocationService::class.java)

            context.stopService(intent)

            if (startService) {
                intent.putExtra(KEY_WITH_FLOATING, withFloating)
                context.startService(intent)
            }
        }
    }

    class FragmentSettingsBasic : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, key: String?) {
            addPreferencesFromResource(R.xml.preferences)
        }
    }

}