package project.stn991578659.moviemate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.stn991578659.moviemate.R
import project.stn991578659.moviemate.data.Movie

class MovieAdapter(private val movieList: List<Movie>,
                   private val OnMovieLongClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.tv_movie_title)
        val genreTextView: TextView = view.findViewById(R.id.tv_movie_genre)
        val yearTextView: TextView = view.findViewById(R.id.tv_movie_year)
        val ratingTextView: TextView = view.findViewById(R.id.tv_movie_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        holder.titleTextView.text = movie.title
        holder.genreTextView.text = "Genre: ${movie.genre}"
        holder.yearTextView.text = "Year: ${movie.releaseYear}"
        holder.ratingTextView.text = "Rating: ${movie.rating}"

        // Long click listener
        holder.itemView.setOnLongClickListener {
            OnMovieLongClickListener(movie.id)
            true
        }
    }

    override fun getItemCount(): Int = movieList.size
}
