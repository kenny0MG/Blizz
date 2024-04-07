package org.blink.blizz.fragment.registrationFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration1Binding

class FirstRegistrationFragment: BaseFragment(){
    private var _binding: FragmentRegistration1Binding? = null;
    private val binding get() = _binding!!;

    private lateinit var mListener: Listener
    interface Listener{
        fun onNinth()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistration1Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.nextReg1Button.setOnClickListener {
            mListener.onNinth()


        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }

}