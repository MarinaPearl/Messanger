package ru.demchuk.messenger.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.demchuk.messenger.MainActivity
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentStreamsBinding
import ru.demchuk.messenger.stubStreamList
import ru.demchuk.messenger.ui.adapterDelegate.MainAdapterDelegate
import ru.demchuk.messenger.ui.recyclerStreams.concatenateWithStream
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamAdapter
import ru.demchuk.messenger.ui.recyclerStreams.toDelegateList
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicAdapter
import ru.demchuk.messenger.ui.recyclerStreams.vm.StreamViewModel
import ru.demchuk.messenger.ui.state.ScreenState


@SuppressLint("ResourceAsColor")
class StreamsFragment : Fragment() {

    private lateinit var binding: FragmentStreamsBinding
    private val adapter: MainAdapterDelegate by lazy { MainAdapterDelegate() }
    private val viewModel: StreamViewModel by viewModels()
    private val snackBar : Snackbar by lazy {
        val snackBar = Snackbar.make(
            binding.root,
            "Ошибка при загрузке стримов",
            Snackbar.LENGTH_LONG
        )
        val sbView: View = snackBar.view
        sbView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.blue_green
            )
        )
        snackBar.setActionTextColor(R.color.grey_black)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStreamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.apply {
            addDelegate(StreamAdapter(lambdaForStream))
            addDelegate(TopicAdapter(createActionForTopic()))
        }

        binding.shimmer.isVisible = false
        binding.recyclerStream.adapter = adapter
        binding.recyclerStream.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //binding.recyclerStream.addItemDecoration(StickyHeaderItemDecoration())
        adapter.submitList(stubStreamList.toDelegateList())
        viewModel.listFilterTopic.observe(this) {
            adapter.submitList(
                it?.concatenateWithStream(stubStreamList)?.toList()
            )
        }
        binding.search.editTextSearch.addTextChangedListener {
            lifecycleScope.launch {
                it?.let { query -> viewModel.searchQueryState.emit(query.toString()) }
            }
        }


        viewModel.searchState
            .flowWithLifecycle(lifecycle)
            .onEach(::setScreen)
            .launchIn(lifecycleScope)
    }


    private val lambdaForStream = { stream: Stream ->
        if (!stream.press) {
            viewModel.filterList(stream)
        }
        viewModel.clearList(adapter.currentList, stream)
    }

    private fun createActionForTopic(): () -> Unit = {
        val activity = activity as MainActivity
        activity.router.navigateTo(MainActivity.Screens.Message())
    }



    private fun setScreen(state: ScreenState) {
        when (state) {
            ScreenState.Error -> {
                binding.apply {
                    recyclerStream.isVisible = false
                    shimmer.isVisible = false
                }
                snackBar.show()
            }
            ScreenState.Loading -> {
                binding.apply {
                    recyclerStream.isVisible = false
                    shimmer.isVisible = true
                }
            }
            is ScreenState.Data -> {
                binding.apply {
                    recyclerStream.isVisible = true
                    shimmer.isVisible = false
                }
                adapter.submitList(state.list.toDelegateList())
            }
            ScreenState.Init -> {}
        }

    }


}