package com.najed.headsupv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.najed.headsupv2.db.Celeb
import com.najed.headsupv2.db.CelebsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataActivity : AppCompatActivity() {

    private lateinit var celebEditTexts: List<EditText>
    private lateinit var addButton: Button
    private lateinit var celebsRecyclerView: RecyclerView
    private lateinit var celebs: List<Celeb>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        celebs = listOf()
        celebsRecyclerView = findViewById(R.id.celebs_rv)
        var adapter = Adapter(celebs, this)

        CoroutineScope(IO).launch {
            celebs = CelebsDatabase.getInstance(applicationContext).celebDAO().getAllCelebs()
            withContext(Main) {
                adapter = Adapter(celebs, this@DataActivity)
                celebsRecyclerView.adapter = adapter
            }
        }
        celebsRecyclerView.layoutManager = LinearLayoutManager(this)

        celebEditTexts = listOf (
            findViewById(R.id.name_et),
            findViewById(R.id.taboo1_et),
            findViewById(R.id.taboo2_et),
            findViewById(R.id.taboo3_et)
        )

        addButton = findViewById(R.id.add_btn)
        addButton.setOnClickListener {
            val celeb = Celeb(0, celebEditTexts[0].text.toString(),
                celebEditTexts[1].text.toString(),
                celebEditTexts[2].text.toString(),
                celebEditTexts[3].text.toString())
            CoroutineScope(IO).launch {
                CelebsDatabase.getInstance(applicationContext).celebDAO().addCeleb(celeb)
                withContext(Main) {
                    Toast.makeText(this@DataActivity, "${celeb.name} added", Toast.LENGTH_SHORT).show()
                    adapter.update()
                }
            }
        }

    }
}