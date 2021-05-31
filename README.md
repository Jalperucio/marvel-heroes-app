# Android Clean Architecture Components

It is written 100% in Kotlin

## Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* Android Support Libraries
* [RxJava3](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-3.0)
* [Koin](https://github.com/InsertKoinIO/koin)
* [Glide](https://github.com/bumptech/glide)
* [Retrofit](http://square.github.io/retrofit/)
* [OkHttp](http://square.github.io/okhttp/)
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)

## Requirements

* JDK 1.8
* [Android SDK](https://developer.android.com/studio/index.html)
* Android O ([API 30](https://developer.android.com/preview/api-overview.html))
* Latest Android SDK Tools and build tools.

## Architecture

The architecture of the project follows the principles of Clean Architecture with RXJava3 for managing observables sequences
and asynchronous tasks

The sample app when run will show you a simple list of the first 20 Marvel Heroes from Marvel offical API, letting the user
updating them dynamically by scrolling down the list through paginated request to the API

It also stores all the heroes received in memory cache in order to get a quicker access to them and avoiding redundant API 
requests.

### ABOUT Clean arquitecture layers 
### APP
Presentation layer where all the views are set and business logic and calls are implemented

### Domain
Use Cases layer

### Data
The Data Module is our access point to external data layers and is used to fetch data from multiple sources according to our
needings

### Datasources
Final layer where communications with API Rest, memory cache (or other sources as databases, i.e.) are located to get info
The Remote module handles all communications with remote sources, in our case it makes simple API calls using a Retrofit 
interface.

The MarvelRemoteImpl class uses a model mapper that will map these Remote models to the model representation

### Model
In a perfect clean arquitecture model classes should be located on each layer, but it's not a worthly effort in a samll app
like this one, so I've set this module in order to keep the independency and modularization between layers but being able to
use Marvel Hero data model through the different layers from the app.


### TO DO LIST ###
- [ ] Add cache validation to check memory data expiration and refresh it
- [ ] Add filtering for main list
- [ ] Apply styling and comic/series/stories/events fragments to show their contents
- [ ] Add testing classes