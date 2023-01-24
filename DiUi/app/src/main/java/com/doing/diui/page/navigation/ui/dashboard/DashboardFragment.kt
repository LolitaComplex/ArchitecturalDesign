package com.doing.diui.page.navigation.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.doing.diui.databinding.FragmentDashboardBinding
import com.doing.hilibrary.log.DiLog

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        DiLog.d("Doing", "DashboardFragment onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        DiLog.d("Doing", "DashboardFragment onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        DiLog.d("Doing", "DashboardFragment onDetach()")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DiLog.d("Doing", "DashboardFragment onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiLog.d("Doing", "DashboardFragment onCreate()")
    }

    override fun onResume() {
        super.onResume()
        DiLog.d("Doing", "DashboardFragment onResume()")
    }

    override fun onPause() {
        super.onPause()
        DiLog.d("Doing", "DashboardFragment onPause()")
    }

    override fun onStop() {
        super.onStop()
        DiLog.d("Doing", "DashboardFragment onStop()")
    }

    override fun onStart() {
        super.onStart()
        DiLog.d("Doing", "DashboardFragment onStart()")
    }
}