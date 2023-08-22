package com.example.photoeditor

import android.content.Context
import android.media.effect.Effect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.photoeditor.photo_classes.EffectType
import com.example.photoeditor.photo_classes.IEffect
import com.example.photoeditor.photo_classes.IEffectFactory
import kotlinx.coroutines.runBlocking
import kotlin.math.max

interface EffectConfigApplyListener{
    fun onEffectConfigApply(effect: IEffect)
}

interface EffectConfigAcceptListener{
    fun onEffectConfigAccept()
}
interface EffectConfigRevertListener{
    fun onEffectConfigRevert()
}

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [EffectConfig.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class EffectConfig : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: EffectType? = null
    private lateinit var effect: IEffect
    protected lateinit var effectConfigApplyListener: EffectConfigApplyListener
    protected lateinit var effectConfigAcceptListener: EffectConfigAcceptListener
    protected lateinit var effectConfigRevertListener: EffectConfigRevertListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = EffectType.values()[it.getInt(ARG_PARAM1)]
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        effectConfigApplyListener = context as EffectConfigApplyListener
        effectConfigAcceptListener = context as EffectConfigAcceptListener
        effectConfigRevertListener = context as EffectConfigRevertListener
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EffectConfig.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: EffectType) =
            when(param1){
                EffectType.BRIGHTNESS,
                EffectType.CONTRAST,
                EffectType.BLUR,
                EffectType.SHARPNESS,
                EffectType.GAMMA_CORRECTION -> {
                    EffectConfigSingleSeekBar().apply{
                        arguments = Bundle().apply{
                            putInt(ARG_PARAM1, param1.ordinal)
                        }
                    }
                }
                EffectType.GRAYSCALE -> {
                    EffectConfigSingleApply().apply{
                        arguments = Bundle().apply{
                            putInt(ARG_PARAM1, param1.ordinal)
                        }
                    }
                }
                EffectType.COLOUR_BALANCE -> {
                    EffectConfigRGBSeekBar().apply{
                        arguments = Bundle().apply{
                            putInt(ARG_PARAM1, param1.ordinal)
                        }
                    }
                }
            }
        }
    }