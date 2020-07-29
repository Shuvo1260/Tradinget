package org.binaryitplanet.tradinget.Features.View

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentBackupAndRestoreBinding
import java.io.File
import java.io.FileOutputStream

class BackupAndRestore : Fragment() {

    private val TAG = "BackupAndRestore"

    private lateinit var binding: FragmentBackupAndRestoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBackupAndRestoreBinding.inflate(inflater, container, false)

        binding.backup.setOnClickListener {

            val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permissions, Config.BACKUP_REQUEST_CODE)
        }

        binding.restore.setOnClickListener {
            val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permissions, Config.RESTORE_REQUEST_CODE)
        }
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "code: $requestCode, $permissions, $grantResults")
        if (requestCode == Config.BACKUP_REQUEST_CODE && grantResults.isNotEmpty()) {
            backupData()
        } else if (requestCode == Config.RESTORE_REQUEST_CODE && grantResults.isNotEmpty()) {
            restoreData()
        }

    }

    // Backup data
    private fun backupData() {
        binding.progress.visibility = View.VISIBLE


        try{

            Log.d(TAG, "Permitted")
            DatabaseManager.getInstance(context!!)?.close()

            val db = DatabaseManager.getInstance(context!!)?.openHelper?.writableDatabase?.path
            val dbPath = File(db)
            val dbShm = File("$db-shm")
            val dbWal = File("$db-wal")

            Log.d(TAG, "DB: + $db")
            Log.d(TAG, "DB: + $dbShm")
            Log.d(TAG, "DB: + $dbWal")

            val folder = File(Config.SD_CARD_PATH)

            if (!folder.exists())
                folder.mkdirs()

            val db2 = File(Config.SD_CARD_PATH, Config.DATABASE_NAME + ".db")
            val db2Shm = File(Config.SD_CARD_PATH, Config.DATABASE_NAME + "-shm")
            val db2Wal = File(Config.SD_CARD_PATH, Config.DATABASE_NAME + "-wal")


            Log.d(TAG, "Done")
            copyDataFromOneToAnother(dbPath.path, db2.path)
            if (Build.VERSION_CODES.Q == Build.VERSION.SDK_INT) {
                copyDataFromOneToAnother(dbShm.path, db2Shm.path)
                copyDataFromOneToAnother(dbWal.path, db2Wal.path)
            }

            Log.d(TAG, "Done")
            binding.progress.visibility = View.INVISIBLE

            Toast.makeText(
                context,
                Config.SUCCESS_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()


        }catch (e: Exception) {
            Log.d(TAG, "BackupException: ${e.message}")

            Toast.makeText(
                context,
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            binding.progress.visibility = View.INVISIBLE
        }

    }


    // Restoring data
    private fun restoreData() {
        binding.progress.visibility = View.VISIBLE


        try{
            Log.d(TAG, "Permitted")
            DatabaseManager.getInstance(context!!)?.close()

            val db = DatabaseManager.getInstance(context!!)?.openHelper?.writableDatabase?.path
            val dbPath = File(db)
            val dbShm = File("$db-shm")
            val dbWal = File("$db-wal")

            Log.d(TAG, "DB: + $db")
            Log.d(TAG, "DB: + $dbShm")
            Log.d(TAG, "DB: + $dbWal")

            val folder = File(Config.SD_CARD_PATH)

            if (!folder.exists())
                folder.mkdirs()

            val db2 = File(Config.SD_CARD_PATH, Config.DATABASE_NAME + ".db")
            val db2Shm = File(Config.SD_CARD_PATH, Config.DATABASE_NAME + "-shm")
            val db2Wal = File(Config.SD_CARD_PATH, Config.DATABASE_NAME + "-wal")


            Log.d(TAG, "Done")
            copyDataFromOneToAnother(db2.path, dbPath.path)

            if (Build.VERSION_CODES.Q == Build.VERSION.SDK_INT) {
                copyDataFromOneToAnother(db2Shm.path, dbShm.path)
                copyDataFromOneToAnother(db2Wal.path, dbWal.path)
            }

            Log.d(TAG, "Done")
            binding.progress.visibility = View.INVISIBLE

            Toast.makeText(
                context,
                Config.SUCCESS_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()

        }catch (e: Exception) {
            Log.d(TAG, "RestoreException: ${e.message}")

            Toast.makeText(
                context,
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            binding.progress.visibility = View.INVISIBLE
        }

    }

    // Copying data from a location to another location
    private fun copyDataFromOneToAnother(fromPath: String, toPath: String) {
        val inStream = File(fromPath).inputStream()
        val outStream = FileOutputStream(toPath)

        inStream.use { input ->
            outStream.use { output ->
                input.copyTo(output)
            }
        }
    }

}