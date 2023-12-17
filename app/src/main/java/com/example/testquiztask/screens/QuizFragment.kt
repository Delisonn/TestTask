package com.example.testquiztask.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.testquiztask.MainActivity
import com.example.testquiztask.R
import com.example.testquiztask.checkInternet.CheckInternetConnection
import com.example.testquiztask.databinding.FragmentQuizBinding
import com.example.testquiztask.model.QuestionData
import com.example.testquiztask.navigator
import com.example.testquiztask.viewModel.QuizViewModel
import com.example.testquiztask.viewModel.factory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class QuizFragment : Fragment(){

    private lateinit var binding: FragmentQuizBinding
    private val viewModel : QuizViewModel by viewModels { factory() }
    private lateinit var questionsData: List<QuestionData>
    private lateinit var containersList: List<LinearLayout>
    private lateinit var countsTextView: List<TextView>
    private lateinit var answersTextView: List<TextView>
    private var currentCount : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        if (savedInstanceState != null) {
            currentCount = savedInstanceState.getInt(COUNTER_VALUE)
        } else {
            currentCount = getCounter()
            if (currentCount == 0) {
                viewModel.createNewQuestions()
            }
        }

        Log.d("createFragment", currentCount.toString())

        val internetConnection = CheckInternetConnection(requireActivity())
        internetConnection.observe(requireActivity()) { isConnection ->

            if (isConnection) {
                binding.layoutNoInternetConnection.visibility = View.GONE

                containersList = listOf(
                    binding.quizContainer1,
                    binding.quizContainer2,
                    binding.quizContainer3,
                    binding.quizContainer4,
                    binding.quizContainer5
                )

                countsTextView = listOf(
                    binding.countQuizTextView1,
                    binding.countQuizTextView2,
                    binding.countQuizTextView3,
                    binding.countQuizTextView4,
                    binding.countQuizTextView5
                )

                answersTextView = listOf(
                    binding.answerTextView1,
                    binding.answerTextView2,
                    binding.answerTextView3,
                    binding.answerTextView4,
                    binding.answerTextView5,
                )

                binding.goBackImageView.setOnClickListener {
                    viewModel.deleteQuestions()
                    navigator().goToMenu()
                }


                viewModel.questions.observe(viewLifecycleOwner, Observer {
                    questionsData = it

                    binding.quizTextView.visibility = View.VISIBLE
                    binding.mainQuizContainer.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                    for (question in questionsData) {
                        Log.d("checkResp", questionsData.toString())
                    }
                        with(binding) {
                            quizTextView.text = questionsData[currentCount].question

                            for (container in containersList.indices) {


                                countsTextView[container].text = (container + 1).toString()
                                answersTextView[container].text = questionsData[currentCount].answers[container]
                                containersList[container].setOnClickListener {
                                    lifecycleScope.launch {
                                        withContext(Dispatchers.Main) {
                                            if (container == questionsData[currentCount].correct_answer) {
                                                containersList[container].setBackgroundResource(R.drawable.correct_background_answer)
                                                countsTextView[container].setBackgroundResource(R.drawable.correct_shape_rounded)
                                                answersTextView[container].setTextColor(
                                                    ContextCompat.getColor(
                                                        requireContext(),
                                                        R.color.correct_color
                                                    )
                                                )
                                            } else {
                                                containersList[questionsData[currentCount].correct_answer].setBackgroundResource(
                                                    R.drawable.correct_background_answer
                                                )
                                                countsTextView[questionsData[currentCount].correct_answer].setBackgroundResource(
                                                    R.drawable.correct_shape_rounded
                                                )
                                                answersTextView[questionsData[currentCount].correct_answer].setTextColor(
                                                    ContextCompat.getColor(requireContext(), R.color.correct_color)
                                                )

                                                containersList[container].setBackgroundResource(R.drawable.error_background_answer)
                                                countsTextView[container].setBackgroundResource(R.drawable.error_shape_rounded)
                                                answersTextView[container].setTextColor(
                                                    ContextCompat.getColor(
                                                        requireContext(),
                                                        R.color.error_color
                                                    )
                                                )
                                            }
                                            for (answer in containersList.indices) {
                                                containersList[answer].isEnabled = false
                                            }
                                        }
                                        delay(1000)
                                        withContext(Dispatchers.Main) {
                                            if (currentCount < questionsData.size - 1)
                                                launchNext()
                                            else
                                                navigator().showResults()
                                        }
                                    }
                                    viewModel.setCurrentAndSelectedIndex(currentCount, container)
                                    Log.d("CheckDataAfterClick", questionsData[currentCount].toString())
                                }
                            }

                        }
//                    else
//                        navigator().showResults()
                })


            } else {
                binding.layoutNoInternetConnection.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                binding.quizTextView.visibility = View.GONE
                binding.mainQuizContainer.visibility = View.GONE
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigator().goToMenu()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNTER_VALUE, requireArguments().getInt(COUNTER_VALUE))
    }

    private fun launchNext(){
        val fragment = QuizFragment.newInstance(
            currentCount + 1
        )
        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()

    }

    private fun getCounter(): Int = requireArguments().getInt(COUNTER_VALUE)


    companion object{

        @JvmStatic
        private val COUNTER_VALUE = "COUNTER_VALUE"

        @JvmStatic
        fun newInstance(
            counterValue: Int
        ): QuizFragment{
            val args = Bundle().apply {
                putInt(COUNTER_VALUE, counterValue)
            }
            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
        }
    }
}