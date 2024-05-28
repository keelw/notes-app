package wk.projects.notesapp

// imports
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Main activity to run the app
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private var noteList = mutableListOf<Note>()

    // Create the basic viewport and set the content view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        noteAdapter = NoteAdapter(noteList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter

        findViewById<Button>(R.id.addNoteButton).setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_NOTE)
        }

        // Load existing notes (if any) from SharedPreferences
        loadNotes()
    }

    // When something happens this will get the note list as is, log results, and save
    // notes to local storage so the app can always keep track of the notes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            val noteTitle = data?.getStringExtra("noteTitle") ?: ""
            val noteContent = data?.getStringExtra("noteContent") ?: ""
            val note = Note(noteTitle, noteContent)
            noteList.add(note)
            noteAdapter.notifyDataSetChanged()
            saveNotes() // Call saveNotes() after adding a new note
            Log.d("MainActivity", "onActivityResult called")
        }
    }

    // Save the notes and note count to local storage so it can be accessed later
    fun saveNotes() {
        val sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Save the count of notes
        editor.putInt("noteCount", noteList.size)

        // Save each note separately
        for ((index, note) in noteList.withIndex()) {
            editor.putString("noteTitle_$index", note.title)
            editor.putString("noteContent_$index", note.content)
        }
        editor.apply()
        Log.d("MainActivity", "saveNotes completed")
    }

    // This function will load the notes and populate them onto the page
    // from shared preferences
    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE)
        val noteCount = sharedPreferences.getInt("noteCount", 0)
        for (i in 0 until noteCount) {
            val title = sharedPreferences.getString("noteTitle_$i", "") ?: ""
            val content = sharedPreferences.getString("noteContent_$i", "") ?: ""
            val note = Note(title, content)
            noteList.add(note)
        }
        noteAdapter.notifyDataSetChanged()
    }

    companion object {
        const val REQUEST_CODE_ADD_NOTE = 1
    }
}
