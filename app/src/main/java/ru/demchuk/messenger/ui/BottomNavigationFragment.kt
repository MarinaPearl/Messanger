package ru.demchuk.messenger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.demchuk.messenger.MainActivity
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentBottomNavigationBinding


class BottomNavigationFragment : Fragment() {

    private lateinit var binding: FragmentBottomNavigationBinding

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
                    activity.routerFragmentBottomNavigation.navigateTo(MainActivity.Screens.People())
                    return@setOnItemSelectedListener true
                }
                R.id.icon_channels -> {
                    activity.router.navigateTo(MainActivity.Screens.Message())
                    return@setOnItemSelectedListener true
                }
                R.id.icon_profile -> {
                    activity.router.navigateTo(MainActivity.Screens.Message())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }


}