package com.example.musicplaylist

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailedViewActivity : AppCompatActivity() {

    // UI elements
    private lateinit var tvSongList: TextView
    private lateinit var tvAverageRating: TextView
    private lateinit var btnDisplaySongs: Button
    private lateinit var btnCalculateAverage: Button
    private lateinit var btnReturnToMain: Button

    // Data passed from MainActivity
    private var songTitles: ArrayList<String> = ArrayList()
    private var artistNames: ArrayList<String> = ArrayList()
    private var ratings: ArrayList<Int> = ArrayList()
    private var comments: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        // Initialize UI elements
        tvSongList = findViewById(R.id.tvSongList)
        tvAverageRating = findViewById(R.id.tvAverageRating)
        btnDisplaySongs = findViewById(R.id.btnDisplaySongs)
        btnCalculateAverage = findViewById(R.id.btnCalculateAverage)
        btnReturnToMain = findViewById(R.id.btnReturnToMain)

        // Retrieve data passed from the MainActivity via Intent
        intent.getStringArrayListExtra("songTitles")?.let { songTitles = it }
        intent.getStringArrayListExtra("artistNames")?.let { artistNames = it }
        intent.getIntegerArrayListExtra("ratings")?.let { ratings = it } // Use getIntegerArrayListExtra
        intent.getStringArrayListExtra("comments")?.let { comments = it }

        // Set up click listener for "Display All Songs" button
        btnDisplaySongs.setOnClickListener {
            displayAllSongs()
        }

        // Set up click listener for "Calculate Average Rating" button
        btnCalculateAverage.setOnClickListener {
            calculateAndDisplayAverageRating()
        }

        // Set up click listener for "Return to Main Screen" button
        btnReturnToMain.setOnClickListener {
            returnToMainScreen()
        }
    }

    /**
     * Method to display the list of songs with their details.
     * Uses a loop to iterate through the parallel arrays and format the output.
     */
    private fun displayAllSongs() {
        if (songTitles.isEmpty()) {
            tvSongList.text = "No songs added to the playlist yet."
            showToast("Playlist is empty.")
            return
        }

        val stringBuilder = StringBuilder()
        stringBuilder.append("Your Current Playlist:\n\n")

        // Loop through the arrays to display each song's details
        for (i in songTitles.indices) {
            stringBuilder.append("Song Title: ${songTitles[i]}\n")
            stringBuilder.append("Artist Name: ${artistNames[i]}\n")
            stringBuilder.append("Rating: ${ratings[i]}/5\n")
            stringBuilder.append("Comments: ${comments[i]}\n")
            stringBuilder.append("----------------------------\n\n")
        }
        tvSongList.text = stringBuilder.toString()
        showToast("Songs displayed.")
    }

    /**
     * Method to calculate and display the average rating for songs.
     * Uses a loop to sum the ratings and then calculates the average.
     */
    private fun calculateAndDisplayAverageRating() {
        if (ratings.isEmpty()) {
            tvAverageRating.text = "Average Rating: N/A (No songs added)"
            showToast("Cannot calculate average: No songs in playlist.")
            return
        }

        var totalRating = 0
        // Loop through the ratings array to sum them up
        for (rating in ratings) {
            totalRating += rating
        }

        // Calculate the average rating
        val averageRating = totalRating.toDouble() / ratings.size

        // Display the average rating, formatted to two decimal places
        tvAverageRating.text = String.format("Average Rating: %.2f/5", averageRating)
        showToast("Average rating calculated.")
    }

    /**
     * Method to return to the MainActivity.
     * finish() destroys the current activity, revealing the previous one on the stack.
     */
    private fun returnToMainScreen() {
        finish() // Destroys this activity and returns to the previous one
    }

    /**
     * Helper method to display a Toast message to the user.
     * @param message The string message to display.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}