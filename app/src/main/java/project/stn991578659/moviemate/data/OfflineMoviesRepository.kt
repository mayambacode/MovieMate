package project.stn991578659.moviemate.data

import kotlinx.coroutines.flow.Flow

class OfflineMoviesRepository(override val movieDao: MovieDao) : MovieRepository  {

    override fun getAllMoviesStreams(): Flow<List<Movie>> = movieDao.getAllMovies()

      override fun getMovieStreams(id: Int): Flow<Movie?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMovie(movie: Movie) {
        TODO("Not yet implemented")
    }
}