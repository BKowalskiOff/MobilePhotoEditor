package com.example.photoeditor

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.FloatRange
import com.example.photoeditor.databinding.FragmentEffectConfigRgbSeekBarBinding
import com.example.photoeditor.databinding.FragmentEffectConfigSingleSeekBarBinding
import com.example.photoeditor.photo_classes.EffectType
import com.example.photoeditor.photo_classes.IEffect
import com.example.photoeditor.photo_classes.IEffectFactory
import kotlinx.coroutines.runBlocking
import kotlin.math.max

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [EffectConfig.newInstance] factory method to
 * create an instance of this fragment.
 */

enum class RGBColor{
    RED,
    GREEN,
    BLUE
}

class EffectConfigRGBSeekBar : EffectConfig() {

    // TODO: Rename and change types of parameters
    private var param1: EffectType? = null
    private lateinit var binding: FragmentEffectConfigRgbSeekBarBinding
    private lateinit var effect: IEffect
    private var rgbColor: RGBColor? = null
    private var rValue = 0.5
    private var gValue = 0.5
    private var bValue = 0.5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = EffectType.values()[it.getInt(ARG_PARAM1)]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentEffectConfigRgbSeekBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        effectConfigAcceptListener = context as EffectConfigAcceptListener
        effectConfigRevertListener = context as EffectConfigRevertListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewEffectType.text = param1?.let {  EffectType.names[it.ordinal]}
        binding.buttonAccept.setOnClickListener {
            effectConfigAcceptListener.onEffectConfigAccept()
        }
        binding.buttonRevert.setOnClickListener {
            effectConfigRevertListener.onEffectConfigRevert()
        }

        binding.buttonRed.setOnClickListener {
            it.alpha = 1f
            binding.buttonBlue.alpha = 0.2f
            binding.buttonGreen.alpha = 0.2f
            rgbColor = RGBColor.RED
        }
        binding.buttonGreen.setOnClickListener {
            it.alpha = 1f
            binding.buttonRed.alpha = 0.2f
            binding.buttonBlue.alpha = 0.2f
            rgbColor = RGBColor.GREEN
        }
        binding.buttonBlue.setOnClickListener {
            it.alpha = 1f
            binding.buttonRed.alpha = 0.2f
            binding.buttonGreen.alpha = 0.2f
            rgbColor = RGBColor.BLUE
        }

        binding.seekBarEffectVal.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                if (rgbColor == null) {
                    Toast.makeText(context, "Najpierw wybierz kolor", Toast.LENGTH_LONG)
                    return
                }

                seekBar?.let {
                    // Normalize seekbar value to range 0.0-1.0 with step: 1/1024
                    // With that, we get precise values for rgb ranges (0 to 255, -255 to 255),
                    // some small integer ranges (e.g. blur radius)
                    // and floating point ranges (e.g. contrast coefficient)
                    when (rgbColor!!) {
                        RGBColor.RED -> rValue = it.progress.toDouble() / it.max
                        RGBColor.GREEN -> gValue = it.progress.toDouble() / it.max
                        RGBColor.BLUE -> bValue = it.progress.toDouble() / it.max
                    }
                    val value = it.progress.toDouble() / it.max

                    IEffectFactory().createEffect(param1!!, rValue, gValue, bValue)?.let {
                        effectConfigApplyListener.onEffectConfigApply(it)
                    }

                }
            }
        })

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
        @JvmStatic fun newInstance(param1: Int) =
                EffectConfigSingleSeekBar().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                    }
                }
    }
}