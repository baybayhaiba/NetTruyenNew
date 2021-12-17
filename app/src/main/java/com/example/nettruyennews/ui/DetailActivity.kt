package com.example.nettruyennews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nettruyennews.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}