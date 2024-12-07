package project.stn991578659.moviemate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991578659.moviemate.data.Movie


class StatisticsFragment : Fragment() {

    private lateinit var tvTotalMovies: TextView
    private lateinit var tvAverageRating: TextView
    private lateinit var tvGenreCount: TextView
    private lateinit var tvOldestMovie: TextView
    private lateinit var tvNewestMovie: TextView

    private val movieList = mutableListOf<Movie>() // Movie model list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        // Initialize Views
        tvTotalMovies = view.findViewById(R.id.tv_total_movies)
        tvAverageRating = view.findViewById(R.id.tv_average_rating)
        tvGenreCount = view.findViewById(R.id.tv_genre_count)
        tvOldestMovie = view.findViewById(R.id.tv_oldest_movie)
        tvNewestMovie = view.findViewById(R.id.tv_newest_movie)

        // Load Movies and Calculate Statistics
        loadMovies()

        return view
    }

    private fun loadMovies() {
        val db = FirebaseFirestore.getInstance()

        db.collection("movies")
            .get()
            .addOnSuccessListener { result ->
                movieList.clear()
                for (document in result) {
                    val movie = Movie(
                        id = document.id.toInt(),
                        title = document.getString("title") ?: "",
                        genre = document.getString("genre") ?: "",
                        releaseYear = document.getLong("releaseYear")?.toInt() ?: 0,
                        rating = document.getDouble("rating") ?: 0.0
                    )
                    movieList.add(movie)
                }
                calculateStatistics()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load movies!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun calculateStatistics() {
        if (movieList.isEmpty()) {
            tvTotalMovies.text = "Total Movies: 0"
            tvAverageRating.text = "Average Rating: N/A"
            tvGenreCount.text = "Genres: N/A"
            tvOldestMovie.text = "Oldest Movie: N/A"
            tvNewestMovie.text = "Newest Movie: N/A"
            return
        }

        // Total Movies
        tvTotalMovies.text = "Total Movies: ${movieList.size}"

        // Average Rating
        val averageRating = movieList.map { it.rating }.average()
        tvAverageRating.text = "Average Rating: %.2f".format(averageRating)

        // Genre Count
        val genreCount = movieList.groupingBy { it.genre }.eachCount()
        val genreCountText = genreCount.entries.joinToString { "${it.key}: ${it.value}" }
        tvGenreCount.text = "Genres: $genreCountText"

        // Oldest and Newest Movie
        val oldestMovie = movieList.minByOrNull { it.releaseYear }
        val newestMovie = movieList.maxByOrNull { it.releaseYear }

        tvOldestMovie.text = "Oldest Movie: ${oldestMovie?.title ?: "N/A"} (${oldestMovie?.releaseYear ?: "N/A"})"
        tvNewestMovie.text = "Newest Movie: ${newestMovie?.title ?: "N/A"} (${newestMovie?.releaseYear ?: "N/A"})"
    }
}
