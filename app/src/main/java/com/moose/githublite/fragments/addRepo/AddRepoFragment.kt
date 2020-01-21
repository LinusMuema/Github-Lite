package com.moose.githublite.fragments.addRepo

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsSpinner
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jaredrummler.materialspinner.MaterialSpinner
import com.moose.githublite.R
import kotlinx.android.synthetic.main.fragment_add_repo.*

class AddRepoFragment : Fragment() {

    private lateinit var addRepoViewModel: AddRepoViewModel
    private lateinit var repoName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addRepoViewModel = ViewModelProviders.of(this).get(AddRepoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_repo, container, false)
        view.findViewById<Button>(R.id.name_btn).setOnClickListener {
            expandable_name.toggle()
            expandable_description.toggle()
        }
        view.findViewById<Button>(R.id.description_btn).setOnClickListener {
            expandable_description.toggle()
            expandable_homepage.toggle()
        }
        view.findViewById<Button>(R.id.homepage_btn).setOnClickListener {
            expandable_homepage.toggle()
            expandable_private.toggle()
        }
        view.findViewById<Button>(R.id.private_btn).setOnClickListener {
            expandable_private.toggle()
            expandable_license.toggle()
        }
        view.findViewById<Button>(R.id.license_btn).setOnClickListener {
            expandable_license.toggle()
            expandable_name.toggle()
        }
        return view
    }

}