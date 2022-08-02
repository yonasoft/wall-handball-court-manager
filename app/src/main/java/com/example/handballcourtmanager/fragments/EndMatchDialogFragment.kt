package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentEndMatchDialogBinding


class EndMatchDialogFragment: DialogFragment() {

    private var binding:FragmentEndMatchDialogBinding?=null

    companion object{
        const val REQUEST_KEY_END = "request_key"
        const val BUNDLE_KEY_END = "bundle_key"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_end_match_dialog, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.btnYes.setOnClickListener{
            setFragmentResult(REQUEST_KEY_END,
            bundleOf(BUNDLE_KEY_END to true)
                )
        }
        binding!!.btnNo.setOnClickListener{
            setFragmentResult(REQUEST_KEY_END,
                bundleOf(BUNDLE_KEY_END to false)
            )

        }
    }

}
