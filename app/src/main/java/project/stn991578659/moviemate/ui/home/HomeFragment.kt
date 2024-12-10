package project.stn991578659.moviemate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import project.stn991578659.moviemate.R
import project.stn991578659.moviemate.adapter.MovieAdapter
import project.stn991578659.moviemate.data.Movie
import project.stn991578659.moviemate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewMovies = binding.recyclerViewMovies

        recyclerViewMovies.layoutManager = LinearLayoutManager(requireContext())

        val data = ArrayList<Movie>()

        data.add(Movie(0, "The Dark Knight", 2008, "Action", 9.0))
        data.add(Movie(1, "Inception", 2010, "Sci-Fi", 8.8))
        data.add(Movie(2, "The Hangover", 2009, "Comedy", 7.7))

        val adapter = MovieAdapter(data)
        recyclerViewMovies.adapter = adapter


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}