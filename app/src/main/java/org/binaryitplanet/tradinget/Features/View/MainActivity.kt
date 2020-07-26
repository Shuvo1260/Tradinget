package org.binaryitplanet.tradinget.Features.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import org.binaryitplanet.tradinget.Features.View.Broker.Broker
import org.binaryitplanet.tradinget.Features.View.Buyer.Buyer
import org.binaryitplanet.tradinget.Features.View.Home.Home
import org.binaryitplanet.tradinget.Features.View.Inventory.Inventory
import org.binaryitplanet.tradinget.Features.View.Invoice.Invoice
import org.binaryitplanet.tradinget.Features.View.Seller.Seller
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding



    private var drawerToggle: ActionBarDrawerToggle? = null

    private var fragmentManager = supportFragmentManager
    private var fragmentTransition = fragmentManager.beginTransaction()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpFirstFragment()

        setUpDrawerToggle()

        binding.navigation.setNavigationItemSelectedListener(this)

    }


    // Setting first fragment
    private fun setUpFirstFragment() {

        setSupportActionBar(binding.toolbar)
        setUpToolbarTitle(Config.TOOLBAR_TITLE_HOME)
        fragmentTransition.add(
            R.id.frameLayout,
            Home(),
            Config.TOOLBAR_TITLE_HOME)
        fragmentTransition.commit()

    }

    // Setting up the drawertoggle or menu toggle
    private fun setUpDrawerToggle() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        drawerToggle!!.syncState()
    }

    // Setting navigation title
    private fun setUpToolbarTitle(title: String) {
        binding.navigationTitle.text = title
    }


    // Navigation item selection listener
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        fragmentManager = supportFragmentManager
        fragmentTransition = fragmentManager.beginTransaction()

        if (item.itemId == R.id.nav_home) {

            setUpToolbarTitle(Config.TOOLBAR_TITLE_HOME)
            fragmentTransition.replace(
                R.id.frameLayout,
                Home(),
                Config.TOOLBAR_TITLE_HOME
            )

        }else if (item.itemId == R.id.nav_buyers) {

            setUpToolbarTitle(Config.TOOLBAR_TITLE_BUYER)
            fragmentTransition.replace(
                R.id.frameLayout,
                Buyer(),
                Config.TOOLBAR_TITLE_BUYER
            )

        }else if (item.itemId == R.id.nav_sellers) {

            setUpToolbarTitle(Config.TOOLBAR_TITLE_SELLER)
            fragmentTransition.replace(
                R.id.frameLayout,
                Seller(),
                Config.TOOLBAR_TITLE_SELLER
            )

        }else if (item.itemId == R.id.nav_brokers) {

            setUpToolbarTitle(Config.TOOLBAR_TITLE_BROKER)
            fragmentTransition.replace(
                R.id.frameLayout,
                Broker(),
                Config.TOOLBAR_TITLE_BROKER
            )

        }else if (item.itemId == R.id.nav_inventories) {

            setUpToolbarTitle(Config.TOOLBAR_TITLE_INVENTORY)
            fragmentTransition.replace(
                R.id.frameLayout,
                Inventory(),
                Config.TOOLBAR_TITLE_INVENTORY
            )

        }else if (item.itemId == R.id.nav_invoice) {

            setUpToolbarTitle(Config.TOOLBAR_TITLE_INVOICE)
            fragmentTransition.replace(
                R.id.frameLayout,
                Invoice(),
                Config.TOOLBAR_TITLE_INVOICE
            )

        }else if (item.itemId == R.id.nav_backup_and_restore) {

            setUpToolbarTitle(Config.TOOLBAR_TITLE_BACKUP_AND_RESTORE)
            fragmentTransition.replace(
                R.id.frameLayout,
                BackupAndRestore(),
                Config.TOOLBAR_TITLE_BACKUP_AND_RESTORE
            )

        }else if (item.itemId == R.id.nav_exit) {

            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
            overridePendingTransition(R.anim.positiontotop, R.anim.toptobottom)
            finish()

        }

        // Replacing fragment on fragment choose
        fragmentTransition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(binding.navigation)){
            binding.drawerLayout.closeDrawer(binding.navigation)
        }else {
            super.onBackPressed()

            // Finding current fragment
            var currentFragment = fragmentManager.findFragmentById(R.id.frameLayout)

            Log.d(TAG, "Current: ${currentFragment?.tag}")

            setUpToolbarTitle(currentFragment?.tag!!)

        }
    }

}