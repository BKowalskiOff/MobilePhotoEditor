package com.example.photoeditor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.photoeditor.databinding.FragmentEffectConfigSingleApplyBinding
import com.example.photoeditor.databinding.FragmentEffectConfigSingleSeekBarBinding
import com.example.photoeditor.photo_classes.EffectType
import com.example.photoeditor.photo_classes.IEffect
import com.example.photoeditor.photo_classes.IEffectFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [EffectConfig.newInstance] factory method to
 * create an instance of this fragment.
 */
class EffectConfigSingleApply : EffectConfig() {

    // TODO: Rename and change types of parameters
    private var param1: EffectType? = null
    private lateinit var binding: FragmentEffectConfigSingleApplyBinding
    private lateinit var effect: IEffect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = EffectType.values()[it.getInt(ARG_PARAM1)]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentEffectConfigSingleApplyBinding.inflate(inflater, container, false)
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