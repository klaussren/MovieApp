package com.example.movieapp.ui.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.model.Video
import com.example.movieapp.ui.view.holder.VideoMovieViewHolder

class VideoMovieAdapter(private val videoList:List<Video>):
    RecyclerView.Adapter<VideoMovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoMovieViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        return VideoMovieViewHolder(layoutInflater.inflate(R.layout.item_video_movie,parent,false))
    }

    override fun onBindViewHolder(holder: VideoMovieViewHolder, position: Int) {
       val item = videoList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int =videoList.size
}