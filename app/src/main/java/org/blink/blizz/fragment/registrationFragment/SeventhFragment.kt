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
import org.blink.blizz.databinding.FragmentRegistration7Binding
import org.blink.blizz.model.User


@SuppressLint("StaticFieldLeak")
private var _binding: FragmentRegistration7Binding? = null;
private val binding get() = _binding!!;
class SeventhRegistrationFragment: BaseFragment(){

    private lateinit var mListener: Listener


    interface Listener{
        fun onFifth(user: User)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistration7Binding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val user = bundle!!.getSerializable("sexPartnerUser") as User

        binding.bioEditText.addTextChangedListener(EditTextBioWatcher(binding.bioEditText))

        binding.materialButtonRegOnFifth.setOnClickListener {
            user.bio = binding.bioEditText.text.toString()

            mListener.onFifth(user)
        }

        binding.scipBioTextView.setOnClickListener {
            mListener.onFifth(user)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }

    class EditTextBioWatcher(private val view: View): TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()

            when(view.id){
                binding.bioEditText.id->{
                    binding.materialButtonRegOnFifth.isEnabled = text.length >= 2
                }
            }
        }
    }
}