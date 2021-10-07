package com.example.fragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

const val CURRENT_INDEX = "current_index"

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var one: TextView
    private lateinit var two: TextView
    private lateinit var three: TextView
    private lateinit var four: TextView

    private val fragments = arrayOfNulls<Fragment>(4)
    private val tags = arrayOf("onw", "two", "three", "four")
    private var showFragment: Fragment? = null
    private var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            fragments.forEachIndexed { index, _ ->
                val fragment: Fragment? =
                    supportFragmentManager.getFragment(savedInstanceState, tags[index])
                if (fragment != null) {
                    fragments[index] = fragment
                }
            }
            currentIndex = savedInstanceState.getInt(CURRENT_INDEX, 0)
        }

        fragmentContainer = findViewById(R.id.fragment_container)
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        one.setOnClickListener { switchFragment(0) }
        two.setOnClickListener { switchFragment(1) }
        three.setOnClickListener { switchFragment(2) }
        four.setOnClickListener { switchFragment(3) }

        switchFragment(currentIndex)
    }

    private fun getFragmentByIndex(index: Int): Fragment {
        return when (index) {
            0 -> SimpleFragment.newInstance("one")
            1 -> SimpleFragment.newInstance("two")
            2 -> SimpleFragment.newInstance("three")
            3 -> SimpleFragment.newInstance("four")
            else -> {
                throw IllegalArgumentException("非法参数")
            }
        }
    }

    private fun switchFragment(index: Int) {
        if (fragments[index] == null) {
            fragments[index] = getFragmentByIndex(index)
        }
        val to: Fragment? = fragments[index]

        if (to == null || showFragment == to)
            return

        currentIndex = index
        supportFragmentManager.beginTransaction().apply {
            if (!to.isAdded) {
                showFragment?.let { hide(it) }
                add(R.id.fragment_container, to, tags[index])
            } else {
                showFragment?.let { hide(it) }
                show(to)
            }
            showFragment = to
            commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        fragments.forEachIndexed { index, fragment ->
            if (fragment != null && fragment.isAdded) {
                supportFragmentManager.putFragment(outState, tags[index], fragment)
            }
        }
        outState.putInt(CURRENT_INDEX, currentIndex)
        super.onSaveInstanceState(outState)
    }

}