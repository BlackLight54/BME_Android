package hu.bme.aut.android.todo

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.todo.adapter.SimpleItemRecyclerViewAdapter
import hu.bme.aut.android.todo.databinding.ActivityTodoListBinding
import hu.bme.aut.android.todo.model.Todo
import hu.bme.aut.android.todo.viewmodel.TodoViewModel

class TodoListActivity : AppCompatActivity(), TodoCreateFragment.TodoCreatedListener, SimpleItemRecyclerViewAdapter.TodoItemClickListener {
    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var binding: ActivityTodoListBinding

    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (findViewById<NestedScrollView>(R.id.todo_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView()
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        todoViewModel.allTodos.observe(this) { todos ->
            simpleItemRecyclerViewAdapter.submitList(todos)
        }
    }

    private fun setupRecyclerView() {

        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        binding.root.findViewById<RecyclerView>(R.id.todo_list).adapter = simpleItemRecyclerViewAdapter
    }

    override fun onItemClick(todo: Todo) {
        if (twoPane) {
            val fragment = TodoDetailFragment.newInstance(todo.description)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.todo_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, TodoDetailActivity::class.java)
            intent.putExtra(TodoDetailActivity.KEY_DESC, todo.description)
            startActivity(intent)
        }
    }

    override fun onItemLongClick(position: Int, view: View, todo: Todo): Boolean {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_todo)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    todoViewModel.delete(todo)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemCreateTodo) {
            val todoCreateFragment = TodoCreateFragment()
            todoCreateFragment.show(supportFragmentManager, "TAG")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTodoCreated(todo: Todo) {
        todoViewModel.insert(todo)
    }
}