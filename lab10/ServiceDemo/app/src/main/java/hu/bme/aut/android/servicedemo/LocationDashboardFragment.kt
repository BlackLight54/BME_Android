package hu.bme.aut.android.servicedemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.servicedemo.databinding.FragmentLocationDashboardBinding

class LocationDashboardFragment : Fragment() {
    private lateinit var binding: FragmentLocationDashboardBinding

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