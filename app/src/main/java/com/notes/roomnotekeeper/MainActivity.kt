package com.notes.roomnotekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.notes.roomnotekeeper.app.NoteKeeperApp
import com.notes.roomnotekeeper.dao.NoteDao
import com.notes.roomnotekeeper.databinding.ActivityMainBinding
import com.notes.roomnotekeeper.model.Note
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteDao = (application as NoteKeeperApp).db.noteDao()
        binding.btnAdd.setOnClickListener {
            addRecord(noteDao)
        }
    }

    fun addRecord(noteDao: NoteDao) {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()

        if (title.isNotBlank() && content.isNotBlank()) {
            lifecycleScope.launch {
                noteDao.insert(Note(title = title, content = content))
                Toast.makeText(applicationContext, "Note saved", Toast.LENGTH_LONG).show()
                binding.etTitle.text.clear()
                binding.etContent.text.clear()
            }
        } else {
            Toast.makeText(this, "Title or content cannot be blank", Toast.LENGTH_LONG).show()
        }
    }
}