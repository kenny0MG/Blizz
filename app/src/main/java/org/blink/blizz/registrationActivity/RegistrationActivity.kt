package org.blink.blizz.registrationActivity

import android.os.Bundle
import android.util.Log
import org.blink.blizz.R
import org.blink.blizz.common.viewModel.baseActivity.BaseActivity
import org.blink.blizz.fragment.registrationFragment.EightRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.FifthRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.FirstRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.FourthRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.NinthRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.SecondRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.SeventhRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.TenthRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.ThirdRegistrationFragment
import org.blink.blizz.fragment.registrationFragment.SixthRegistrationFragment
import org.blink.blizz.model.User


class RegistrationActivity : BaseActivity() ,
    FirstRegistrationFragment.Listener,
    SecondRegistrationFragment.Listener,
    ThirdRegistrationFragment.Listener,
    FourthRegistrationFragment.Listener,
    FifthRegistrationFragment.Listener,
    SixthRegistrationFragment.Listener,
    SeventhRegistrationFragment.Listener,
    EightRegistrationFragment.Listener,
    NinthRegistrationFragment.Listener,
    TenthRegistrationFragment.Listener
{

    private lateinit var mViewModel: RegistrationViewModel
    val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mViewModel = initViewModel()

        supportFragmentManager.commit(allowStateLoss = false) {
                addF<FirstRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,null)
                addToBackStack(null)
            }
    }

    override fun onSixth() {
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<SixthRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,null)
            addToBackStack(null)
        }
    }

    override fun onNinth() {
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<NinthRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,null)
            addToBackStack(null)
        }
    }


    override fun onTenth(phone:String) {
        bundle.putSerializable("phone", phone)
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<TenthRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,bundle)
            addToBackStack(null)
        }

    }

    override fun onThird(user:User) {
        bundle.putSerializable("nameUser", user)
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<ThirdRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,bundle)
            addToBackStack(null)
        }
    }

    override fun onSeventh(user:User) {
        bundle.putSerializable("sexPartnerUser", user)
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<SeventhRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,bundle)
            addToBackStack(null)
        }
    }

    override fun onSecond(user: User) {
        bundle.putSerializable("birthdayUser", user)
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<SecondRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,bundle)
            addToBackStack(null)
        }
    }

    override fun onFourth(user:User) {
        bundle.putSerializable("hobieUser", user)
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<FourthRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,bundle)
            addToBackStack(null)
        }
    }

    override fun onEight(user:User) {
        bundle.putSerializable("sexUser", user)
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<EightRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,bundle)
            addToBackStack(null)
        }
    }

    override fun onFifth(user: User) {
        bundle.putSerializable("bioUser", user)
        supportFragmentManager.commit(allowStateLoss = false) {
            replaceF<FifthRegistrationFragment>(R.id.frame_layout_registration, "main_profile_fragment" ,bundle)
            addToBackStack(null)
        }
    }


}









