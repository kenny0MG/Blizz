package org.blink.blizz.fragment.registrationFragment

import android.R.attr.key
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import org.blink.blizz.R
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration10Binding
import org.blink.blizz.model.User
import org.blink.blizz.registrationActivity.RegistrationViewModel
import java.text.SimpleDateFormat


@SuppressLint("StaticFieldLeak")
private lateinit var dayEditTextView: EditText
@SuppressLint("StaticFieldLeak")
private lateinit var monthEditTextView: EditText
@SuppressLint("StaticFieldLeak")
private lateinit var yearEditTextView: EditText


@SuppressLint("StaticFieldLeak")
private var _binding: FragmentRegistration10Binding? = null;
private val binding get() = _binding!!;
class TenthRegistrationFragment(): BaseFragment(){

    private lateinit var mListener: Listener


    interface Listener{
        fun onSecond(user: User)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegistration10Binding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments


        val phone = bundle!!.getString("phone")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        /*
            Инициализация переменных
         */
        dayEditTextView = view.findViewById(R.id.day_editTextView)
        monthEditTextView = view.findViewById(R.id.month_editTextView)
        yearEditTextView = view.findViewById(R.id.year_editTextView)



        dayEditTextView.addTextChangedListener(EditTextWatcher(dayEditTextView))
        monthEditTextView.addTextChangedListener(EditTextWatcher(monthEditTextView))
        yearEditTextView.addTextChangedListener(EditTextWatcher(yearEditTextView))





        binding.buttonMaterialRegistration10.setOnClickListener {
            val text = binding.dayEditTextView.text.toString()+"/"+ binding.monthEditTextView.text.toString()+"/"+ binding.yearEditTextView.text.toString()
            val date = formatter.parse(text)
            val birthday = formatter.format(date!!).toString()

            val user = User(phone = phone.toString(), birthday = birthday)
            mListener.onSecond(user)
        }






    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }
}



/*
    Работа с EditText
*/
class EditTextWatcher(private val view: View):TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {

        val text = p0.toString()

        when(view.id){
            R.id.day_editTextView -> {
                checkButtonState()
                if(text.length == 2){
                    monthEditTextView.requestFocus()
                }
            }
            R.id.month_editTextView -> {
                checkButtonState()
                if(text.length == 2){
                    yearEditTextView.requestFocus()
                }
                else if (text.isEmpty()) {
                    dayEditTextView.requestFocus()
                }
            }
            R.id.year_editTextView -> {
                checkButtonState()
                if(text.isEmpty()){
                    monthEditTextView.requestFocus()
                }
            }
        }
    }


    fun checkButtonState() {
        val isEditText1Filled = binding.dayEditTextView.text.length == 2
        val isEditText2Filled = binding.monthEditTextView.text.length == 2
        val isEditText3Filled = binding.yearEditTextView.text.length == 4

        binding.buttonMaterialRegistration10.isEnabled = isEditText1Filled && isEditText2Filled && isEditText3Filled
    }
}

