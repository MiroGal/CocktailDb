package com.mirogal.cocktail.ui.main.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_pager.*
import kotlinx.android.synthetic.main.fragment_test.*
import java.util.*


class TestFragment : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_test
    override val viewModel: MainViewModel by viewModels()

    private var listener: OnFragmentActionListener? = null
    private var number = 0
    private var message: String? = null

    private val logTag = "${TestFragment::class.java.simpleName} $number"


    companion object {
        private const val NUMBER_KEY = "number_key"
        private const val MESSAGE_KEY = "message_key"

        fun newInstance(number: Int, message: String?): TestFragment {
            val fragment = TestFragment()
            val bundle = Bundle()
            bundle.putInt(NUMBER_KEY, number)
            bundle.putString(MESSAGE_KEY, message)
            fragment.arguments = bundle
            return fragment
        }
    }


    // Fragment is added


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(logTag, "onAttach")

        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }

        number = arguments?.getSerializable(NUMBER_KEY) as Int
        message = arguments?.getSerializable(MESSAGE_KEY) as String?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(logTag, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(logTag, "onViewCreated")

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_filter_label)

        if (message != null && message!!.isNotEmpty()) {
            val color = resources.getColor(R.color.background_primary)
            val contrastColor = getContrastColor(color)
            test_fragment_background.setBackgroundColor(color)
            btn_next.setTextColor(contrastColor)
            btn_next.text = "$number\n$message"
        } else {
            val color = randomColor()
            val contrastColor = getContrastColor(color)
            test_fragment_background.setBackgroundColor(color)
            btn_next.setTextColor(contrastColor)
            btn_next.text = "$number"
        }

        btn_next.setOnClickListener { listener?.onTestActionButtonClick(number, message) }
    }

    override fun onStart() {
        super.onStart()
        Log.d(logTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(logTag, "onResume")
    }


    // Fragment is active


    override fun onPause() {
        Log.d(logTag, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(logTag, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(logTag, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(logTag, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(logTag, "onDetach")
        super.onDetach()
    }


    // Fragment is destroyed


    private fun randomColor(): Int {
        val rnd = Random()
        return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        // return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun getContrastColor(color: Int): Int {
        val y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000.toDouble()
        return if (y >= 128) Color.BLACK else Color.WHITE
    }


    interface OnFragmentActionListener {
        fun onTestActionButtonClick(number: Int, message: String?)
    }

}
