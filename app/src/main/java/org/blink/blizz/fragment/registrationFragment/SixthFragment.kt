package org.blink.blizz.fragment.registrationFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration6Binding
import org.blink.blizz.model.User


@SuppressLint("StaticFieldLeak")
private var _binding: FragmentRegistration6Binding? = null;
private val binding get() = _binding!!;
class SixthRegistrationFragment: BaseFragment(){

    private lateinit var mListener: Listener


    interface Listener{
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistration6Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialButton.setOnClickListener {
        }


        binding.firstNumberEditText.addTextChangedListener(EditTextWatcher(binding.firstNumberEditText))
        binding.secondNumberEditText.addTextChangedListener(EditTextWatcher(binding.secondNumberEditText))
        binding.thirdNumberEditText.addTextChangedListener(EditTextWatcher(binding.thirdNumberEditText))
        binding.fourthNumberEditText.addTextChangedListener(EditTextWatcher(binding.fourthNumberEditText))
        binding.fifthNumberEditText.addTextChangedListener(EditTextWatcher(binding.fifthNumberEditText))
        binding.sixthNumberEditText.addTextChangedListener(EditTextWatcher(binding.sixthNumberEditText))



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }


    class EditTextWatcher(private val view: View):TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()

            when(view.id){
                binding.firstNumberEditText.id ->{
                    if(text.length == 1){
                        binding.secondNumberEditText.requestFocus()
                    }
                }

                binding.secondNumberEditText.id ->{
                    if(text.length == 1){
                        binding.thirdNumberEditText.requestFocus()
                    }
                    else{
                        binding.firstNumberEditText.requestFocus()
                    }
                }

                binding.thirdNumberEditText.id ->{
                    if(text.length == 1){
                        binding.fourthNumberEditText.requestFocus()
                    }
                    else{
                        binding.secondNumberEditText.requestFocus()
                    }
                }

                binding.fourthNumberEditText.id ->{
                    if(text.length == 1){
                        binding.fifthNumberEditText.requestFocus()
                    }
                    else{
                        binding.thirdNumberEditText.requestFocus()
                    }
                }

                binding.fifthNumberEditText.id ->{
                    if(text.length == 1){
                        binding.sixthNumberEditText.requestFocus()
                    }
                    else{
                        binding.fourthNumberEditText.requestFocus()
                    }
                }
                binding.sixthNumberEditText.id ->{
                    if(text.isEmpty()){
                        binding.fifthNumberEditText.requestFocus()
                    }
                }
            }
        }

    }
}