package com.example.morseencoder.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
//import com.example.morseencoder.EncoderModeNames
import com.example.morseencoder.R
import com.example.morseencoder.SharedViewModel
import com.example.morseencoder.databinding.FragmentEncoderBinding
import com.example.morseencoder.visibleStates
import kotlinx.android.synthetic.main.fragment_encoder.*
import timber.log.Timber
import java.lang.Integer.max

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EncoderFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EncoderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EncoderFragment : Fragment() {
    private lateinit var binding: FragmentEncoderBinding
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

        model = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java] //Note that "this" returns the activity, not the fragment. All fragments use same activity, so will get the same viewmodel
        } ?: throw Exception("Invalid Activity")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the data binding utility,
        // then return the root of the binding as the view
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_encoder, container, false)

        binding.lifecycleOwner = this

        binding.sharedViewModel = model
        // TODO doing this allows me to cut out some onclick listener code which I haven't yet done (see my udacity L5 notes)

        binding.sendMsgBtn.setOnClickListener {view : View ->
            view.findNavController().navigate(R.id.action_encoderFragment_to_senderFragment)
            Timber.i("send message button clicked")
        }

        binding.encoderModeSelection.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Timber.i("the encoder mode is set to nothing. this should not be the case")
            }

            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                val selection = binding.encoderModeSelection.selectedItem.toString()
                val selectionInt = model.getVisStateFromName(selection)
                model.encoderMode.value = selectionInt
                Timber.i("Encoder Mode changed to ${model.encoderMode}. Id is ${model.encoderMode.value.toString()}")
            }
        }

        // save text to viewModel after it's edited   // TODO: I'm not using the observer pattern here so maybe I need to rethink
        binding.encoderMessageInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                model.updateSecretMessage(encoderMessageInput.text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        return binding.root
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
         * @return A new instance of fragment EncoderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EncoderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
