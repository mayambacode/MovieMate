package project.stn991578659.moviemate.data

import kotlinx.coroutines.flow.Flow

class OfflineMoviesRepository(override val movieDao: MovieDao) : MovieRepository  {

    override fun getAllMoviesStreams(): Flow<List<Movie>> = movieDao.getAllMovies()

    override fun getMovieStreams(id: Int): Flow<Movie?> = movieDao.getMovieById(id)

    override suspend fun insertMovie(movie: Movie) = movieDao.insert(movie)

    override suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)

    override suspend fun updateMovie(movie: Movie) = movieDao.update(movie)
}