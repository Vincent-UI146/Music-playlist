package com.example.musicplaylist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Declare parallel arrays (MutableLists for dynamic sizing up to 4 elements)
    // Using MutableList to easily add elements and manage capacity.
    private val songTitles = mutableListOf<String>()
    private val artistNames = mutableListOf<String>()
    private val ratings = mutableListOf<Int>()
    private val comments = mutableListOf<String>()

    // Maximum number of songs the app can cater to
    private val MAX_SONGS = 4

    // UI elements
    private lateinit var etSongTitle: EditText
    private lateinit var etArtistName: EditText
    private lateinit var etRating: EditText
    private lateinit var etComments: EditText
    private lateinit var btnAddPlaylist: Button
    private lateinit var btnViewDetailed: Button
    private lateinit var btnExitApp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        etSongTitle = findViewById(R.id.etSongTitle)
        etArtistName = findViewById(R.id.etArtistName)
        etRating = findViewById(R.id.etRating)
        etComments = findViewById(R.id.etComments)
        btnAddPlaylist = findViewById(R.id.btnAddPlaylist)
        btnViewDetailed = findViewById(R.id.btnViewDetailed)
        btnExitApp = findViewById(R.id.btnExitApp)

        // Set up click listener for "Add to Playlist" button
        btnAddPlaylist.setOnClickListener {
            addSongToPlaylist()
        }

        // Set up click listener for "View Detailed Playlist" button
        btnViewDetailed.setOnClickListener {
            navigateToDetailedView()
        }

        // Set up click listener for "Exit App" button
        btnExitApp.setOnClickListener {
            exitApp()
        }
    }

    /**
     * Method to add song details to the playlist.
     * It validates user input, handles array capacity, and provides user feedback.
     */
    private fun addSongToPlaylist() {
        // Check if the playlist is full
        if (songTitles.size >= MAX_SONGS) {
            showToast("Playlist is full. Cannot add more than $MAX_SONGS songs.")
            return
        }

        // Get user input from EditText fields
        val songTitle = etSongTitle.text.toString().trim()
        val artistName = etArtistName.text.toString().trim()
        val ratingStr = etRating.text.toString().trim()
        val comment = etComments.text.toString().trim()

        // Validate input fields
        if (songTitle.isEmpty() || artistName.isEmpty() || ratingStr.isEmpty() || comment.isEmpty()) {
            showToast("Please fill in all fields.")
            return // Stop execution if any field is empty
        }

        // Validate rating: ensure it's a number between 1 and 5
        val rating: Int? = ratingStr.toIntOrNull()
        if (rating == null || rating !in 1..5) {
            // Error handling: provide constructive feedback and implement the incorrect action (rejecting addition)
            showToast("Invalid rating. Please enter a number between 1 and 5.")
            etRating.error = "Rating must be 1-5" // Set error on the EditText
            return // Stop execution if rating is invalid
        }

        // If all validations pass, add data to the parallel arrays
        songTitles.add(songTitle)
        artistNames.add(artistName)
        ratings.add(rating)
        comments.add(comment)

        // Provide success feedback to the user
        showToast("'$songTitle' by $artistName added to playlist.")

        // Clear input fields for the next entry using a loop (implicitly through `clear()`)
        etSongTitle.text.clear()
        etArtistName.text.clear()
        etRating.text.clear()
        etComments.text.clear()

        // Log the current state of arrays for debugging (optional)
        // println("Current Playlist: Titles=$songTitles, Artists=$artistNames, Ratings=$ratings, Comments=$comments")
    }

    /**
     * Method to navigate to the DetailedViewActivity.
     * It passes the current playlist data to the next activity.
     */
    private fun navigateToDetailedView() {
        val intent = Intent(this, DetailedViewActivity::class.java).apply {
            // Pass the parallel array data to the next activity
            // Using ArrayList<String> for string arrays and IntArray for int array
            // Note: For larger datasets, consider using Parcelable or Serializable.
            // For 4 items, this approach is simple and effective.
            putStringArrayListExtra("songTitles", ArrayList(songTitles))
            putStringArrayListExtra("artistNames", ArrayList(artistNames))
            putIntegerArrayListExtra("ratings", ArrayList(ratings)) // Use putIntegerArrayListExtra for List<Int>
            putStringArrayListExtra("comments", ArrayList(comments))
        }
        startActivity(intent)
    }

    /**
     * Method to exit the application.
     * finishAffinity() closes all activities in the current task.
     */
    private fun exitApp() {
        showToast("Exiting application.")
        finishAffinity() // Closes all activities in the current task
    }

    /**
     * Helper method to display a Toast message to the user.
     * @param message The string message to display.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
