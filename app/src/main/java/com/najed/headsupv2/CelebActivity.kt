package com.najed.headsupv2

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CelebActivity : AppCompatActivity() {

    var celebCount = 0
    lateinit var celebs: Celeb
    lateinit var landscapeLayout: LinearLayout
    lateinit var portraitLayout: ConstraintLayout
    lateinit var celebTextViews: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celeb)
        landscapeLayout = findViewById(R.id.celeb_info_layout)
        portraitLayout = findViewById(R.id.portrait_layout)

        Paper.init(this)
        celebs = Paper.book().read("celebs", Celeb())
        if (celebs.isEmpty()){
            setCelebs()
            Log.d("celebCount", "inside if celebs.isEmpty()")
        }
        else {
            updateCelebViews()
            Log.d("celebCount", "inside else, the count is $celebCount")
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateCelebViews()
            portraitLayout.visibility = INVISIBLE
            landscapeLayout.visibility = VISIBLE
        }
        else {
            celebCount++
            landscapeLayout.visibility = INVISIBLE
            portraitLayout.visibility = VISIBLE
        }
    }

    private fun setCelebs() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<Celeb?>? = apiInterface!!.getCelebs()
        call?.enqueue(object: Callback<Celeb?> {
            override fun onResponse(call: Call<Celeb?>, response: Response<Celeb?>) {
                celebs = response.body()!!
                Paper.book().write("celebs", celebs)
                updateCelebViews()
            }
            override fun onFailure(call: Call<Celeb?>, t: Throwable) {
                Toast.makeText(this@CelebActivity, t.message, Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        })
    }

    private fun updateCelebViews(){
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
}