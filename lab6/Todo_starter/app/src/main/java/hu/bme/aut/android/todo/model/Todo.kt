package hu.bme.aut.android.todo.model

data class Todo(
    val id: Int,
    val title: String,
    val priority: Priority,
    val dueDate: String,
    val description: String
) {
    enum class Priority {
        LOW, MEDIUM, HIGH
    }
}