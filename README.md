# UnderSound

Required libraries:
1. Volley
2. Picasso
3. Spotify App Remote 
4. Spotify Authentication

VOLLEY LIBRARY
Volley is an HTTP library that makes networking for Android apps easier and, most importantly, faster.
Download at https://github.com/google/volley

PICASSO LIBRARY
A powerful image downloading and caching library for Android.
Download at https://github.com/square/picasso

SPOTIFY APP REMOTE LIBRARY
The Spotify App Remote SDK allows your application to interact with the Spotify app running in the background as a service.
Used for playback.
Download at https://github.com/spotify/android-sdk/blob/master/app-remote-lib/README.md

SPOTIFY AUTHENTICATION LIBRARY
This library is responsible for authenticating the user and fetching the access token that can subsequently be used to play music or in requests to the Spotify Web API.
Download at https://github.com/spotify/android-auth/releases

Note: In order for the app to work within Android Studio (i.e. developer mode), you must provide us your SHA1 code so that you may be whitelisted to access the Spotify API. This is not an issue for the app if downloaded from the Google Playstore, however, since the user would not be opening the application through developer mode. This code can be generated via the steps: Gradle (top right tab) -> Tasks -> android -> double-click signingReport