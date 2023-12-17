package com.example.testquiztask.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testquiztask.databinding.FragmentResultBinding
import com.example.testquiztask.model.QuestionData
import com.example.testquiztask.model.QuestionsAdapter
import com.example.testquiztask.navigator
import com.example.testquiztask.viewModel.QuizViewModel
import com.example.testquiztask.viewModel.factory

class ResultFragment: Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var adapter: QuestionsAdapter
    private val viewModel : QuizViewModel by viewModels { factory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        binding.goBackImageView.setOnClickListener {
            viewModel.deleteQuestions()
            navigator().goToMenu()
        }

        viewModel.questions.observe(viewLifecycleOwner, Observer {

            adapter = QuestionsAdapter(requireActivity())

            val layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter

            adapter.questionsData = it

        })

        binding.tryAgainButton.setOnClickListener {
            viewModel.deleteQuestions()
            navigator().showQuiz()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.deleteQuestions()
                navigator().goToMenu()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

}