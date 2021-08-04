package com.nitro.navigationmultibackstackinnernav.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkBuilder
import com.nitro.navigationmultibackstackinnernav.R
import com.nitro.navigationmultibackstackinnernav.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        binding.button.setOnClickListener {
            NavDeepLinkBuilder(requireContext())
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.navigation_notifications)
                .createTaskStackBuilder().startActivities()

            //Same issue :
//            findNavController().navigate(R.id.navigation_notifications)

            //Fixed :
//            findNavController().navigate(R.id.navigation_notifications, null, navOptions {
//                popUpTo(findNavController().graph.findStartDestination().id) {
//                    saveState = true
//                    inclusive = false
//                }
//            })
        }
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}