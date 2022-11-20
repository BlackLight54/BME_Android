package hu.bme.aut.android.servicedemo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import hu.bme.aut.android.servicedemo.databinding.ActivityMainBinding
import hu.bme.aut.android.servicedemo.service.FileSystemStatsIntentService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;

    @SuppressLint("HandlerLeak")
    private val freeSpaceHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.arg1 == Activity.RESULT_OK) {
                val freeMB = msg.obj as Long
                val freeGB = freeMB / 1024
                Toast.makeText(this@MainActivity, getString(R.string.txt_free_space, freeMB, freeGB), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val freeSpaceMessenger = Messenger(freeSpaceHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.layoutContainer, LocationDashboardFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_free_space -> {
                val intentStartService = Intent(this, FileSystemStatsIntentService::class.java)
                intentStartService.putExtra(FileSystemStatsIntentService.KEY_MESSENGER, freeSpaceMessenger)
                startService(intentStartService)
            }
        }

        return true
    }

}