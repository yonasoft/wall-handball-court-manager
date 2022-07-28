package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentSinglesDetailBinding

class FragmentSinglesDetailFragment: Fragment() {

    private var binding:FragmentSinglesDetailBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_singles_detail,container,false)
        val view = binding!!.root
        setHasOptionsMenu(true)
        val spinnerItems = List(32){ i -> i+1 }
        binding!!.spinnerCourtNum.adapter = ArrayAdapter(this.context!!,android.R.layout.simple_spinner_dropdown_item,spinnerItems)

        return view
    }
}