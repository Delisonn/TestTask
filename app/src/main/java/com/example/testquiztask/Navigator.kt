package com.example.testquiztask

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator{
    return requireActivity() as Navigator
}

interface Navigator {

    fun showSettings()

    fun showQuiz()

    fun showResults()

    fun goToMenu()


}