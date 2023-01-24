package com.doing.diui.page.navigation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.doing.diui.R
import com.doing.diui.databinding.FragmentHomeBinding
import com.doing.hilibrary.log.DiLog

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnNext: Button = binding.HomeFragmentBtnNext
        homeViewModel.text.observe(viewLifecycleOwner) {
            btnNext.text = it
        }

        btnNext.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.navigation_activity_viewpager2)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        DiLog.w("Doing", "HomeFragment onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        DiLog.w("Doing", "HomeFragment onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        DiLog.w("Doing", "HomeFragment onDetach()")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DiLog.w("Doing", "HomeFragment onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiLog.w("Doing", "HomeFragment onCreate()")
    }

    override fun onResume() {
        super.onResume()
        DiLog.w("Doing", "HomeFragment onResume()")
    }

    override fun onPause() {
        super.onPause()
        DiLog.w("Doing", "HomeFragment onPause()")
    }

    override fun onStop() {
        super.onStop()
        DiLog.w("Doing", "HomeFragment onStop()")
    }

    override fun onStart() {
        super.onStart()
        DiLog.w("Doing", "HomeFragment onStart()")
    }
}