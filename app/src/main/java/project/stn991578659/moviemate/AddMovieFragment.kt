package project.stn991578659.moviemate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.stn991578659.moviemate.data.Movie
import project.stn991578659.moviemate.data.MovieDatabase
import project.stn991578659.moviemate.databinding.FragmentAddMovieBinding

class AddMovieFragment  : Fragment() {

    private var _binding : FragmentAddMovieBinding? = null
    private  val binding get() = _binding!!


    private lateinit var movieDatabase: MovieDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the Room database
        movieDatabase = MovieDatabase.getDatabase(requireContext())

        // Handle Add Movie Button Click
        binding.btnSubmitMovie.setOnClickListener {
            val title = binding.etMovieTitle.text.toString().trim()
            val genre = binding.etGenre.text.toString().trim()
            val releaseYear = binding.etReleaseYear.text.toString().trim()
            val rating = binding.etRating.text.toString().trim()

            if (title.isEmpty() || genre.isEmpty() || releaseYear.isEmpty() || rating.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rating.toDoubleOrNull() == null || rating.toDouble() !in 1.0..10.0) {
                Toast.makeText(requireContext(), "Rating must be between 1 and 10", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save the movie to database (to be implemented)
            saveMovieToDatabase(title, genre, releaseYear.toInt(), rating.toDouble())

            // Clear the fields
            binding.etMovieTitle.text?.clear()
            binding.etGenre.text?.clear()
            binding.etReleaseYear.text?.clear()
            binding.etRating.text?.clear()

            Toast.makeText(requireContext(), "Movie added successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveMovieToDatabase(title: String, genre: String, year: Int, rating: Double) {
        val newMovie = Movie(
            title = title,
            genre = genre,
            releaseYear = year,
            rating = rating
        )
        // Use a coroutine to perform the database operation
        lifecycleScope.launch(Dispatchers.IO) {
            movieDatabase.movieDao().insert(newMovie)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}