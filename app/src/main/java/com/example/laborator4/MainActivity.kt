package com.example.laborator4

import android.os.Bundle
import android.widget.ImageView
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    // Lista URL-urilor pentru imagini
    private val imageUrls = listOf(
        "http://cti.ubm.ro/cmo/digits/img0.jpg",
        "http://cti.ubm.ro/cmo/digits/img1.jpg",
        "http://cti.ubm.ro/cmo/digits/img2.jpg",
        "http://cti.ubm.ro/cmo/digits/img3.jpg",
        "http://cti.ubm.ro/cmo/digits/img4.jpg",
        "http://cti.ubm.ro/cmo/digits/img5.jpg",
        "http://cti.ubm.ro/cmo/digits/img6.jpg",
        "http://cti.ubm.ro/cmo/digits/img7.jpg",
        "http://cti.ubm.ro/cmo/digits/img8.jpg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        // Folosim Coroutine pentru a încărca imaginile asincron
        CoroutineScope(Dispatchers.Main).launch {
            loadImages(gridLayout)
        }
    }

    private suspend fun loadImages(gridLayout: GridLayout) = withContext(Dispatchers.IO) {
        for (url in imageUrls) {
            withContext(Dispatchers.Main) {
                val imageView = ImageView(this@MainActivity).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        // Setează dimensiuni fixe (de exemplu, 100dp x 100dp)
                        width = resources.displayMetrics.density.toInt() * 150
                        height = resources.displayMetrics.density.toInt() * 150
                        setMargins(8, 8, 8, 8)
                    }
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }

                gridLayout.addView(imageView)

                Glide.with(this@MainActivity)
                    .load(url)
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .into(imageView)
            }
        }
    }

}