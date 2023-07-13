package ru.demchuk.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.demchuk.messenger.ui.*

class MainActivity : AppCompatActivity() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    private val navigatorHolder get() = cicerone.getNavigatorHolder()
    private val navigator = AppNavigator(this, R.id.fragmentMain)
    private val ciceroneFragmentBottomNavigation = Cicerone.create()
    val routerFragmentBottomNavigation get() = ciceroneFragmentBottomNavigation.router
    private val navigatorHolderFragmentBottomNavigation get() = ciceroneFragmentBottomNavigation.getNavigatorHolder()
    private val navigatorFragmentBottomNavigation =
        AppNavigator(this, R.id.fragmentContentWithBottomNavigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
        navigatorHolderFragmentBottomNavigation.setNavigator(navigatorFragmentBottomNavigation)
        router.navigateTo(Screens.Search())
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        navigatorHolderFragmentBottomNavigation.removeNavigator()
        super.onPause()
    }

    object Screens {
        fun Message() = FragmentScreen { DialogueFragment() }
        fun People() = FragmentScreen { PeopleFragment() }

        fun Profile() = FragmentScreen { ProfileFragment() }

        fun Streams() = FragmentScreen { StreamsFragment() }
        fun Search() = FragmentScreen { SearchFragment() }

    }


}