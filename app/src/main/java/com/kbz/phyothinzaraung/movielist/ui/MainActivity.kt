package com.kbz.phyothinzaraung.movielist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kbz.phyothinzaraung.movielist.R
import com.kbz.phyothinzaraung.movielist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}