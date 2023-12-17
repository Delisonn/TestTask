package com.example.testquiztask.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testquiztask.databinding.FragmentStartMenuBinding
import com.example.testquiztask.navigator

class StartMenuFragment : Fragment() {

    private lateinit var binding: FragmentStartMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStartMenuBinding.inflate(inflater, container, false)

        with(binding){
            startQuizButton.setOnClickListener {
                navigator().showQuiz()
            }

            startSettingsButton.setOnClickListener {
                navigator().showSettings()
            }
        }

        return binding.root
    }

}