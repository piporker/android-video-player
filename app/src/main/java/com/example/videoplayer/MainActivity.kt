package com.example.videoplayer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var btnSelectVideo: Button
    private lateinit var btnReplay: Button
    private lateinit var btnPlayPause: Button
    private lateinit var btnFullScreen: Button

    private val PICK_VIDEO_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi views
        videoView = findViewById(R.id.videoView)
        btnSelectVideo = findViewById(R.id.btnSelectVideo)
        btnReplay = findViewById(R.id.btnReplay)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnFullScreen = findViewById(R.id.btnFullScreen)

        // Setup VideoView dengan MediaController
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Listener untuk tombol pilih video
        btnSelectVideo.setOnClickListener {
            openVideoPicker()
        }

        // Listener untuk tombol replay
        btnReplay.setOnClickListener {
            videoView.seekTo(0)
            videoView.start()
        }

        // Listener untuk tombol play/pause
        btnPlayPause.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                btnPlayPause.text = "Play"
            } else {
                videoView.start()
                btnPlayPause.text = "Pause"
            }
        }

        // Listener untuk fullscreen
        btnFullScreen.setOnClickListener {
            if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
    }

    private fun openVideoPicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_VIDEO_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val videoUri: Uri? = data.data
            if (videoUri != null) {
                videoView.setVideoURI(videoUri)
                videoView.requestFocus()
                videoView.start()
                btnPlayPause.text = "Pause"
            }
        }
    }
}