package org.blink.blizz.common.viewModel.baseFragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.blink.blizz.common.BlissApp

import org.blink.blizz.common.CommonViewModel
import org.blink.blizz.common.ModelFactory

abstract class BaseFragment:Fragment() {
    lateinit var commonViewModel: CommonViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        commonViewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
        commonViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it.let {
                //Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
    protected inline fun <reified T : ViewModel> initViewModel(): T =
        ViewModelProviders.of(this, ModelFactory(
            BlissApp(), commonViewModel,
            commonViewModel)
        ).get(T::class.java)



    //Получение прав доступа на считывание галерии

    fun setupPermissions(context: Context): Boolean {
        val permission1 =
            ContextCompat.checkSelfPermission(context as Activity, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permission2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(context as Activity, Manifest.permission.ACCESS_MEDIA_LOCATION)
        } else {
            PackageManager.PERMISSION_GRANTED
        }

        if (permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    arrayOf(
                        Manifest.permission.ACCESS_MEDIA_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                } else {
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }, 1
            )

            return false
        } else {
            return true
        }
    }


}