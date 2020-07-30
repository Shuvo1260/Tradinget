package org.binaryitplanet.tradinget.Utils

class Config {
    companion object {

        val OVER_DUE_DATE = "Over due dates"
        val UNDER_DUE_DATE = "Under due dates"
        val REQUIRED_FIELD = "Required field"
        val SUCCESS_MESSAGE = "Success"
        val FAILED_MESSAGE = "Failed"
        val LIST_CACHED_SIZE = 1000

        // Codes
        val BACKUP_REQUEST_CODE = 101
        val RESTORE_REQUEST_CODE = 102

        // Toolbar titles
        val TOOLBAR_TITLE_HOME = "Home"
        val TOOLBAR_TITLE_BUYER = "Buyers"
        val TOOLBAR_TITLE_SELLER = "Sellers"
        val TOOLBAR_TITLE_BROKER = "Brokers"
        val TOOLBAR_TITLE_INVENTORY = "Inventories"
        val TOOLBAR_TITLE_INVOICE = "Invoice"
        val TOOLBAR_TITLE_BACKUP_AND_RESTORE = "Backup and restore"
        val TOOLBAR_TITLE_ADD_BUYER = "Add buyer"
        val TOOLBAR_TITLE_ADD_BROKER = "Add broker"
        val TOOLBAR_TITLE_ADD_SELLER = "Add seller"
        val TOOLBAR_TITLE_ADD_PACKET = "Add packet"
        val TOOLBAR_TITLE_UPDATE_BUYER = "Update buyer"
        val TOOLBAR_TITLE_UPDATE_BROKER = "Update broker"
        val TOOLBAR_TITLE_UPDATE_SELLER = "Update seller"
        val TOOLBAR_TITLE_UPDATE_PACKET = "Update packet"

        // Intent
        val OPERATION_FLAG = "OperationFlag"
        val STAKEHOLDER = "Stakeholder"

        // Inventory tab position
        val PACKETS_CODE = 0
        val SOLD_PACKETS_CODE = 1


        // StakeHolder type id
        val TYPE_ID_BUYER = 0
        val TYPE_ID_SELLER = 1
        val TYPE_ID_BROKER = 2

        // Database
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Tradinget_Database"
        val SD_CARD_PATH = "/storage/emulated/0/Tradinget/Backup/"

        // Tables
        const val TABLE_STAKEHOLDER = "Stakeholder_table"

        // Columns
        const val COLUMN_ID = "ID"
        const val COLUMN_TYPE = "Type"
        const val COLUMN_NAME = "Name"
        const val COLUMN_FIRM_NAME = "Firm_name"
        const val COLUMN_MOBILE_NUMBER = "Mobile_number"
        const val COLUMN_ALT_MOBILE_NUMBER = "Alt_mobile_number"
        const val COLUMN_ADDRESS = "Address"
        const val COLUMN_GST_NUMBER = "GST_number"
        const val COLUMN_PAN_NUMBER = "Pan_number"
    }
}