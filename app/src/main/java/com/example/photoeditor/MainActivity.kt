package com.example.photoeditor

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.photoeditor.databinding.ActivityMainBinding
import com.example.photoeditor.photo_classes.EffectType
import com.example.photoeditor.photo_classes.IEffect
import com.example.photoeditor.photo_classes.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class MainActivity : AppCompatActivity(), FileSelectedListener, EffectSelectedListener,
    EffectConfigApplyListener, EffectConfigRevertListener, EffectConfigAcceptListener{

    private lateinit var binding: ActivityMainBinding
    private var photo: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setting the menu fragment container to photo importing fragment
        supportFragmentManager.beginTransaction().apply {
            add(binding.fcvMenu.id, PhotoImportMenu())
            addToBackStack(null)
            commit()
        }
    }

    override fun onFileSelected(image: Bitmap) {

        photo = Photo(image)

        photo!!.original?.let { updateImageView(it) }

        supportFragmentManager.beginTransaction().apply{
            add(binding.fcvMenu.id, EffectsMenu())
            addToBackStack(null)
            commit()
        }

    }

    private fun updateImageView(bitmap: Bitmap){
        val scaled = Bitmap.createScaledBitmap(bitmap, binding.ivPhoto.width, binding.ivPhoto.height, true)
        binding.ivPhoto.setImageBitmap(scaled)
    }

    override fun onEffectSelected(effect: EffectType) {
        supportFragmentManager.beginTransaction().apply{
            add(binding.fcvMenu.id, EffectConfig.newInstance(effect.ordinal))
            addToBackStack(null)
            commit()
        }
    }

    override fun onEffectConfigApply(effect: IEffect){
        try {
            photo?.let { it ->
                it.preview = it.original?.let { originalBitmap -> effect.modifyPhoto(originalBitmap) }
                it.preview?.let { updateImageView(it) }
            }
        }catch (e: Error){
            Log.d("ERROR", e.toString())
        }
    }

    override fun onEffectConfigAccept() {
        photo!!.preview?.let{
            photo!!.original = it
            updateImageView(it)
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onEffectConfigRevert() {
        photo!!.original?.let{
            photo!!.preview = it
            updateImageView(it)
            onBackPressedDispatcher.onBackPressed()
        }
    }

}