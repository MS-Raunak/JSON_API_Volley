package com.ms.fetchingapi_volley

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMeme()

        val btnMeme = findViewById<Button>(R.id.btnMeme)
        btnMeme.setOnClickListener {
            getMeme()
        }

    }

    fun getMeme() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait..")
        progressDialog.show()

        var memeTitle = findViewById<TextView>(R.id.memeTitle)
        var memeAuthor = findViewById<TextView>(R.id.memeAuthor)
        var memeImg = findViewById<ImageView>(R.id.memeImg)
        // initiate the queue
        val queue = Volley.newRequestQueue(this)

        val url = "https://meme-api.com/gimme"

        val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url,
            { response ->
                Log.e("MainActivity", "getMemeData")
                var jsonObject = JSONObject(response)
                memeTitle.text = jsonObject.getString("title")
                memeAuthor.text = jsonObject.getString("author")
                Glide.with(this).load(jsonObject.get("url")).into(memeImg)

                progressDialog.dismiss()
            },
            {  error ->
                progressDialog.dismiss()
                Toast.makeText(this, "${error.localizedMessage}", Toast.LENGTH_LONG).show()
            })
        queue.add(stringRequest)
    }
}