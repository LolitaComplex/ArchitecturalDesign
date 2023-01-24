package com.doing.diui.page.navigation.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.doing.diui.databinding.FragmentNotificationsBinding
import com.doing.hilibrary.log.DiLog

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        DiLog.i("Doing", "NotificationsFragment onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        DiLog.i("Doing", "NotificationsFragment onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        DiLog.i("Doing", "NotificationsFragment onDetach()")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DiLog.i("Doing", "NotificationsFragment onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiLog.i("Doing", "NotificationsFragment onCreate()")
    }

    override fun onResume() {
        super.onResume()
        DiLog.i("Doing", "NotificationsFragment onResume()")
    }

    override fun onPause() {
        super.onPause()
        DiLog.i("Doing", "NotificationsFragment onPause()")
    }

    override fun onStop() {
        super.onStop()
        DiLog.i("Doing", "NotificationsFragment onStop()")
    }

    override fun onStart() {
        super.onStart()
        DiLog.i("Doing", "NotificationsFragment onStart()")
    }
}