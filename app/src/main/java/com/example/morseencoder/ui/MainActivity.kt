package com.example.morseencoder.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.morseencoder.R
import com.example.morseencoder.TabPagerAdapter
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

class MainActivity : AppCompatActivity(),
    EncoderFragment.OnFragmentInteractionListener,
    EncoderHolderFragment.OnFragmentInteractionListener,
    SenderFragment.OnFragmentInteractionListener,
    SentFragment.OnFragmentInteractionListener,

    DecoderFragment.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {
        Timber.i("A fragment interacted with the main activity. Not doing anything with that info as of now, but it's good to know.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val tabPagerAdapter = TabPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = tabPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        Timber.i("MainActivity Created")
    }
}