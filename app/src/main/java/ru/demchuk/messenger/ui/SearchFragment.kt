package ru.demchuk.messenger.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.demchuk.messenger.MainActivity
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val ciceroneFragmentSearch = Cicerone.create()
    private val routerFragmentSearch get() = ciceroneFragmentSearch.router
    private val navigatorHolderFragmentSearch get() = ciceroneFragmentSearch.getNavigatorHolder()
    private val navigatorFragmentSearch by lazy {AppNavigator(requireActivity(), R.id.fragmentContentSearch, childFragmentManager)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        navigatorHolderFragmentSearch.setNavigator(navigatorFragmentSearch)
        routerFragmentSearch.navigateTo(MainActivity.Screens.ButtonNavigation("stream"))

    }

    override fun onPause() {
        navigatorHolderFragmentSearch.removeNavigator()
        super.onPause()
    }

}