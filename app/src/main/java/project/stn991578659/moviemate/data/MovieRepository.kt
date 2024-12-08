package project.stn991578659.moviemate.data

import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    abstract val movieDao: Any

    /**
     * Retrieve all movies from the given data source.
     */
    fun getAllMoviesStreams(): Flow<List<Movie>>


    /**
     * Retrieve an itesxm from the given data source that matches with the [id].
     */
    fun getMovieStreams(id: Int): Flow<Movie?>

    /**
     * Insert movie in the data source
     */
    suspend fun insertMovie(movie: Movie)

    /**
     * Delete movie from the data source
     */
    suspend fun deleteMovie(movie: Movie)

    /**
     * Update movie in the data source
     */
    suspend fun updateMovie(movie: Movie)
}