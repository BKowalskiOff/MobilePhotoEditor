package com.example.photoeditor

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photoeditor.databinding.ActivityMainBinding
import com.example.photoeditor.photo_classes.Photo

class MainActivity : AppCompatActivity(), FileSelectedListener{

    private lateinit var binding: ActivityMainBinding
    private var photo: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setting the menu fragment container as photo importing fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fcv_menu, PhotoImportMenu())
            commit()
        }
    }

    override fun onFileSelected(image: Bitmap) {

        photo = Photo(image)

        val scaled = Bitmap.createScaledBitmap(photo!!.bitmap!!, binding.ivPhoto.width, binding.ivPhoto.height, true)

        binding.ivPhoto.setImageBitmap(scaled)

    }

}