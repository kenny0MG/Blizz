package org.blink.blizz.fragment.registrationFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration9Binding

class NinthRegistrationFragment: BaseFragment(){
    private var _binding: FragmentRegistration9Binding? = null;
    private val binding get() = _binding!!;


    private lateinit var mListener: Listener
    interface Listener{
        fun onTenth(phone:String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistration9Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegPhone.setOnClickListener {
            mListener.onTenth(binding.ccpPhone.selectedCountryCode() + binding.phoneEditText.text)

        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }

}