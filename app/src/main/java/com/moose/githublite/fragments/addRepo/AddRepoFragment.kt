package com.moose.githublite.fragments.addRepo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jaredrummler.materialspinner.MaterialSpinner
import com.moose.githublite.R
import kotlinx.android.synthetic.main.fragment_add_repo.*
import net.cachapa.expandablelayout.ExpandableLayout
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.design.snackbar
import org.json.JSONException
import org.json.JSONObject


class AddRepoFragment : Fragment() {

    private lateinit var addRepoViewModel: AddRepoViewModel
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private lateinit var spinner: MaterialSpinner
    private lateinit var shared: SharedPreferences
    private lateinit var group: ViewGroup
    private lateinit var token:String
    private var repoName:String = ""
    private var repoDescription:String = ""
    private var repoPrivate:String = ""
    private var autoInit:Boolean = false
    private var private:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addRepoViewModel = ViewModelProviders.of(this).get(AddRepoViewModel::class.java)
        shared = activity?.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        token = shared.getString("token","token")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_repo, container, false)
        group = view.findViewById(R.id.add_repo)
        spinner = view.findViewById(R.id.spinner)
        spinner.setItems("No", "Yes")
        spinner.setOnItemSelectedListener { _, _, _, item ->
            repoPrivate = item.toString()
        }
        val createdObserver = Observer<String>{
            if (it.contains("Created")){
                relative_repos.snackbar("Repository created successfully")
                clearForms(view)
            }
            else if(it == "It exists"){
                relative_repos.snackbar("Nice try! It exists.")
                clearForms(view)
            }
            else if (it == "No connection"){
                relative_repos.snackbar("No internet connection detected :(")
                clearForms(view)
            }
        }

        view.findViewById<Button>(R.id.name_btn).setOnClickListener {
            if (repo_name.text.isNullOrEmpty()) {
                name_layout.error = "Name cannot be empty"
            }
            else {
                repoName = repo_name.text.toString()
                name_instruction.setBackgroundColor(resources.getColor(R.color.success))
                expandable_name.toggle()
                expandable_description.toggle()
            }
        }
        view.findViewById<Button>(R.id.description_btn).setOnClickListener {
            if (!repo_description.text.isNullOrEmpty()) {
                repoDescription = repo_description.text.toString()
            }
            description_instruction.setBackgroundColor(resources.getColor(R.color.success))
            expandable_description.toggle()
            expandable_private.toggle()
        }

        view.findViewById<TextView>(R.id.auto_init).setOnClickListener {
            checkbox.isChecked = true
        }

        view.findViewById<Button>(R.id.private_btn).setOnClickListener {
            if (checkbox.isChecked)
                autoInit = true
            private = when(repoPrivate){
                "Yes" -> true
                else -> false
            }
            private_instruction.setBackgroundColor(resources.getColor(R.color.success))
            progress_bar.visibility = View.VISIBLE
            expandable_private.toggle()
            createJson()
        }
        addRepoViewModel.created.observe(this, createdObserver)
        return view
    }

    private fun clearForms(view: View) {
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        view.findViewById<RelativeLayout>(R.id.name_instruction).setBackgroundColor(resources.getColor(R.color.primary_dark))
        view.findViewById<RelativeLayout>(R.id.description_instruction).setBackgroundColor(resources.getColor(R.color.primary_dark))
        view.findViewById<RelativeLayout>(R.id.private_instruction).setBackgroundColor(resources.getColor(R.color.primary_dark))
        view.findViewById<EditText>(R.id.repo_name).text.clear()
        view.findViewById<EditText>(R.id.repo_description).text.clear()
        view.findViewById<CheckBox>(R.id.checkbox).isChecked= false
        view.findViewById<ExpandableLayout>(R.id.expandable_name).toggle()
        spinner.setItems("No", "Yes")
    }

    private fun createJson() {
        val repo = JSONObject()
        try {
            repo.put("name", repoName)
            if (repoDescription.isNotEmpty())
                repo.put("description", repoDescription)
            if (private)
                repo.put("private", private)
            if (autoInit)
                repo.put("auto_init", autoInit)
            addRepoViewModel.addRepo(token, repo.toString().toRequestBody(mediaType))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}