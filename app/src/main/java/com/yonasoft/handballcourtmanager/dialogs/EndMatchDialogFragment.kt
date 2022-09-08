package com.yonasoft.handballcourtmanager.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentEndMatchDialogBinding


//Confirmation dialog when end match is pressed in a detail dialog
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
        //Setup listeners
        setupListeners()
    }

    private fun setupListeners() {
        //Function of the yes buttons
        binding!!.btnYes.setOnClickListener {
            //Send result of true to end the match to the detail fragment that opened this dialog
            setFragmentResult(
                REQUEST_KEY_END,
                bundleOf(BUNDLE_KEY_END to true)
            )
            //End dialog
            dismiss()
        }
        binding!!.btnNo.setOnClickListener {
            //Send result of false to end the match to the detail fragment that opened this dialog
            setFragmentResult(
                REQUEST_KEY_END,
                bundleOf(BUNDLE_KEY_END to false)
            )
            //End dialog
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
