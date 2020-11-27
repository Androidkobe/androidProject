package com.example.demo.sundu.developer.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.demo.R
import kotlinx.android.synthetic.main.developer_viewpage_collection_demo_itemfragment.view.*

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */


private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class DemoObjectFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.developer_viewpage_collection_demo_itemfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf {
            it.containsKey(ARG_OBJECT)
        }?.apply {
            val textView: TextView = view.text
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}