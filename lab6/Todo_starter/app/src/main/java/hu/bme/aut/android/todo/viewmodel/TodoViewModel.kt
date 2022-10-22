package hu.bme.aut.android.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.todo.Repository
import hu.bme.aut.android.todo.TodoApplication
import hu.bme.aut.android.todo.model.Todo
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val repository: Repository

    val allTodos: LiveData<List<Todo>>

    init {
        val todoDao = TodoApplication.todoDatabase.todoDao()
        repository = Repository(todoDao)
        allTodos = repository.getAllTodos()
    }

    fun insert(todo: Todo) = viewModelScope.launch {
        repository.insert(todo)
    }
}