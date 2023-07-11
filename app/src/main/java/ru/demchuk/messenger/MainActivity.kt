package ru.demchuk.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.demchuk.messenger.ui.DialogueFragment

class MainActivity : AppCompatActivity() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    private val navigatorHolder get() = cicerone.getNavigatorHolder()
    private val navigator = AppNavigator(this, R.id.fragmentMain)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }



}