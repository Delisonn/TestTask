package com.example.testquiztask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.testquiztask.model.ThemeManager
import com.example.testquiztask.screens.QuizFragment
import com.example.testquiztask.screens.ResultFragment
import com.example.testquiztask.screens.SettingsFragment
import com.example.testquiztask.screens.StartMenuFragment

class MainActivity : AppCompatActivity(), Navigator {

    private val themeManager: ThemeManager by lazy { ThemeManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val selectedTheme = themeManager.getSelectedTheme()

        when(selectedTheme){
            0 -> setTheme(R.style.DefaultThemeGoBackImageView)
            1 -> setTheme(R.style.SecondThemeGoBackImageView)
            2 -> setTheme(R.style.ThirdThemeGoBackImageView)
        }

        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, StartMenuFragment())
                .commit()
    }


    override fun showSettings() {
        launchFragment(SettingsFragment())
    }

    override fun showQuiz() {
        val fragment = QuizFragment.newInstance(0)
        launchFragment(fragment)
    }

    override fun showResults() {
        launchFragment(ResultFragment())
    }

    override fun goToMenu() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    private fun launchFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }


}