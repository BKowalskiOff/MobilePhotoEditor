package com.example.photoeditor

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.photoeditor.databinding.ActivityMainBinding
import com.example.photoeditor.photo_classes.EffectType
import com.example.photoeditor.photo_classes.IEffect
import com.example.photoeditor.photo_classes.Photo

class MainActivity : AppCompatActivity(), FileSelectedListener, EffectSelectedListener, EffectConfigApplyListener{

    private lateinit var binding: ActivityMainBinding
    private var photo: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setting the menu fragment container as photo importing fragment
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fcvMenu.id, PhotoImportMenu())
            commit()
        }
    }

    override fun onFileSelected(image: Bitmap) {

        photo = Photo(image)

        photo!!.original?.let { updateImageView(it) }

        supportFragmentManager.beginTransaction().apply{
            replace(binding.fcvMenu.id, EffectsMenu())
            commit()
        }

    }

    private fun updateImageView(bitmap: Bitmap){
        val scaled = Bitmap.createScaledBitmap(bitmap, binding.ivPhoto.width, binding.ivPhoto.height, true)
        binding.ivPhoto.setImageBitmap(scaled)
    }

    override fun onEffectSelected(effect: EffectType) {
        supportFragmentManager.beginTransaction().apply{
            replace(binding.fcvMenu.id, EffectConfig.newInstance(effect.ordinal))
            commit()
        }
    }

    override fun onEffectConfigApply(effect: IEffect){
        //Log.d("TEST", photo!!.preview!!.toString())
        try {
            photo?.let { it ->
                it.preview = photo!!.original?.let { it1 -> effect.modifyPhoto(it1) }
                it.preview?.let { updateImageView(it) }
            }
        }catch (e: Error){
            Log.d("ERROR", e.toString())
        }
        Log.d("TEST", photo!!.preview!!.toString())

    }

}