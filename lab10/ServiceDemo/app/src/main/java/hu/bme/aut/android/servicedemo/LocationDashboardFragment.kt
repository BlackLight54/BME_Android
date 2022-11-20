package hu.bme.aut.android.servicedemo

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import hu.bme.aut.android.servicedemo.databinding.FragmentLocationDashboardBinding
import hu.bme.aut.android.servicedemo.service.LocationService
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.util.*

@RuntimePermissions
class LocationDashboardFragment : Fragment() {
    private lateinit var binding: FragmentLocationDashboardBinding

    private val locationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val currentLocation =
                intent.getParcelableExtra<Location>(LocationService.KEY_LOCATION)!!

            binding.fieldLat.tvValue.text = currentLocation.latitude.toString()
            binding.fieldLng.tvValue.text = currentLocation.longitude.toString()
            binding.fieldAlt.tvValue.text = currentLocation.altitude.toString()
            binding.fieldSpeed.tvValue.text = currentLocation.speed.toString()
            binding.fieldProvider.tvValue.text = currentLocation.provider
            binding.fieldPosTime.tvValue.text = Date(currentLocation.time).toString()
        }


    }
    override fun onStart() {
        super.onStart()

        registerReceiverWithPermissionCheck()
    }

    @NeedsPermission(
        ACCESS_COARSE_LOCATION,
        ACCESS_FINE_LOCATION
    )
    fun registerReceiver() {
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(locationReceiver, IntentFilter(LocationService.BR_NEW_LOCATION))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(locationReceiver)

        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onRequestPermissionsResult(requestCode, grantResults)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fieldProvider.tvHead.text = "Technology:"
        binding.fieldLat.tvHead.text = "Latitude:"
        binding.fieldLng.tvHead.text = "Longitude:"
        binding.fieldSpeed.tvHead.text = "Speed:"
        binding.fieldAlt.tvHead.text = "Height:"
        binding.fieldPosTime.tvHead.text = "Position time:"
    }
}