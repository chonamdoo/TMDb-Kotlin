# TMDb-Kotlin
A simple Android client for The Movie DB

This project is an Android app which displays data from [The Movie Database](https://www.themoviedb.org/) The Movie Database API.

# Features
- Get Movies
  - Get the Popular Movies
  - Get the Top Rated Movies
  - Get the Upoming
  - Get the Now Playing
- Get Tv shows
  - Get the Popular Tv shows
  - Get the Top Rated
  - Get the On Tv
  - Get the Airing Today
- Show the movie detail and tv shows details
- Get the images from a movie and tv shows
- Get the trailers/clips from a movie and tv show
- Search Movies, tv shows and persons

# Build this project
In the `build.gradle` assign your `API_KEY_TMDB` and `API_KEY_YOUTUBE` to play the trailers

- Get the TheMoviedb API KEY [here](https://developers.themoviedb.org/3/getting-started)
- Get the YouTube API KEY [here](https://developers.google.com/youtube/v3/getting-started)

```java
ext {
        API_KEY_TMDB = "\"YOU_API_KEY\""
        API_KEY_YOUTUBE = "\"YOU_API_KEY\""
}
```

# Implementations
- Fully written in [Kotlin](https://kotlinlang.org/) language
- [Retrofit 2](http://square.github.io/retrofit)
- [Dagger 2](https://google.github.io/dagger/)
- [RxKotlin 2](https://github.com/ReactiveX/RxKotlin)
- [RxAndroid 2](https://github.com/ReactiveX/RxAndroid)
- [DraggablePanel](https://github.com/pedrovgs/DraggablePanel) - DEPRECATED
- [Gson](https://github.com/google/gson)
- [Glide](https://github.com/bumptech/glide)
- [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)
- [KAndroid](https://github.com/pawegio/KAndroid)
- [Android Ktx](https://github.com/android/android-ktx)
- [Android View Animations](https://github.com/daimajia/AndroidViewAnimations)
- [Circle-Progress-View](https://github.com/jakob-grabner/Circle-Progress-View)
- Other support libraries - AppCompat, RecyclerView, CardView, Design

# Author
Email: rafael16mercado@gmail.com

# Screenshots
![Movies](https://raw.githubusercontent.com/M1Dr05/TMDb-Kotlin/master/screenshots/movies.jpg) ![Tv shows](https://raw.githubusercontent.com/M1Dr05/TMDb-Kotlin/master/screenshots/tv_shows.jpg)
![Details](https://raw.githubusercontent.com/M1Dr05/TMDb-Kotlin/master/screenshots/details.jpg)![Search](https://raw.githubusercontent.com/M1Dr05/TMDb-Kotlin/master/screenshots/search.jpg)
