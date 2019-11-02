package com.example.morseencoder.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.morseencoder.FlashlightHandler
import com.example.morseencoder.MorseCodeHandler
import com.example.morseencoder.R
import com.example.morseencoder.SharedViewModel
import com.example.morseencoder.databinding.FragmentSenderBinding
import timber.log.Timber
import kotlin.math.max

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SenderFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SenderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SenderFragment : Fragment() {

    private var secretMessage = "HI" // TODO: can I get rid of this?
    private lateinit var flashlightHandler: FlashlightHandler
    private val morseCodeHandler = MorseCodeHandler()

    private lateinit var binding : FragmentSenderBinding
    private lateinit var model: SharedViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun CountDownTimer.stopTransmission() {
        this.cancel()
        flashlightHandler.setFlashlightState(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the data binding utility,
        // then return the root of the binding as the view
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sender, container, false)

        binding.cancelSendBtn.setOnClickListener {view : View ->
            view.findNavController().navigate(R.id.action_senderFragment_to_sentFragment)
            flashlightHandler.timer.stopTransmission()
            model.messageCancelled.value = true
            Timber.i("transmission is being cancelled")
        }

        model = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java] //Note that "this" returns the activity, not the fragment. All fragments use same activity, so will get the same viewmodel
        } ?: throw Exception("Invalid Activity")

        // Set up observation relationships with LiveData
        model.lightOn.observe(this, Observer { lightState ->
            if(lightState == true) {binding.senderLightView.setImageResource(R.drawable.light_on)} else {binding.senderLightView.setImageResource(R.drawable.light_off) }
        })
        model.curCharacter.observe(this, Observer { curChar ->
            setHighlightedText(curChar)
        })
        model.curCharacter.value = 0

        // create flashlight handler for use sending message
        flashlightHandler = FlashlightHandler(context, model)


        // TODO: think about whether it's worthwhile to have secret message as a local var, as livedata and as the text string
        // TODO: this is a lot of code to just set the secret message to the input message or to "hi" if there isn't one
        val modelMessage = model.curSecretMessage.value.toString()
        if (modelMessage.isNotEmpty()) {
            binding.senderMessageDisplay.text = model.curSecretMessage.value.toString()
            secretMessage = model.curSecretMessage.value ?: secretMessage
        } else {
            binding.senderMessageDisplay.text = secretMessage
        }
        model.curSecretMessage.value = secretMessage


        Timber.i("SecretMessage is $secretMessage and the text I'm trying to modify is ${binding.senderMessageDisplay.text}")

        return binding.root
    }

    // Set text span to be colored red to display currently sending character
    private fun setHighlightedText(position: Int) {

        val redPos = if(position < secretMessage.length) {position} else {secretMessage.length}

        val whitePos = if(position < secretMessage.length) { max(position-1, 0) } else {    secretMessage.length}

        val spannableString = SpannableString(binding.senderMessageDisplay.text)

        val whiteTextSpan = ForegroundColorSpan(resources.getColor(R.color.defaultText)) //TODO try to use the non-depricated version that includes the theme for this and the white span below
        val redTextSpan = ForegroundColorSpan(resources.getColor(R.color.colorAccent))

        spannableString.setSpan(whiteTextSpan, whitePos, whitePos+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(redTextSpan, redPos, redPos+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.senderMessageDisplay.text = spannableString
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SenderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SenderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()

        // SEND MESSAGE //
        val messageToSend = morseCodeHandler.encodeString(secretMessage)
        flashlightHandler.sendMessage(view, messageToSend)

    }

    //should eventually pause or end message sending here, not just turn off camera
    // still needs to turn off the camera though
    override fun onPause() {
        super.onPause()
        //flashlightHandler.setFlashlightState(view, turnOn = false)
        flashlightHandler.setFlashlightState(turnOn = false)
        Timber.i("flashlight turning off")
    }
}
