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
import com.google.firebase.firestore.FirebaseFirestore

class EditMovieFragment : Fragment() {

    private lateinit var etMovieTitle: EditText
    private lateinit var etGenre: EditText
    private lateinit var etReleaseYear: EditText
    private lateinit var etRating: EditText
    private lateinit var btnSaveMovie: Button

    private var movieId: String? = null // Assuming movies are identified by an ID

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

        // Get the movie ID from arguments (passed from RecyclerView or other fragments)
        movieId = arguments?.getString("MOVIE_ID")

        // Load the movie data for editing
        loadMovieData()

        // Save updated movie details
        btnSaveMovie.setOnClickListener {
            saveUpdatedMovie()
        }

        return view
    }

    private fun loadMovieData() {
        // Example: Using Firebase Firestore to fetch movie data
        val db = FirebaseFirestore.getInstance()

        movieId?.let {
            db.collection("movies").document(it)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        etMovieTitle.setText(document.getString("title"))
                        etGenre.setText(document.getString("genre"))
                        etReleaseYear.setText(document.getLong("releaseYear")?.toString())
                        etRating.setText(document.getDouble("rating")?.toString())
                    } else {
                        Toast.makeText(requireContext(), "Movie not found!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error loading movie!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveUpdatedMovie() {
        val updatedTitle = etMovieTitle.text.toString().trim()
        val updatedGenre = etGenre.text.toString().trim()
        val updatedReleaseYear = etReleaseYear.text.toString().toIntOrNull()
        val updatedRating = etRating.text.toString().toDoubleOrNull()

        if (updatedTitle.isEmpty() || updatedGenre.isEmpty() || updatedReleaseYear == null || updatedRating == null) {
            Toast.makeText(requireContext(), "Please fill all fields correctly!", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the movie in Firestore
        val db = FirebaseFirestore.getInstance()

        movieId?.let {
            val movieData = hashMapOf(
                "title" to updatedTitle,
                "genre" to updatedGenre,
                "releaseYear" to updatedReleaseYear,
                "rating" to updatedRating
            )

            db.collection("movies").document(it)
                .update(movieData as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Movie updated successfully!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Navigate back to the previous screen
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to update movie!", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
