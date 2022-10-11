package hu.bme.aut.android.launcher.fragment

import android.content.Intent
import android.net.Uri
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idPairList = listOf<Pair<View, String>>(
            binding.btnDialer0 to "0",
            binding.btnDialer1 to "1",
            binding.btnDialer2 to "2",
            binding.btnDialer3 to "3",
            binding.btnDialer4 to "4",
            binding.btnDialer5 to "5",
            binding.btnDialer6 to "6",
            binding.btnDialer7 to "7",
            binding.btnDialer8 to "8",
            binding.btnDialer9 to "9",
            binding.btnDialerHashmark to "#",
            binding.btnDialerStar to "*"
        )
        idPairList.forEach{ pair ->
            pair.first.setOnClickListener {
                addCharacterToDialer(pair.second)
            }
        }
        binding.btnCallBackSpace.setOnClickListener{
            removeLastCharacterFromDialer()
        }
        binding.btnCall.setOnClickListener {
            val phoneNumber = binding.etCall.text
            val phoneUri = "tel:$phoneNumber"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneUri))
            requireContext().startActivity(intent)
        }
    }

    private fun removeLastCharacterFromDialer() {
        binding.etCall.setText(binding.etCall.text.toString().dropLast(1))
    }

    private fun addCharacterToDialer(c: String){
        binding.etCall.setText(binding.etCall.text.toString() + c)
    }
}