package com.example.testquiztask.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testquiztask.databinding.FragmentSettingsBinding
import com.example.testquiztask.model.ThemesAdapter
import com.example.testquiztask.navigator

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: ThemesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        adapter = ThemesAdapter(requireActivity())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.themesRecyclerView.layoutManager = layoutManager
        binding.themesRecyclerView.adapter = adapter

        binding.goBackImageView.setOnClickListener {
            navigator().goToMenu()
        }

        return binding.root
    }

}