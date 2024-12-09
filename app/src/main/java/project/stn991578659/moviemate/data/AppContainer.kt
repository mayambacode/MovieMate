package project.stn991578659.moviemate.data

import android.content.Context

interface AppContainer {
    val movieRepository: MovieRepository
}

class AppContainerImpl(private val context: Context) : AppContainer {

    override val movieRepository: MovieRepository by lazy {
        OfflineMoviesRepository(MovieDatabase.getDatabase(context).movieDao())
    }

}