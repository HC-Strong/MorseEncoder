package com.example.morseencoder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.morseencoder.ui.DecoderFragment
import com.example.morseencoder.ui.EncoderHolderFragment
import timber.log.Timber

class TabPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        Timber.i("the position is $position")
        return if(position==0) {
            EncoderHolderFragment.newInstance("h", "i")
        } else {
            DecoderFragment.newInstance("H", "I")
        }
    }

    override fun getCount(): Int {
        return 2 // there are just 2 tabs, 1 for Encoding and 1 for Decoding
    }



}