package project.stn991578659.moviemate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Double,
)
