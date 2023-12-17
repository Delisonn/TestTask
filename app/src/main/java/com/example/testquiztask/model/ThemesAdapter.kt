package com.example.testquiztask.model

import android.content.ClipData.Item
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testquiztask.R
import com.example.testquiztask.databinding.ItemThemeBinding

class ThemesAdapter(private val context: Context) : RecyclerView.Adapter<ThemesAdapter.ThemeViewHolder>(){

    val themesList = listOf(
        R.style.DefaultThemeGoBackImageView,
        R.style.SecondThemeGoBackImageView,
        R.style.ThirdThemeGoBackImageView
    )
    private val themeManager: ThemeManager by lazy { ThemeManager(context) }

    class ThemeViewHolder(
        val binding: ItemThemeBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemThemeBinding.inflate(inflater, parent, false)

        return ThemeViewHolder(binding)
    }

    override fun getItemCount(): Int = themesList.size

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        val theme = themesList[position]
        with(holder.binding){

            when(position){
                0 -> {
                    showThemeTextView.setBackgroundResource(R.drawable.default_theme)
                    showThemeTextView.typeface = ResourcesCompat.getFont(context, R.font.pentagra)
                }
                1 -> {
                    showThemeTextView.setBackgroundResource(R.drawable.second_theme)
                    showThemeTextView.setTextColor(Color.WHITE)
                    showThemeTextView.typeface = ResourcesCompat.getFont(context, R.font.brassmono)
                }
                2 -> {
                    showThemeTextView.setBackgroundResource(R.drawable.third_theme)
                    showThemeTextView.typeface = ResourcesCompat.getFont(context, R.font.oceanwide)
                }
            }

            showThemeTextView.setOnClickListener {
                context.setTheme(theme)
                changeTheme(position)
            }
        }
    }

    private fun changeTheme(theme: Int) {
        themeManager.setSelectedTheme(theme)
        (context as AppCompatActivity).recreate()
    }

}