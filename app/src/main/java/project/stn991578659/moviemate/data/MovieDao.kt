package project.stn991578659.moviemate.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

//    @Insert(onConflict =  OnConflictStrategy.IGNORE)
//    fun insert(title: String, year: Int, genre: String, rating: Double)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): Movie

    @Query("SELECT * FROM movies WHERE title = :title")
    fun getMovieByTitle(title: String): Movie

    @Query("SELECT * FROM movies WHERE year = :year")
    fun getMoviesByYear(year: Int): List<Movie>

    @Query("SELECT * FROM movies WHERE genre = :genre")
    fun getMoviesByGenre(genre: String): List<Movie>

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getAllMovies(): Flow<List<Movie>>

}