package com.example.testquiztask

import android.app.Application
import com.example.testquiztask.model.QuizService

class App : Application() {

    val questionsService = QuizService()

}