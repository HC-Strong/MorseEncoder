package com.example.morseencoder.screens

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.morseencoder.R
import com.example.morseencoder.databinding.FragmentSenderBinding
import timber.log.Timber

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
    private var flashLightOn = false

    private lateinit var binding : FragmentSenderBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the data binding utility,
        // then return the root of the binding as the view
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sender, container, false)

        binding.tempFinishSendingBtn.setOnClickListener {view : View ->
            view.findNavController().navigate(R.id.action_senderFragment_to_sentFragment)
            Timber.i("temp finish sending button clicked")
        }

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

    private fun cameraAvailable(context : Context) : Boolean {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun getCameraId(cameraManager: CameraManager) : String {
        // how does as CameraManager differ from : CameraManger?
        return cameraManager.cameraIdList[0]
    }

    private fun toggleFlashlight(view: View?, context: Context?) {
        Timber.v("logging view here to suppress warnings: $view")
        val cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        if (!flashLightOn) {
            Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(getCameraId(cameraManager), true)
            flashLightOn = true
        } else {
            Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(getCameraId(cameraManager), false)
            flashLightOn = false
        }
    }

    override fun onResume() {
        super.onResume()
        toggleFlashlight(view, context)
        Timber.i("flashlight turning on")
    }

    //should eventually pause or end message sending here, not just turn off camera
    // still needs to turn off the camera though
    override fun onPause() {
        super.onPause()
        toggleFlashlight(view, context)
        Timber.i("flashlight turning off")
    }
}
