package project.stn991578659.moviemate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991578659.moviemate.adapter.MovieAdapter
import project.stn991578659.moviemate.data.Movie

class SearchMovieFragment : Fragment() {

    private lateinit var etSearchMovie: EditText
    private lateinit var rvSearchResults: RecyclerView
    private lateinit var movieAdapter: MovieAdapter // Custom RecyclerView adapter

    private val movieList = mutableListOf<Movie>() // Movie model list
    private val filteredList = mutableListOf<Movie>() // Search results

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_movie, container, false)

        // Initialize Views
        etSearchMovie = view.findViewById(R.id.et_search_movie)
        rvSearchResults = view.findViewById(R.id.rv_search_results)

        // Setup RecyclerView
//        movieAdapter = MovieAdapter(filteredList)
        rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
        rvSearchResults.adapter = movieAdapter

        // Load Movies and Handle Search
        loadMovies()
        handleSearch()

        return view
    }

    private fun loadMovies() {
        // Example: Fetch movies from Firebase Firestore
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
                filteredList.clear()
                filteredList.addAll(movieList)
                movieAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load movies!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleSearch() {
        etSearchMovie.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()

                filteredList.clear()
                if (query.isEmpty()) {
                    filteredList.addAll(movieList)
                } else {
                    filteredList.addAll(
                        movieList.filter {
                            it.title.lowercase().contains(query) ||
                                    it.genre.lowercase().contains(query)
                        }
                    )
                }
                movieAdapter.notifyDataSetChanged()
            }
        })
    }
}
