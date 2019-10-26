package com.example.morseencoder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.morseencoder.ui.DecoderFragment
import com.example.morseencoder.ui.EncoderHolderFragment
import timber.log.Timber

private val TAB_TITLES = arrayOf(
    R.string.encoderScreenTitle,
    R.string.decoderScreenTitle
)

class TabPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        Timber.i("the position is $position")
        return if(position==0) {
            EncoderHolderFragment.newInstance("", "") // TODO try to cleanly get rid of these parms or make them something useful
        } else {
            DecoderFragment.newInstance("", "")
        }
    }

    override fun getCount(): Int {
        return 2 // there are just 2 tabs, 1 for Encoding and 1 for Decoding
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }


}