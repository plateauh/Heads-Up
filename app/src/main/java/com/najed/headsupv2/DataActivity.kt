package com.najed.headsupv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataActivity : AppCompatActivity() {

    private lateinit var celebEditTexts: List<EditText>
    private lateinit var addButton: Button
    private lateinit var celebsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val dbHelper = DBHelper(applicationContext)

        celebsRecyclerView = findViewById(R.id.celebs_rv)
        val adapter = Adapter(dbHelper.getAllCeleb(), this)
        celebsRecyclerView.adapter = adapter
        celebsRecyclerView.layoutManager = LinearLayoutManager(this)

        celebEditTexts = listOf (
            findViewById(R.id.name_et),
            findViewById(R.id.taboo1_et),
            findViewById(R.id.taboo2_et),
            findViewById(R.id.taboo3_et)
        )

        addButton = findViewById(R.id.add_btn)
        addButton.setOnClickListener {
            val celeb = CelebItem(0, celebEditTexts[0].text.toString(),
                celebEditTexts[1].text.toString(),
                celebEditTexts[2].text.toString(),
                celebEditTexts[3].text.toString())
            if (dbHelper.addCeleb(celeb)) {
                Toast.makeText(this, "${celeb.name} added", Toast.LENGTH_SHORT).show()
                adapter.update()
            }
        }

    }
}