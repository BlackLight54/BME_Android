package hu.bme.aut.android.launcher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.launcher.databinding.FragmentApplicationsBinding

class ApplicationsFragment : Fragment() {

    private lateinit var binding: FragmentApplicationsBinding;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}