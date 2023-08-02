package ru.demchuk.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.demchuk.messenger.ui.*
import ru.demchuk.messenger.ui.dialogue.DialogueFragment
import ru.demchuk.messenger.ui.people.PeopleFragment
import ru.demchuk.messenger.ui.recyclerStreams.StreamsFragment

class MainActivity : AppCompatActivity() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    private val navigatorHolder get() = cicerone.getNavigatorHolder()
    private val navigator = AppNavigator(this, R.id.fragmentMain)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.navigateTo(Screens.ButtonNavigation())
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    object Screens {
        fun Message() = FragmentScreen { DialogueFragment() }
        fun People() = FragmentScreen { PeopleFragment() }

        fun Profile() = FragmentScreen { ProfileFragment() }

        fun Streams() = FragmentScreen { StreamsFragment() }
        fun Search() = FragmentScreen { SearchFragment() }
        fun ButtonNavigation() = FragmentScreen { BottomNavigationFragment() }

    }


}