package com.vanka.devbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        LoadFrag(HomeFrag())
        findViewById<BottomNavigationView>(R.id.nav).setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.Home->{
                    LoadFrag(HomeFrag())
                    true

                }
                R.id.add->{
                    LoadFrag(UploadFrag())
                    true
                }




                else -> {
                    LoadFrag(HomeFrag())
                    true
                }
            }

        }
    }
    private fun LoadFrag(fragment: Fragment) {
        var load = supportFragmentManager.beginTransaction()
        load.replace(R.id.cont,fragment)
        load.commit()
    }
}