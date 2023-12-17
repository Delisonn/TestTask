package com.example.testquiztask.model

import android.util.Log
import com.example.testquiztask.BuildConfig
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

typealias QuestionsListener = (questionsData: List<QuestionData>) -> Unit

class QuizService() {

    private var questionsData: List<QuestionData>? = null

    private val listeners = mutableSetOf<QuestionsListener>()



    fun createNewQuestions(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                questionsData = takeTasks().await()
                notifyChanges()
                Log.d("checkResponse", questionsData.toString())
            } catch (e: Exception) {
                Log.e("Quiz", "Error: ${e.message}")
            }
        }
    }

    private suspend fun takeTasks(): Deferred<List<QuestionData>> = CoroutineScope(Dispatchers.IO).async {
        val request = Request.Builder()
            .url(BuildConfig.BASE_URL)
            .build()

        return@async suspendCancellableCoroutine { continuation ->
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body!!.string()
                        val questions = GsonBuilder().create()
                            .fromJson(body, QuestionsData::class.java)

                        val shuffledQuestions = questions.questions.shuffled()

                        val result = shuffledQuestions.take(5)

                        continuation.resume(result)
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }
            })

            continuation.invokeOnCancellation {
                OkHttpClient().newCall(request).cancel()
            }
        }
    }


    fun addListener(listener: QuestionsListener){
        listeners.add(listener)
        questionsData?.let { listener.invoke(it) }
    }

    fun removeListener(listener: QuestionsListener){
        listeners.remove(listener)
    }

    fun setCurrentAndSelectedIndex(currentFragmentIndex: Int, selectedIndex: Int){
        questionsData!![currentFragmentIndex].currentIndex = currentFragmentIndex
        questionsData!![currentFragmentIndex].selectedIndex = selectedIndex
    }

    fun deleteQuestions(){
        questionsData = null
    }

    private fun notifyChanges(){
        listeners.forEach { questionsData?.let { data -> it.invoke(data) } }
    }
}



data class QuestionsData(
    val questions: List<QuestionData>
)
data class QuestionData(
    val question: String,
    val answers: List<String>,
    val correct_answer: Int,
    var currentIndex: Int = 0,
    var selectedIndex: Int = 0,
)