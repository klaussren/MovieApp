package com.example.movieapp.ui.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemVideoMovieBinding
import com.example.movieapp.domain.model.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


class VideoMovieViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding= ItemVideoMovieBinding.bind(view)

    val youTubePlayerView:YouTubePlayerView  = binding.youtubePlayerView
    fun render(videoModel: Video){



        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {


                val defaultPlayerUiController =

                    DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                val videoId = videoModel.key
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        binding.tvTitleVideo.text = videoModel.name

    }

}