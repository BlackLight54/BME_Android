package hu.bme.aut.android.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hu.bme.aut.android.todo.databinding.TodoDetailBinding
import hu.bme.aut.android.todo.model.Todo

class TodoDetailFragment : Fragment() {

    private var selectedTodo: Todo? = null
    private lateinit var binding: TodoDetailBinding

    companion object {

        private const val KEY_TODO_DESCRIPTION = "KEY_TODO_DESCRIPTION"

        fun newInstance(todoDesc: String): TodoDetailFragment {
            val args = Bundle()
            args.putString(KEY_TODO_DESCRIPTION, todoDesc)

            val result = TodoDetailFragment()
            result.arguments = args
            return result
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            selectedTodo = Todo(
                id = 0,
                title = "cim",
                priority = Todo.Priority.LOW,
                dueDate = "1987.23.12",
                description = args.getString(KEY_TODO_DESCRIPTION) ?: ""
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = TodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoDetail.text = selectedTodo?.description
    }
}