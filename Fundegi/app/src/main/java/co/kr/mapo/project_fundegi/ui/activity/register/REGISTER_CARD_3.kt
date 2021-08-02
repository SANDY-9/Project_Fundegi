package co.kr.mapo.project_fundegi.ui.activity.register

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.databinding.ActivityRegisterCard3Binding
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class REGISTER_CARD_3 : AppCompatActivity() {

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private lateinit var binding : ActivityRegisterCard3Binding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProvider : ProcessCameraProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_card_3)
        requestPermission()
    }

    private fun init() {
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        val cameraProvider = ProcessCameraProvider.getInstance(this)
        cameraProvider.addListener(Runnable {
            this.cameraProvider = cameraProvider.get()
            bindPreview()
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview() {
        val preview : Preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }
        val cameraSelector : CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        imageCapture = ImageCapture.Builder()
            .setTargetRotation(binding.viewFinder.display.rotation)
            .build()
        this.cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
    }

    fun takeCapture(v: View) {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.KOREA
            ).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                }
                //이미지 uri 저장
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    cameraProvider.unbindAll()
                    val savedUri = Uri.fromFile(photoFile)
                    imageScan(savedUri)
                }
            }
        )
    }

    //캡쳐한 이미지 파일 만들기
    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    //캡쳐한 이미지 스캔(분석)하기
    private fun imageScan(savedUri : Uri) {
        with(binding) {
            gif.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                gif.visibility = View.GONE
                result.visibility = View.VISIBLE
            }, 1000L)
        }
        val image: InputImage
        try {
            image = InputImage.fromFilePath(this, savedUri)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    var resultNumber = ""
                    var resultExpire = ""
                    var analysis = false
                    for (block in visionText.textBlocks) {
                        var str = block.text
                        Log.e("[헤헷]", str)
                        // extract expiration period
                        if(str.contains("/")) {
                            val index = str.indexOf("/")
                            resultExpire = str.substring(index-2, index+2)
                        } else {
                            // extract card numbers
                            var blockCount = 0
                            if (!analysis) {
                                Log.e("[for]", str.indices.toString())
                                for (i in str.indices) {
                                    resultNumber = ""
                                    if (str[i] == ' ') blockCount++
                                    //correction
                                    Log.e("[str]", "$i : ${str[i]}")
                                    resultNumber += when (str[i]) {
                                        'I' -> '1'
                                        'D' -> '0'
                                        'S' -> '5'
                                        'b' -> '6'
                                        'g' -> '3'
                                        else -> str[i]
                                    }
                                    if (blockCount == 3 && i == str.lastIndex) analysis = true
                                }
                            }
                        }
                    }
                    binding.result.text = resultNumber +"\n"+ resultExpire
                    Log.e("[결과]", resultNumber +"\n"+ resultExpire)
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                    Log.e("[FAIL]", e.toString())
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //스레드 중단
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun requestPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        if (ActivityCompat.checkSelfPermission(this, cameraPermission)
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(cameraPermission), 100)
        else {
            init()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(grantResults[0]) {
            -1 -> {
                Toast.makeText(this, "카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                finish()
            }
            0 -> init()
        }
    }

    fun goBack(v: View) {
        finish()
    }
}