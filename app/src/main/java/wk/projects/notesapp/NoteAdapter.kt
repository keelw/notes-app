package wk.projects.notesapp

// Imports
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Create a note adapter class so that the
class NoteAdapter(private val noteList: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    // This override function will get the title and content of the note
    // and store it to the view holder object.
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // Set click listener for delete button
        holder.deleteButton.setOnClickListener {
            noteList.removeAt(position)
            notifyItemRemoved(position)
            (holder.itemView.context as MainActivity).saveNotes() // Call saveNotes() after deletion
        }
    }

    // Keeping track of the size of the list is important because the list is not
    // stored in json but rather directly as different variables.
    override fun getItemCount() = noteList.size

    // NoteViewHolder class which simply keeps track of the title and content
    // of the note view objects
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }
}
