package com.example.morseencoder

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morseencoder.screens.EncoderFragment
import com.example.morseencoder.screens.SenderFragment
import com.example.morseencoder.screens.SentFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(),
    EncoderFragment.OnFragmentInteractionListener,
    SenderFragment.OnFragmentInteractionListener,
    SentFragment.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {
        Timber.i("A fragment interacted with the main activity. Not doing anything with that info as of now, but it's good to know.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.i("MainActivity Created")
    }
}