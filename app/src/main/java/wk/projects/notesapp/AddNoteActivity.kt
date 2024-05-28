package wk.projects.notesapp

// Imports
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

// Create a class for the AddNoteActivity which listens to the saveNoteButton
// object and saves the note title, note content, and sets the values
// accordingly
class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        findViewById<Button>(R.id.saveNoteButton).setOnClickListener {
            val noteTitle = findViewById<EditText>(R.id.titleEditText).text.toString()
            val noteContent = findViewById<EditText>(R.id.contentEditText).text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("noteTitle", noteTitle)
            resultIntent.putExtra("noteContent", noteContent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
