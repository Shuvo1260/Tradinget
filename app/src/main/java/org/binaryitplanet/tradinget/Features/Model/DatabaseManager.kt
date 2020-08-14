package org.binaryitplanet.tradinget.Features.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.binaryitplanet.tradinget.Utils.*

@Database(
    entities = [
        StakeholderUtils::class, PacketUtils::class, PacketDetailsUtils::class,
    SoldPacketUtils::class, LedgerUtils::class, InvoiceSettingsUtils::class,
    NotesUtils::class, SellerLedgerUtils::class
    ],
    version = Config.DATABASE_VERSION
)
abstract class DatabaseManager: RoomDatabase() {

    abstract fun getStakeholderDAO(): StakeholderDAO
    abstract fun getPacketDAO(): PacketDAO
    abstract fun getPacketDetailsDAO(): PacketDetailsDAO
    abstract fun getSoldPacketDAO(): SoldPacketDAO
    abstract fun getLedgerDAO(): LedgerDAO
    abstract fun getInvoiceSettingsDAO(): InvoiceSettingsDAO
    abstract fun getNotesDAO(): NotesDAO
    abstract fun getSellerLedgerDAO(): SellerLedgerDAO

    companion object{
        var INSTANCE: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager? {

            if (INSTANCE == null) {
                synchronized(DatabaseManager::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseManager::class.java,
                        Config.DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }

        fun destroy(){
            INSTANCE = null
        }
    }
}