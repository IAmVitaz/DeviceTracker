package com.vitaz.devicetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitaz.devicetracker.databinding.MainActivityBinding
import com.vitaz.devicetracker.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

//        supportActionBar?.hide()
//
//        binding = MainActivityBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)

    }
}
