package com.yonasoft.handballcourtmanager.fragments.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private var binding: FragmentHelpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            this?.apply {
                setOnClickToggleVisibility(tvQueueTitle, queueDesc)
                setOnClickToggleVisibility(queueDrop, queueDesc)

                setOnClickToggleVisibility(tvMatchTitle, matchDesc)
                setOnClickToggleVisibility(matchDrop, matchDesc)

                setOnClickToggleVisibility(tvResultsTitle, resultsDesc)
                setOnClickToggleVisibility(resultsDrop, resultsDesc)

                queueDesc.visibility = View.GONE
                matchDesc.visibility = View.GONE
                resultsDesc.visibility = View.GONE
            }
        }
    }

    private fun setOnClickToggleVisibility(view: View, target: View) {
        view.setOnClickListener {
            target.visibility = if (target.visibility == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
