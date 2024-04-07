package org.blink.blizz.countryCodePicker.bottomFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.blink.blizz.countryCodePicker.CountryPickerItem
import org.blink.blizz.countryCodePicker.codePickerAdapter.CountryListAdapter
import org.blink.blizz.databinding.FragmentCountryPickerBinding

class BottomCountryPickerFragment : BottomSheetDialogFragment(),
    CountryListAdapter.OnItemClickListener {
    private var binding: FragmentCountryPickerBinding? = null
    private var listener: OnClickItemListener? = null
    private var countryList = ArrayList<CountryPickerItem>()
    private var countryListAdapter: CountryListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryPickerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        countryListAdapter = CountryListAdapter(this)
        binding?.rvCountryList?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryListAdapter
        }
        countryListAdapter?.newList(countryList)
        countryListAdapter?.notifyDataSetChanged()
        binding?.etSearch?.doOnTextChanged { text, _, _, _ ->
            countryListAdapter?.filter?.filter(text)
        }
    }

    fun setClickListener(listener: OnClickItemListener, list: List<CountryPickerItem>) {
        this.listener = listener
        this.countryList = list as ArrayList<CountryPickerItem>
    }

    interface OnClickItemListener {
        fun onCountryItemClick(item: CountryPickerItem?)
    }

    override fun onSelectItem(item: CountryPickerItem) {
        listener?.onCountryItemClick(item)
    }
}