package org.blink.blizz.fragment.registrationFragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import org.blink.blizz.R
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.flexbox.*
import org.blink.blizz.adapter.HobieAdapter
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration3Binding
import org.blink.blizz.model.Hobie
import org.blink.blizz.model.User
import org.blink.blizz.model.sampleChipData
import org.blink.blizz.utils.getPersonality


private lateinit var hobieCount:MutableList<Hobie>
class ThirdRegistrationFragment: BaseFragment(){
    private var _binding: FragmentRegistration3Binding? = null;
    private val binding get() = _binding!!;

    private lateinit var mListener: Listener

    val listAdapter = HobieAdapter(){

        if(!hobieCount.contains(it)){
            hobieCount.add(it)
        }
        else{
            hobieCount.remove(it)
        }
        if(hobieCount.size < 3){
            binding.textViewTotalCount.setBackgroundResource(R.drawable.bg_edit_text_unfocused);
            binding.materialButton.isEnabled = false

        }else if(hobieCount.size >= 3){
            binding.textViewTotalCount.setBackgroundResource(R.drawable.bg_edit_text_focus);
            binding.materialButton.isEnabled = true
        }

        binding.textViewTotalCount.text = "${hobieCount.size}/10"


    }

    interface Listener{
        fun onFourth(user: User)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistration3Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        hobieCount = ArrayList<Hobie>()
        val bundle = this.arguments
        val user = bundle!!.getSerializable("nameUser") as User


        binding.materialButton.setOnClickListener {
            saveHobie(user)
            mListener.onFourth(user)

        }

        binding.filteringEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val userText = s?.toString()

                if (userText.isNullOrBlank()) {
                    initRecyclerView()
                } else {

                    val filteredUserList = sampleChipData.filter {
                        it.name.lowercase().contains(userText.lowercase())
                    }

                    listAdapter.submitList(filteredUserList)


                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerViewHobie.setHasFixedSize(true);
        binding.recyclerViewHobie.setItemViewCacheSize(20);
        binding.recyclerViewHobie.setDrawingCacheEnabled(true);
        binding.recyclerViewHobie.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        val flexLayoutManager: FlexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            flexWrap = FlexWrap.WRAP
        }
        val itemDecoration = FlexboxItemDecoration(context)
        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.bg_space))
        itemDecoration.setOrientation(FlexboxItemDecoration.VERTICAL)

        binding.recyclerViewHobie.layoutManager = flexLayoutManager
        binding.recyclerViewHobie.adapter = listAdapter
        binding.recyclerViewHobie.addItemDecoration(itemDecoration)

        listAdapter.submitList(sampleChipData)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }


    fun saveHobie(user: User){
        var numberDouble = 0.00
        val userMap = mutableMapOf<String, Hobie>()

        hobieCount.forEach { hobie ->
            numberDouble += hobie.number
            userMap[hobie.id] = hobie
        }

        user.hobie = userMap
        user.personality = getPersonality(numberDouble, hobieCount.size)
    }

}

