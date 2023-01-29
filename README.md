# GitStars

Simple Android application for showing most starred GitHub repositories utilizing AndroidX Paging 3 library.

## About The Project

One of the main ideas of this project is to test usage of [AndroidX Paging 3 library] (https://developer.android.com/topic/libraries/architecture/paging/v3-overview) 
to see how useful and convenient it is. 
For now it is a really simple single-screen application based on MVVM architecture without shiny UI, 
but it might be useful in training purposes, e.g. to see how Paging 3 works and how it could be tested.

## Built with
* [AndroidX] (https://developer.android.com/jetpack/androidx)
* [Retrofit] (https://github.com/square/retrofit)
* [OkHttp] (https://github.com/square/okhttp)
* [KOIN] (https://github.com/InsertKoinIO/koin)
* [Glide] (https://github.com/bumptech/glide)

## Prerequisites

**Android Studio** with Android SDK 32 installed.

## Installation

First, clone this repository and import into **Android Studio**
```bash
git clone git@github.com:antonkozlenko/GitStars.git
```

And then just click **Run** - application doesn't require any specific configuration.
Once application is launched on your device or emulator you'll see a list of most starred GitHub repositories 
created in last 30 days and sorted by their star rating (starting from top-rated ones) 

