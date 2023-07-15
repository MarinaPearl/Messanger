package ru.demchuk.messenger.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.demchuk.messenger.MainActivity
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentBottomNavigationBinding


class BottomNavigationFragment(val key: String) : Fragment() {

    private lateinit var binding: FragmentBottomNavigationBinding
    private val ciceroneFragmentBottomNavigation = Cicerone.create()
    private val routerFragmentBottomNavigation get() = ciceroneFragmentBottomNavigation.router
    private val navigatorHolderFragmentBottomNavigation get() = ciceroneFragmentBottomNavigation.getNavigatorHolder()
    private val navigatorFragmentBottomNavigation by lazy {
        AppNavigator(
            requireActivity(),
            R.id.fragmentContentWithBottomNavigation,
            childFragmentManager
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setOnItemSelectedListener {
            val activity = activity as MainActivity
            when (it.itemId) {
                R.id.icon_people -> {
                    routerFragmentBottomNavigation.navigateTo(MainActivity.Screens.People())
                    return@setOnItemSelectedListener true
                }
                R.id.icon_channels -> {
                    activity.router.navigateTo(MainActivity.Screens.Search())
                    return@setOnItemSelectedListener true
                }
                R.id.icon_profile -> {
                    activity.router.navigateTo(MainActivity.Screens.ButtonNavigation( "profile"))
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onResume() {
        navigatorHolderFragmentBottomNavigation.setNavigator(navigatorFragmentBottomNavigation)
        when (key) {
            "stream" -> {
                routerFragmentBottomNavigation.navigateTo(MainActivity.Screens.Streams())
            }
            "profile" -> {
                routerFragmentBottomNavigation.navigateTo(MainActivity.Screens.Profile())
                binding.bottomNavigation.menu.getItem(2).isChecked = true
            }
        }
        super.onResume()
    }

    override fun onPause() {
        navigatorHolderFragmentBottomNavigation.removeNavigator()
        super.onPause()
    }
}