package com.example.prueba_tecnica.base.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.view.fragment.character.ListCharacterFragment

class MainActivity : AppCompatActivity() {

    /**
     * here the first view is loaded (character list)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, ListCharacterFragment())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            backPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * control over the action "go back"
     */
    private fun backPressed() {
        val fragment: BaseFragment<*>? =
            supportFragmentManager.findFragmentById(R.id.frameLayout) as BaseFragment<*>?
        if (fragment !is ListCharacterFragment) {
            supportFragmentManager.popBackStack()
        } else {
            fragment.showError(R.string.exit_app, goBack = true) {
                finishAffinity()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        backPressed()
    }
}