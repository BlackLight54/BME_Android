package hu.bme.aut.android.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.todo.database.RoomTodo
import hu.bme.aut.android.todo.database.TodoDao
import hu.bme.aut.android.todo.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val todoDao: TodoDao) {

    fun getAllTodos(): LiveData<List<Todo>> {
        return todoDao.getAllTodos()
            .map {roomTodos ->
                roomTodos.map {roomTodo ->
                    roomTodo.toDomainModel() }
            }
    }

    suspend fun insert(todo: Todo) = withContext(Dispatchers.IO) {
        todoDao.insertTodo(todo.toRoomModel())
    }

    private fun RoomTodo.toDomainModel(): Todo {
        return Todo(
            id = id,
            title = title,
            priority = priority,
            description = description,
            dueDate = dueDate
        )
    }

    private fun Todo.toRoomModel(): RoomTodo {
        return RoomTodo(
            title = title,
            priority = priority,
            description = description,
            dueDate = dueDate
        )
    }
}