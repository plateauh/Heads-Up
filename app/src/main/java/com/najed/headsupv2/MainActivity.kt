package com.najed.headsupv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var dataButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_btn)
        startButton.setOnClickListener {
            startActivity(Intent(this, CelebActivity::class.java))
        }

        dataButton = findViewById(R.id.data_btn)
        dataButton.setOnClickListener {
            startActivity(Intent(this, DataActivity::class.java)) // change activity
        }
    }
}