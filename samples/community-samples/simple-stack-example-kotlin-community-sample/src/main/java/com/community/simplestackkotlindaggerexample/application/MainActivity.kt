package com.community.simplestackkotlindaggerexample.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.community.simplestackkotlindaggerexample.R
import com.community.simplestackkotlindaggerexample.databinding.ActivityMainBinding
import com.community.simplestackkotlindaggerexample.screens.home.HomeKey
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger

class MainActivity : AppCompatActivity(), SimpleStateChanger.NavigationHandler {
    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, R.id.container)

        Navigator.configure()
            .setStateChanger(SimpleStateChanger(this))
            .install(this, binding.container, History.single(HomeKey()))
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }
}