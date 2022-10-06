package hu.bme.aut.android.launcher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.launcher.databinding.FragmentDialerBinding

class DialerFragment : Fragment() {

    private lateinit var binding: FragmentDialerBinding;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}