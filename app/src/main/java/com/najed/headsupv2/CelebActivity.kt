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

    var celebCount = 0 // to iterate celebrities
    lateinit var celebs: Celeb // the list of celebrities
    lateinit var landscapeLayout: LinearLayout // the layout displayed in landscape mode
    lateinit var portraitLayout: ConstraintLayout // the layout displayed in portrait mode
    lateinit var celebTextViews: List<TextView> // the list of celebrity TextViews (name and taboos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celeb)
        landscapeLayout = findViewById(R.id.celeb_info_layout)
        portraitLayout = findViewById(R.id.portrait_layout)

        /* Paper is a library alternative to shared preferences
           but works as a fast NoSQL storage. Helped me in storing
           data of non-primitive type, in this case is Celeb which
           is an array list of CelebItems.
           I chose to store data so I don't bother calling the API
           each time running the app.
         */
        Paper.init(this) // initialize paper object
        celebs = Paper.book().read("celebs", Celeb()) // get stored Celeb object. If there's none, the default value is an empty object

        if (celebs.isEmpty()){ // first time running the app, or data's wiped
            setCelebs() // get data from the API
            Log.d("celebCount", "inside if celebs.isEmpty()")
        }
        else { // not the first time running the app but at the beginning of the game
            updateCelebViews()
            Log.d("celebCount", "inside else, the count is $celebCount")
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateCelebViews() // the counter increased when phone was in portrait mode so we just have to update the views
            portraitLayout.visibility = INVISIBLE
            landscapeLayout.visibility = VISIBLE
        }
        else {
            celebCount++ // to get new celebrity when phone get back to landscape mode
            landscapeLayout.visibility = INVISIBLE
            portraitLayout.visibility = VISIBLE
        }
    }

    private fun setCelebs() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<Celeb?>? = apiInterface!!.getCelebs()
        call?.enqueue(object: Callback<Celeb?> {
            override fun onResponse(call: Call<Celeb?>, response: Response<Celeb?>) {
                celebs = response.body()!! // set the response to the object to be iterated through
                updateCelebViews() // to be displayed first time
                Paper.book().write("celebs", celebs) // store it in paper
            }
            override fun onFailure(call: Call<Celeb?>, t: Throwable) {
                Toast.makeText(this@CelebActivity, t.message, Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        })
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
}