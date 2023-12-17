package com.example.testquiztask.model

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testquiztask.R
import com.example.testquiztask.databinding.RecItemBinding

class QuestionsAdapter(private val context: Context) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    var questionsData: List<QuestionData> = emptyList()

    class QuestionViewHolder(
        val binding: RecItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecItemBinding.inflate(inflater, parent, false)
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int = questionsData.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionsData[position]
        with(holder.binding){
            val containerList = listOf(
                quizContainer1,
                quizContainer2,
                quizContainer3,
                quizContainer4,
                quizContainer5
            )

            val countsTextViewList = listOf(
                countQuizTextView1,
                countQuizTextView2,
                countQuizTextView3,
                countQuizTextView4,
                countQuizTextView5
            )

            val answerTextViewList = listOf(
                answerTextView1,
                answerTextView2,
                answerTextView3,
                answerTextView4,
                answerTextView5
            )

            questionTextView.text = question.question

            for (container in containerList.indices){
                countsTextViewList[container].text = (container + 1).toString()
                answerTextViewList[container].text = if (question.selectedIndex == container) "${question.answers[container]} <- Your answer"
                  else question.answers[container]
                if (question.selectedIndex == question.correct_answer){
                    isCorrect(containerList[question.selectedIndex], countsTextViewList[question.selectedIndex], answerTextViewList[question.selectedIndex])
                } else {
                    isCorrect(containerList[question.correct_answer],  countsTextViewList[question.correct_answer], answerTextViewList[question.correct_answer])

                    containerList[question.selectedIndex].setBackgroundResource(R.drawable.error_background_answer)
                    countsTextViewList[question.selectedIndex].setBackgroundResource(R.drawable.error_shape_rounded)
                    answerTextViewList[question.selectedIndex].setTextColor(ContextCompat.getColor(context, R.color.error_color))
            }
            }
        }
    }

    private fun isCorrect(container: LinearLayout, countTextView: TextView, answerTextView: TextView){
        container.setBackgroundResource(R.drawable.correct_background_answer)
        countTextView.setBackgroundResource(R.drawable.correct_shape_rounded)
        answerTextView.setTextColor(ContextCompat.getColor(context, R.color.correct_color))
    }

}