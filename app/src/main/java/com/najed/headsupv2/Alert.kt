package com.najed.headsupv2

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.najed.headsupv2.db.Celeb
import com.najed.headsupv2.db.CelebsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Alert (private val context: Context, private val celeb: Celeb) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val dialogLayout: View = layoutInflater.inflate(R.layout.alert_layout, null)
    private val editTexts = listOf<EditText>(
        dialogLayout.findViewById(R.id.updated_name_et),
        dialogLayout.findViewById(R.id.updated_taboo1_et),
        dialogLayout.findViewById(R.id.updated_taboo2_et),
        dialogLayout.findViewById(R.id.updated_taboo3_et)
    )

    fun updateAlert() {
        dialogBuilder.setView(dialogLayout)
        editTexts[0].setText(celeb.name)
        editTexts[1].setText(celeb.taboo1)
        editTexts[2].setText(celeb.taboo2)
        editTexts[3].setText(celeb.taboo3)
        dialogBuilder.setPositiveButton("Update") { _, _ ->
            val newCeleb = Celeb(celeb.id, editTexts[0].text.toString(), editTexts[1].text.toString(),
                editTexts[2].text.toString(), editTexts[3].text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                CelebsDatabase.getInstance(context).celebDAO().updateCeleb(newCeleb)
            }
            Toast.makeText(context, "Celebrity updated successfully", Toast.LENGTH_SHORT).show()
        }
        dialogBuilder.setNegativeButton("Cancel"){ dialog, _ ->
                dialog.cancel()
            }
        dialogBuilder.setMessage("Update ${celeb.name}")
        dialogBuilder.create().show()
    }

    fun deleteAlert() {
        dialogBuilder.setPositiveButton("Delete") { _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                CelebsDatabase.getInstance(context).celebDAO().deleteCeleb(celeb)
            }
            Toast.makeText(context, "Celebrity deleted successfully", Toast.LENGTH_SHORT).show()
        }
        dialogBuilder.setNegativeButton("Cancel"){ dialog, _ ->
            dialog.cancel()
        }
        dialogBuilder.setMessage("Are you sure you want to delete ${celeb.name}?")
        dialogBuilder.create().show()
    }
}