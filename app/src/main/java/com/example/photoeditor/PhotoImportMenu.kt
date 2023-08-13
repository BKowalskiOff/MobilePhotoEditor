package com.example.photoeditor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.photoeditor.databinding.FragmentPhotoImportMenuBinding
import java.io.File
import java.io.FileDescriptor

interface FileSelectedListener{
    fun onFileSelected(image: Bitmap)
}

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoImportMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoImportMenu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPhotoImportMenuBinding
    private lateinit var photoChooserLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    private lateinit var photoSelectedListener: FileSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPhotoImportMenuBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempUri = initTempUri()

        //registering image selecting and taking picture activities to be able to launch them later (basically boilerplate)
        photoChooserLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            path: Uri? ->

                val bm = bitmapFromUri(path!!)

                photoSelectedListener.onFileSelected(bm)
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){
            val bm = bitmapFromUri(tempUri)
            photoSelectedListener.onFileSelected(bm)
        }

        binding.buttonChoosePicture.setOnClickListener{
            photoChooserLauncher.launch(PickVisualMediaRequest())
        }
        binding.buttonTakePicture.setOnClickListener{
            cameraLauncher.launch(tempUri)
        }

    }

    //initialization of temporary Uri for images taken with camera
    private fun initTempUri(): Uri {
        val tempImagesDir = File(requireContext().filesDir, getString(R.string.temp_images_dir))
        tempImagesDir.mkdir()
        val tempImage = File(tempImagesDir, getString(R.string.temp_image))
        return FileProvider.getUriForFile(requireContext(), getString(R.string.authorities), tempImage)
    }

    private fun bitmapFromUri(path: Uri): Bitmap{

        //boilerplate code to read image file content as bitmap from given Uri
        val pfd: ParcelFileDescriptor? = requireContext().contentResolver.openFileDescriptor(path, "r")
        val fd: FileDescriptor = pfd!!.fileDescriptor
        val bm: Bitmap = BitmapFactory.decodeFileDescriptor(fd!!)

        pfd.close()

        return bm.copy(Bitmap.Config.RGB_565, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        photoSelectedListener = context as FileSelectedListener
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotoImportMenu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotoImportMenu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}