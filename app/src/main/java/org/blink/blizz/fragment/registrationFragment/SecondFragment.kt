package org.blink.blizz.fragment.registrationFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.util.Log
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration2Binding
import org.blink.blizz.model.User


@SuppressLint("StaticFieldLeak")
private var _binding: FragmentRegistration2Binding? = null;
private val binding get() = _binding!!;
class SecondRegistrationFragment(): BaseFragment(){
    private lateinit var userName:User

    private lateinit var mListener: Listener
    interface Listener{
        fun onThird(user:User)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistration2Binding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments


        val user = bundle!!.getSerializable("birthdayUser")

        userName = user as User


        binding.nameEditText.addTextChangedListener(EditTextNameWatcher(binding.nameEditText))

        binding.buttonRegName.setOnClickListener {
            userName.bio = binding.nameEditText.text.toString()
            mListener.onThird(userName)

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }


    class EditTextNameWatcher(private val view: View): TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()

            when(view.id){
                binding.nameEditText.id->{
                    binding.buttonRegName.isEnabled = text.length >= 2
                }
            }
        }
    }
}
