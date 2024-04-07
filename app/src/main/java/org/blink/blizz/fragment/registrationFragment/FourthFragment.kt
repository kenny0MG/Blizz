package org.blink.blizz.fragment.registrationFragment

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import org.blink.blizz.R
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration4Binding
import org.blink.blizz.model.User

class FourthRegistrationFragment: BaseFragment(){
    private var _binding: FragmentRegistration4Binding? = null;
    private val binding get() = _binding!!;


    private lateinit var mListener: Listener


    interface Listener{
        fun onEight(user: User)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistration4Binding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val user = bundle!!.getSerializable("hobieUser") as User

        binding.materialButton.setOnClickListener {
            mListener.onEight(user)
        }

        binding.womanMaterialCard.setOnClickListener{
            binding.materialButton.isEnabled = true


            binding.manMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.gender)
            }
            binding.manImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))

            binding.manTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))


            //woman user
            user.sex = binding.womanTextView.text.toString()


            //Woman button
            binding.womanMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.primary)
            }

            binding.womanImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.primary))

            binding.womanTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary))
        }


        binding.manMaterialCard.setOnClickListener{
            binding.materialButton.isEnabled = true

            binding.manMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.primary)
            }
            binding.manImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.primary))

            binding.manTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary))


            //man user
            user.sex = binding.manTextView.text.toString()



            //Woman button
            binding.womanMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.gender)
            }

            binding.womanImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))

            binding.womanTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))


        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }
}
