package com.example.photoeditor

import android.content.ContentValues
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import com.example.photoeditor.databinding.ActivityMainBinding
import com.example.photoeditor.photo_classes.EffectType
import com.example.photoeditor.photo_classes.IEffect
import com.example.photoeditor.photo_classes.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.FileDescriptor
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
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
        binding.buttonChangeImage.setOnClickListener {
            val dialog = createChangeImageDialog()
            dialog.show()
        }
        binding.buttonSaveImage.setOnClickListener {
            val dialog = createSaveImageDialog()
            dialog.show()
        }
        //setting the menu fragment container to photo importing fragment
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fcvMenu.id, PhotoImportMenu())
            commit()
        }
    }

    private fun createChangeImageDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Utracisz wszystkie niezapisane zmiany!\nCzy chcesz kontynuować?")
            .setPositiveButton("TAK", DialogInterface.OnClickListener { dialogInterface, i ->
                photo = null
                binding.ivPhoto.setImageResource(R.drawable.empty_view)
                binding.topButtons.visibility = View.GONE
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.beginTransaction().apply {
                    replace(binding.fcvMenu.id, PhotoImportMenu())
                    commit()
                }
            })
            .setNegativeButton("NIE", null)
        return builder.create()

    }

    private fun createSaveImageDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Utracisz wszystkie niezapisane zmiany!\nCzy chcesz kontynuować?")
            .setPositiveButton("ZAPISZ", DialogInterface.OnClickListener { dialogInterface, i ->
                val name = "${UUID.randomUUID()}.png"
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DCIM}/PhotoEditor")
                }
                contentResolver.let { it ->
                    it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let{ uri ->
                        it.openOutputStream(uri)?.let{photo!!.preview!!.compress(Bitmap.CompressFormat.PNG,100, it)}
                    }
                }
            })
            .setNegativeButton("ANULUJ", null)
        return builder.create()
    }

    override fun onFileSelected(image: Bitmap) {

        photo = Photo(image)

        photo!!.original?.let { updateImageView(it) }
        binding.topButtons.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().apply{
            replace(binding.fcvMenu.id, EffectsMenu())
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
            replace(binding.fcvMenu.id, EffectConfig.newInstance(effect))
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