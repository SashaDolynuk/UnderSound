package com.example.undersound;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.client.ErrorCallback;

public class SpotifyClass extends android.app.Activity{
    @SuppressLint("all")
    public static final String CLIENT_ID = "2f184ad41615437489cfd03177eade83";
    public static final String REDIRECT_URI = "com.example.spotifyapp://callback/";
    public SpotifyAppRemote mSpotifyAppRemote;

    ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID).setRedirectUri(REDIRECT_URI).showAuthView(true).build();

    /*
     * takes in a spotify id (just get it straight from the database)
     * plays it
     */
    public void playSong(String id) {
        String uri = "spotify:track:" + id;

        playUri(uri);
    }

    /*
     * pauses the current spotify track
     */
    public void pauseSong() {
        mSpotifyAppRemote.getPlayerApi().pause();
    }

    /*
     * the bit that actually plays stuff
     */
    private void playUri(String uri) {
        mSpotifyAppRemote.getPlayerApi().pause();
        mSpotifyAppRemote.getPlayerApi()
                .play(uri)
                .setResultCallback(new CallResult.ResultCallback<Empty>() {
                    @Override
                    public void onResult(Empty empty) {
                        SpotifyClass.this.logMessage("Play successful");
                    }
                })
                .setErrorCallback(mErrorCallback);

    }

    /*
     * idk what the rest of these methods do really
     */
    private void subscribeToPlayerState() {
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<PlayerState>() {
            public void onEvent(PlayerState playerState) {
                // See what values are in playerState, might be able to determine if it's now randomly playing?
                final Track track = playerState.track;
                if (track != null) {
                    Log.d("Testing", track.name + " by " + track.artist.name);
                    // If the track is now different, your song has finished, stop it?
                }
            }
        });
    }

    private final ErrorCallback mErrorCallback = new ErrorCallback() {
        @Override
        public void onError(Throwable throwable) {
            SpotifyClass.this.logError(throwable, "Boom!");
        }
    };

    private void logError(Throwable throwable, String msg) {
        Log.e("Testing", msg, throwable);
    }

    private void logMessage(String msg ) {
        Log.d("Testing!!", msg);
    }
}