package com.example.opencvwithkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsSeekBar
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Float.max

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    var srcBitmap: Bitmap? = null
    var dstBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        srcBitmap=BitmapFactory.decodeResource(this.resources, R.drawable.mountains)
        dstBitmap=srcBitmap!!.copy(srcBitmap!!.config,true)
        imageView.setImageBitmap(dstBitmap)

        sldSigma.setOnSeekBarChangeListener(this)
    }

    fun doBlur(){
        val sigma=max(0.1F, sldSigma.progress/10F)
        this.myBlur(srcBitmap!!,dstBitmap!!,sigma)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
        this.doBlur()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    fun btnFlip_click(view: View){
        this.myFlip(srcBitmap!!,srcBitmap!!)
        this.doBlur()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun myBlur(bitmapIn: Bitmap, bitmapOut: Bitmap, sigma: Float)
    external fun myFlip(bitmapIn: Bitmap, bitmapOut: Bitmap)


    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
