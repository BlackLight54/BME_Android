package hu.bme.aut.android.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.launcher.adapter.LauncherPagerAdapter
import hu.bme.aut.android.launcher.databinding.ActivityLauncherBinding

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpLauncherPanels.adapter = LauncherPagerAdapter(supportFragmentManager)
    }
}
