package com.example.my_application.feature_movielist.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import com.example.my_application.R
import com.example.my_application.feature_movielist.presentation.shows.ShowListView
import com.example.my_application.feature_movielist.presentation.utils.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = Color(R.color.red_100)) {
                            ShowListView()
                    }
                }
            }
        }
    }