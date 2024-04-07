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
import org.blink.blizz.databinding.FragmentRegistration8Binding
import org.blink.blizz.model.User


class EightRegistrationFragment: BaseFragment(){
    private var _binding: FragmentRegistration8Binding? = null;
    private val binding get() = _binding!!;

    private lateinit var mListener: Listener
    interface Listener{
        fun onSeventh(user:User)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistration8Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val user = bundle!!.getSerializable("sexUser") as User


        binding.materialButton.setOnClickListener {
            mListener.onSeventh(user)
        }


        binding.womanPartnerMaterialCard.setOnClickListener{
            binding.materialButton.isEnabled = true

            binding.manPartnerMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.gender)
            }
            binding.manPartnerImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))

            binding.manPartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))


            //Woman button
            binding.womanPartnerMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.primary)
            }

            binding.womanPartnerImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.primary))

            binding.womanPartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary))

            //woman user
            user.sexPartner = binding.womanPartnerTextView.text.toString()

            //everyOne
            binding.everyonePartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
        }


        binding.manPartnerMaterialCard.setOnClickListener{
            binding.materialButton.isEnabled = true

            binding.manPartnerMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.primary)
            }
            binding.manPartnerImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.primary))

            binding.manPartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary))

            //Man user
            user.sexPartner = binding.manPartnerTextView.text.toString()



            //Woman button
            binding.womanPartnerMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.gender)
            }

            binding.womanPartnerImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))

            binding.womanPartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))


            //everyOne
            binding.everyonePartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))

        }

        binding.everyonePartnerTextView.setOnClickListener {
            binding.materialButton.isEnabled = true


            //EveryOne user
            user.sexPartner = binding.everyonePartnerTextView.text.toString()


            binding.everyonePartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary))


            //Woman button
            binding.womanPartnerMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.gender)
            }

            binding.womanPartnerImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))

            binding.womanPartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))




            //Man button
            binding.manPartnerMaterialCard.apply {
                strokeColor = ContextCompat.getColor(context,R.color.gender)
            }
            binding.manPartnerImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))

            binding.manPartnerTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))


        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }

}