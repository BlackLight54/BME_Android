package hu.bme.aut.android.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.todo.databinding.FragmentCreateBinding
import hu.bme.aut.android.todo.model.Todo
import kotlin.random.Random

class TodoCreateFragment : DialogFragment() {

    private lateinit var listener: TodoCreatedListener
    private lateinit var binding: FragmentCreateBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as TodoCreatedListener
            } else {
                activity as TodoCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        dialog?.setTitle(R.string.itemCreateTodo)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spnrTodoPriority.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Low", "Medium", "High")
        )
        binding.tvTodoDueDate.text = "  -  "

        binding.btnCreateTodo.setOnClickListener {
            val selectedPriority = when (binding.spnrTodoPriority.selectedItemPosition) {
                0 -> Todo.Priority.LOW
                1 -> Todo.Priority.MEDIUM
                2 -> Todo.Priority.HIGH
                else -> Todo.Priority.LOW
            }

            listener.onTodoCreated(Todo(
                id = Random.nextInt(),
                title = binding.etTodoTitle.text.toString(),
                priority = selectedPriority,
                dueDate = binding.tvTodoDueDate.text.toString(),
                description = binding.etTodoDescription.text.toString()
            ))
            dismiss()
        }

        binding.btnCancelCreateTodo.setOnClickListener {
            dismiss()
        }

    }

    private fun showDatePickerDialog() {
        //TODO
    }

    interface TodoCreatedListener {
        fun onTodoCreated(todo: Todo)
    }
}