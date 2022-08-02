package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentReturnToQueueDialogBinding

class ReturnToRegularQueueFragmentDialogFragment:DialogFragment() {

    private var binding:FragmentReturnToQueueDialogBinding?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_to_queue_dialog,container,false)
        binding!!.tvReturnToQueueMessage.text = "Select these players back to regular queue?"


        return binding!!.root
    }


}
