package ru.demchuk.messenger.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.demchuk.messenger.MainActivity
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentDialogueBinding
import ru.demchuk.messenger.ui.adapterDelegate.MainAdapterDelegate
import ru.demchuk.messenger.ui.dialogue.concatenateWithDate
import ru.demchuk.messenger.ui.dialogue.data.DateAdapter
import ru.demchuk.messenger.ui.dialogue.data.DateModel
import ru.demchuk.messenger.ui.dialogue.message.MessageAdapter
import ru.demchuk.messenger.ui.dialogue.message.MessageModel


class DialogueFragment : Fragment() {

    private lateinit var binding: FragmentDialogueBinding
    private val adapter: MainAdapterDelegate by lazy { MainAdapterDelegate() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.apply {
            addDelegate(MessageAdapter())
            addDelegate(DateAdapter())
        }
        binding.editTextForMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (binding.editTextForMessage.text.isEmpty()) {
                    binding.buttonSendMessage.setImageResource(R.drawable.add_button)
                    return
                }
                binding.buttonSendMessage.setImageResource(R.drawable.plane_item_icon)
            }
        })
        binding.recyclerDialogue.adapter = adapter
        binding.recyclerDialogue.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.submitList(stubListMessage.concatenateWithDate(stubDatesList))
    }

    override fun onResume() {
        super.onResume()
        binding.backButton.setOnClickListener {
            val activity = activity as MainActivity
            activity.router.backTo(MainActivity.Screens.Streams())
        }
    }

    companion object {
        private const val JUL_5 = "5 июля"
        private const val SEP_1 = "1 сенятбря"
        private const val SEP_12 = "12 сенятбря"
        private const val DEC_7 = "7 декабря"

        private val stubDatesList = listOf(
            DateModel(
                id = 1,
                date = SEP_1,
            ),
            DateModel(
                id = 2,
                date = SEP_12,
            ),
            DateModel(
                id = 3,
                date = JUL_5,
            ),
            DateModel(
                id = 4,
                date = DEC_7,
            ),
        )

        val stubListMessage = mutableListOf<MessageModel>(
            MessageModel(1, 1, "1", "Ваня", "Привет", JUL_5),
            MessageModel(2, 2, "2", "Катя", "Привет", SEP_1),
            MessageModel(3, 3, "3", "Даша", "Привет", SEP_12),
            MessageModel(4, 4, "4", "Саша", "Привет", JUL_5),
            MessageModel(5, 5, "5", "Лиза", "Привет", DEC_7),
            MessageModel(6, 6, "6", "Вася", "Привет", DEC_7),
            MessageModel(7, 7, "7", "Надя", "Привет", SEP_1),
            MessageModel(8, 8, "8", "Вероника", "Привет", DEC_7),
            MessageModel(9, 9, "9", "Ярик", "Привет", SEP_1)
        )
    }


}