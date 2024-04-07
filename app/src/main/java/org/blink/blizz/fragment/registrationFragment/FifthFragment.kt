package org.blink.blizz.fragment.registrationFragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.exoplayer2.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import mpt.kurshach.blissmeet2.adapter.registrationAdapter.RegistrListAdapter
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.databinding.FragmentRegistration5Binding
import org.blink.blizz.model.GalleryItem
import org.blink.blizz.fragment.bottomSheetFragment.ChooseDataBottomSheet
import org.blink.blizz.model.Post
import org.blink.blizz.model.User
import org.blink.blizz.registrationActivity.RegistrationViewModel
import org.blink.blizz.utils.fetchImages
import org.blink.blizz.utils.fetchVideos
import java.io.File

class FifthRegistrationFragment: BaseFragment(),RegistrListAdapter.Listener{
    val mAdapter = RegistrListAdapter(this)
    {
    }

    private var _binding: FragmentRegistration5Binding? = null;
    private val binding get() = _binding!!;

    private lateinit var listImage:MutableList<Post>

    private lateinit var mListener: Listener

    private lateinit var mViewModel:RegistrationViewModel

    interface Listener{
        fun onSixth()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistration5Binding.inflate(inflater, container, false)

        return binding.root

    }

    @SuppressLint("SetTextI18n")
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = initViewModel()
        val bundle = this.arguments
        //val user = bundle!!.getSerializable("bioUser") as User
        val uriA = ArrayList<File>()




        binding.materialButtonImage.setOnClickListener {
            listImage.forEach {
                uriA += File(it.dataUrl)
            }
            lifecycleScope.launch {
                Log.d("uri",mViewModel.uploadImage(uriA,"user"))
            }
            mListener.onSixth()
        }

        listImage = mutableListOf<Post>()

        binding.buttonAddPhoto.setOnClickListener {
           selectMedia()
        }

        binding.addPhotoMaterialButton.setOnClickListener {
            selectMedia()
        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.imageVideoPersonRecyclerView)



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener=context as Listener

    }

    @SuppressLint("SetTextI18n")
    @OptIn(ExperimentalCoroutinesApi::class)
    fun selectMedia(){
        if (setupPermissions(requireContext())) {

            val data = mutableListOf<GalleryItem>().apply {
                addAll(fetchImages(requireActivity()))
                addAll(fetchVideos(requireActivity()))

                sortByDescending { it.dateCreated }
            }

            ChooseDataBottomSheet(data) {


                if ((it.isVideo && it.videoDuration > 1 * 15 * 1000L)) {
                    Toast.makeText(context, "Видео дольше 15 секунд", Toast.LENGTH_SHORT).show()
                    return@ChooseDataBottomSheet
                }
                else if(!it.isVideo){
                    listImage.add(Post(0,"",1,it.url))
                }
                else if(it.isVideo){
                    listImage.add(Post(0,"",2,it.url))
                }



                binding.addPhotoMaterialButton.visibility = View.VISIBLE
                binding.scrollable.fullScroll(ScrollView.FOCUS_DOWN)
                binding.sizeImage.text = "${listImage.size}/6"



                binding.imageVideoPersonRecyclerView.apply {
                    visibility = View.VISIBLE
                    adapter = mAdapter
                    layoutManager = LinearLayoutManager(context,
                        LinearLayoutManager.HORIZONTAL,false)
                }


                mAdapter.updateDataSet(listImage.reversed())

                if(listImage.size >= 3){
                    binding.materialButtonImage.isEnabled = true
                }




            }.show(requireActivity().supportFragmentManager, "ChooseDataBottomSheet")

        } else {
            Toast.makeText(context, "Разрешите доступ к медиа", Toast.LENGTH_LONG).show()
        }
    }


    @SuppressLint("SetTextI18n")
    override fun delete_image(image: Post) {
        listImage.remove(image)
        binding.sizeImage.text = "${listImage.size}/6"
        if(listImage.size < 3){
            binding.materialButtonImage.isEnabled = false
        }
        if(listImage.size == 0) {
            binding.buttonAddPhoto.visibility = View.VISIBLE
            binding.imageVideoPersonRecyclerView.visibility = View.GONE
            binding.addPhotoMaterialButton.visibility = View.GONE
        }
        mAdapter.updateDataSet(listImage)

    }


}
