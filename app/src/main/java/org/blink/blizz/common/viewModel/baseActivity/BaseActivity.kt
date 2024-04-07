package org.blink.blizz.common.viewModel.baseActivity

import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import org.blink.blizz.common.CommonViewModel


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import org.blink.blizz.R
import org.blink.blizz.common.BlissApp
import org.blink.blizz.common.ModelFactory
import org.blink.blizz.registrationActivity.RegistrationActivity


abstract class BaseActivity: AppCompatActivity() {


    lateinit var commonViewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commonViewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
        commonViewModel.errorMessage.observe(this, Observer {
            it.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
    protected inline fun <reified T : ViewModel> initViewModel(): T =
        ViewModelProviders.of(this, ModelFactory(
            BlissApp(), commonViewModel,
            commonViewModel)
        ).get(T::class.java)

    fun goToLogin() {
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }


    companion object {
        const val TAG = "BaseActivity"
    }






    inline fun <reified T : Fragment> FragmentTransaction.addF(
        containerId: Int,
        tag: String? = null,
        args: Bundle? = null,
    ): FragmentTransaction {
        val fragment = T::class.java.newInstance()
        fragment.arguments = args

        add(containerId, fragment, tag)

        return this
    }

    inline fun <reified T : Fragment> FragmentTransaction.replaceF(
        containerId: Int,
        tag: String? = null,
        args: Bundle? = null,
    ): FragmentTransaction {
        val fragment = T::class.java.newInstance()
        fragment.arguments = args

        replace(containerId, fragment, tag)

        return this
    }
    fun FragmentManager.commit(
        allowStateLoss: Boolean = false,
        block: FragmentTransaction.() -> Unit
    ) {
        val transaction = beginTransaction()
        transaction.block()

        if (allowStateLoss) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }

}