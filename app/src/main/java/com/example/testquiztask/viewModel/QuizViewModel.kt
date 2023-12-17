package com.example.testquiztask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testquiztask.model.QuestionData
import com.example.testquiztask.model.QuestionsData
import com.example.testquiztask.model.QuestionsListener
import com.example.testquiztask.model.QuizService

class QuizViewModel(
    private val quiz: QuizService
) : ViewModel() {

    private val _questions = MutableLiveData<List<QuestionData>>()
    val questions : LiveData<List<QuestionData>> = _questions

    private val listener: QuestionsListener = {
        _questions.value = it
    }

    init {
        loadData()
    }

    fun createNewQuestions(){
        quiz.createNewQuestions()
    }

    fun deleteQuestions(){
        quiz.deleteQuestions()
    }

    override fun onCleared() {
        super.onCleared()
        quiz.removeListener(listener)
    }

    fun loadData(){
        quiz.addListener(listener)
    }

    fun setCurrentAndSelectedIndex(currentFragmentIndex: Int, selectedIndex: Int){
        quiz.setCurrentAndSelectedIndex(currentFragmentIndex, selectedIndex)
    }

}