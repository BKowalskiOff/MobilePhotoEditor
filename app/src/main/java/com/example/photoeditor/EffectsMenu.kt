package com.example.photoeditor

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoeditor.databinding.FragmentEffectsMenuBinding
import com.example.photoeditor.databinding.FragmentPhotoImportMenuBinding
import com.example.photoeditor.photo_classes.EffectType

interface EffectSelectedListener{
    fun onEffectSelected(effect: EffectType)
}

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EffectsMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
class EffectsMenu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentEffectsMenuBinding
    private lateinit var effectSelectedListener: EffectSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        effectSelectedListener = context as EffectSelectedListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentEffectsMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonBlur.setOnClickListener { effectSelectedListener.onEffectSelected(EffectType.BLUR) }
        binding.buttonBrightness.setOnClickListener { effectSelectedListener.onEffectSelected(EffectType.BRIGHTNESS) }
        binding.buttonContrast.setOnClickListener { effectSelectedListener.onEffectSelected(EffectType.CONTRAST) }
        binding.buttonSharpness.setOnClickListener { effectSelectedListener.onEffectSelected(EffectType.SHARPNESS) }
        binding.buttonGammaCorrection.setOnClickListener { effectSelectedListener.onEffectSelected(EffectType.GAMMA_CORRECTION) }
        binding.buttonColourBalance.setOnClickListener { effectSelectedListener.onEffectSelected(EffectType.COLOUR_BALANCE) }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EffectsMenu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                EffectsMenu().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}