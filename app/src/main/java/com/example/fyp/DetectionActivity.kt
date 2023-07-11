//package com.example.fyp
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.google.ar.core.HitResult
//import com.google.ar.sceneform.rendering.Renderable
//import com.otaliastudios.cameraview.CameraView
//import kotlinx.android.synthetic.main.activity_detection.*
//
//class DetectionActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detection)
//        val audio = Audio(this,"en","GB")
//        val video = Video()
//        val detector = Detector()
//        val classifier = Classifier()
//        val frameProcessor = FrameProcessor()
//        val view = findViewById<CameraView>(R.id.cameraView)
//        detector.load_model()
//        classifier.load_model(applicationContext)
//        video.video_capture(view,this)
//        view.addFrameProcessor {
//            val image = frameProcessor.prepare_frame(it)
//            detector.detect(image)
//            classifier.classify(image){  result ->
//                tvDetectedObject.text = result
//                audio.generate_audio(result)
//            }
//        }
//    }
//
//
//
//}


package com.example.fyp
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.example.fyp.Audio
import com.example.fyp.Classifier
import com.example.fyp.Detector
import com.example.fyp.FrameProcessor
import com.example.fyp.R
import com.example.fyp.Video
import com.otaliastudios.cameraview.CameraView
import kotlinx.android.synthetic.main.activity_detection.tvDetectedObject

class DetectionActivity : AppCompatActivity() {
    private lateinit var audio: Audio
    private lateinit var video: Video
    private lateinit var detector: Detector
    private lateinit var classifier: Classifier
    private lateinit var frameProcessor: FrameProcessor
    private lateinit var view: CameraView
    private lateinit var boundingBoxView: BoundingBoxView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection)

        audio = Audio(this, "en", "GB")
        video = Video()
        detector = Detector()
        classifier = Classifier()
        frameProcessor = FrameProcessor()
        view = findViewById(R.id.cameraView)
        boundingBoxView = findViewById(R.id.boundingBoxView)
        //tvDetectedObject = findViewById(R.id.tvDetectedObject)

        detector.load_model()
        classifier.load_model(applicationContext)
        video.video_capture(view, this)

        view.addFrameProcessor {
            val image = frameProcessor.prepare_frame(it)
            detector.detect(image) { detectedObjects ->
                val result = if (detectedObjects.isNotEmpty()) {
                    val boundingBox = detectedObjects[0].boundingBox // Anggap objek pertama sebagai contoh
                    boundingBoxView.setBoundingBox(boundingBox)
                    detectedObjects[0].labels.joinToString { it.text }
                } else {
                    boundingBoxView.setBoundingBox(null)
                    ""
                }
                runOnUiThread {
                    tvDetectedObject.text = result
                    audio.generate_audio(result)
                }
            }
        }
    }
}

class BoundingBoxView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }
    private var boundingBox: Rect? = null

    fun setBoundingBox(rect: Rect?) {
        boundingBox = rect
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        boundingBox?.let {
            canvas.drawRect(it, paint)
        }
    }
}