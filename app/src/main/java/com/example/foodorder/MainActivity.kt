package com.example.foodorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isGone
import com.example.foodorder.utils.ProgressDisplay

class MainActivity : AppCompatActivity(), ProgressDisplay {

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("Test","mainActivity")
    }

    override fun show() {

      progressBar = findViewById(R.id.progressBar)
      progressBar!!.visibility = View.VISIBLE
    }

    override fun hide() {

        progressBar = findViewById(R.id.progressBar)
        progressBar!!.visibility = View.GONE
    }

}
