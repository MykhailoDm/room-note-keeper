package com.notes.roomnotekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.notes.roomnotekeeper.adapter.ItemAdapter
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

        lifecycleScope.launch {
            noteDao.getAllNotes().collect {
                val list = ArrayList(it)
                setupListOfDataIntoRecyclerView(list, noteDao)
            }
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

    private fun setupListOfDataIntoRecyclerView(noteList: ArrayList<Note>, noteDao: NoteDao) {
        if (noteList.isNotEmpty()) {
            val itemAdapter = ItemAdapter(noteList, { id ->
                Toast.makeText(this, "Updated note with id: $id", Toast.LENGTH_SHORT).show()
            }, { id ->
                Toast.makeText(this, "Deleted note with id: $id", Toast.LENGTH_SHORT).show()
            });
            binding.rvItemsList.layoutManager = LinearLayoutManager(this);
            binding.rvItemsList.adapter = itemAdapter
            binding.rvItemsList.visibility = View.VISIBLE
            binding.tvNoRecordsAvailable.visibility = View.GONE
        } else {
            binding.rvItemsList.visibility = View.GONE
            binding.tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }
}