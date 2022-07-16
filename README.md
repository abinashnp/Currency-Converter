
<h1 align="center">Currency Converter</h1></br>
<p align="center">  
A demo currency converter app using Modern Android App Development techniques 
</p>
</br>

<p align="center">
 <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
 <a href="https://github.com/abinashnp/Currency-Converter/actions"><img alt="Build Status" src="https://github.com/skydoves/DisneyMotions/workflows/Android%20CI/badge.svg"/></a> 
</p>


## Tech stack & Open-source libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- JetPack
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - [Bindables](https://github.com/skydoves/bindables) - Android DataBinding kit for notifying data changes to UI layers.
  - Repository pattern
  - [Koin](https://github.com/InsertKoinIO/koin) - dependency injection
- Material Design & Animations
- [Retrofit2 & Gson](https://github.com/square/retrofit) - constructing the REST API
- [OkHttp3](https://github.com/square/okhttp) - implementing interceptor, logging and mocking web server
- [BaseRecyclerViewAdapter](https://github.com/skydoves/BaseRecyclerViewAdapter) - implementing adapters and viewHolders
- Ripple animation, Shared element container transform/transition

## MAD Score
![summary](https://user-images.githubusercontent.com/24237865/103010250-58eaaa00-457b-11eb-90d6-e62beda756b0.png)
![kotlin](https://user-images.githubusercontent.com/24237865/103010255-5a1bd700-457b-11eb-8959-0a7c4a2b4bda.png)
