package org.blink.blizz

import android.os.Bundle
import org.blink.blizz.common.setupAuthGuard
import org.blink.blizz.common.viewModel.baseActivity.BaseActivity
import org.blink.blizz.fragment.cardFragment.CardFragment
import org.blink.blizz.fragment.chat.ChatFragment
import org.blink.blizz.fragment.settings.SettingsFragment
import org.blink.blizz.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       setupAuthGuard {
           supportFragmentManager.commit(allowStateLoss = false) {
               replaceF<CardFragment>(R.id.frame_layout_main, "card_fragment" ,null)
               addToBackStack(null)
           }
       }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.itemIconTintList = null;
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.nav_main ->{
                    supportFragmentManager.commit(allowStateLoss = false) {
                        replaceF<CardFragment>(R.id.frame_layout_main, "card_fragment" ,null)
                        addToBackStack(null)
                    }
                }
                R.id.nav_chat ->  {
                    supportFragmentManager.commit(allowStateLoss = false) {
                        replaceF<ChatFragment>(R.id.frame_layout_main, "chat_fragment" ,null)
                        addToBackStack(null)
                    }
                }
                R.id.nav_person ->  {
                    supportFragmentManager.commit(allowStateLoss = false) {
                        replaceF<SettingsFragment>(R.id.frame_layout_main,
                            "settings_profile_fragment",
                            null)
                        addToBackStack(null)
                    }
                }


                else ->{



                }

            }

            true


        }
    }
}
