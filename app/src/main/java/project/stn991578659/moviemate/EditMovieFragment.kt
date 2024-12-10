package project.stn991578659.moviemate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991578659.moviemate.data.Movie
import project.stn991578659.moviemate.data.MovieDao
import project.stn991578659.moviemate.data.MovieDatabase

class EditMovieFragment : Fragment() {

    private lateinit var etMovieTitle: EditText
    private lateinit var etGenre: EditText
    private lateinit var etReleaseYear: EditText
    private lateinit var etRating: EditText
    private lateinit var btnSaveMovie: Button

    // Retrieve arguments passed via Navigation

//    private val args: EditMovieFragmentArgs by navArgs()

    private lateinit var movieDao: MovieDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_movie, container, false)

        // Initialize Views
        etMovieTitle = view.findViewById(R.id.et_edit_movie_title)
        etGenre = view.findViewById(R.id.et_edit_genre)
        etReleaseYear = view.findViewById(R.id.et_edit_release_year)
        etRating = view.findViewById(R.id.et_edit_rating)
        btnSaveMovie = view.findViewById(R.id.btn_save_movie)

        // Get the movie ID from arguments
        val db = MovieDatabase.getDatabase(requireContext())
        // Load the movie data for editing
//        loadMovieData(args.id)
//
//        // Save updated movie details
//        btnSaveMovie.setOnClickListener {
//            saveUpdatedMovie(args.id)
//        }

        return view
    }

    private fun loadMovieData(id: Int) {

//
//        val movie = movieDao.getMovieById(id)
//        etMovieTitle.setText(movie.title)
//        etGenre.setText(movie.genre)
//        etReleaseYear.setText(movie.releaseYear.toString())
//        etRating.setText(movie.rating.toString())

    }

    private suspend fun saveUpdatedMovie(id: Int) {
        val updatedMovie = Movie(
            id = id,
            title = etMovieTitle.text.toString(),
            genre = etGenre.text.toString(),
            releaseYear = etReleaseYear.text.toString().toInt(),
            rating = etRating.text.toString().toDouble()
        )


        movieDao.update(updatedMovie)
        Toast.makeText(requireContext(), "Movie updated successfully!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }


}

