package com.najed.headsupv2

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.najed.headsupv2.db.Celeb
import com.najed.headsupv2.db.CelebsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CelebActivity : AppCompatActivity() {

    private var celebCount = 0 // to iterate celebrities
    private lateinit var celebs: List<Celeb> // the list of celebrities
    private lateinit var landscapeLayout: LinearLayout // the layout displayed in landscape mode
    private lateinit var portraitLayout: ConstraintLayout // the layout displayed in portrait mode
    private lateinit var timerTextView: TextView
    private lateinit var celebTextViews: List<TextView> // the list of celebrity TextViews (name and taboos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celeb)


        landscapeLayout = findViewById(R.id.celeb_info_layout)
        portraitLayout = findViewById(R.id.portrait_layout)
        timerTextView = findViewById(R.id.timer_tv)

        CoroutineScope(Dispatchers.IO).launch {
        celebs = CelebsDatabase.getInstance(applicationContext).celebDAO().getAllCelebs()
        }

        startTimer()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateCelebViews() // the counter increased when phone was in portrait mode so we just have to update the views
            portraitLayout.visibility = INVISIBLE
            landscapeLayout.visibility = VISIBLE
        }
        else {
            if (celebCount == celebs.size-1) celebCount = -1 // when reaching last celebrity
            celebCount++ // to get new celebrity when phone get back to landscape mode
            landscapeLayout.visibility = INVISIBLE
            portraitLayout.visibility = VISIBLE
        }
    }


    // responsible of finding & updating celebrity TextViews
    private fun updateCelebViews() {
        celebTextViews = listOf (
            findViewById(R.id.celeb_name_tv),
            findViewById(R.id.celeb_taboo1_tv),
            findViewById(R.id.celeb_taboo2_tv),
            findViewById(R.id.celeb_taboo3_tv)
        )
        val currentCeleb = celebs[celebCount]
        celebTextViews[0].text = currentCeleb.name
        celebTextViews[1].text = currentCeleb.taboo1
        celebTextViews[2].text = currentCeleb.taboo2
        celebTextViews[3].text = currentCeleb.taboo3
    }

    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "Time: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                timerTextView.text = "Time: 0"
                timerTextView.setTextColor(Color.RED)
                startActivity(Intent(this@CelebActivity, MainActivity::class.java))
            }
        }.start()
    }
}