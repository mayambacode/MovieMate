package project.stn991578659.moviemate.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val releaseYear: Int,
    val genre: String,
    val rating: Double,
)
