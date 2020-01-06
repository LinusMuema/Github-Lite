package com.moose.githublite.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class SampleSlide : Fragment() {
    private var layoutResId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = arguments!!.getInt(ARG_LAYOUT_RES_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(layoutResId, container, false)
        return view
    }

    companion object {
        private const val ARG_LAYOUT_RES_ID = "layoutResId"
        fun newInstance(layoutResId: Int): SampleSlide {
            val sampleSlide = SampleSlide()
            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            sampleSlide.arguments = args
            return sampleSlide
        }
    }
}