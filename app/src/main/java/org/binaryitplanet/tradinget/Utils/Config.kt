package org.binaryitplanet.tradinget.Utils

class Config {
    companion object {

        val OVER_DUE_DATE = "Over due dates"
        val UNDER_DUE_DATE = "Under due dates"
        val REQUIRED_FIELD = "Required field"
        val SUCCESS_MESSAGE = "Success"
        val FAILED_MESSAGE = "Failed"
        val LIST_CACHED_SIZE = 1000
        val DELETE_BUYER_TITLE = "Deleting buyer"
        val DELETE_BROKER_TITLE = "Deleting broker"
        val DELETE_SELLER_TITLE = "Deleting seller"
        val DELETE_PACKET_TITLE = "Deleting packet"
        val DELETE_BUYER_MESSAGE = "Do you want to delete the buyer?"
        val DELETE_BROKER_MESSAGE = "Do you want to delete the broker?"
        val DELETE_SELLER_MESSAGE = "Do you want to delete the seller?"
        val DELETE_PACKET_MESSAGE = "Do you want to delete the packet?"
        val YES_MESSAGE = "Yes"
        val NO_MESSAGE = "No"
        val RUPEE_SIGN = "₹"
        val CTS = "cts"

        // Codes
        val BACKUP_REQUEST_CODE = 101
        val RESTORE_REQUEST_CODE = 102
        val REQUEST_CALL = 103

        // Toolbar titles
        val TOOLBAR_TITLE_HOME = "Home"
        val TOOLBAR_TITLE_BUYER = "Buyers"
        val TOOLBAR_TITLE_SELLER = "Sellers"
        val TOOLBAR_TITLE_BROKER = "Brokers"
        val TOOLBAR_TITLE_INVENTORY = "Inventories"
        val TOOLBAR_TITLE_INVOICE = "Invoice"
        val TOOLBAR_TITLE_CREATE_INVOICE = "Create invoice"
        val TOOLBAR_TITLE_BACKUP_AND_RESTORE = "Backup and restore"
        val TOOLBAR_TITLE_ADD_BUYER = "Add buyer"
        val TOOLBAR_TITLE_ADD_BROKER = "Add broker"
        val TOOLBAR_TITLE_ADD_SELLER = "Add seller"
        val TOOLBAR_TITLE_ADD_PACKET = "Add packet"
        val TOOLBAR_TITLE_ADD_PACKET_DETAILS = "Add packet details"
        val TOOLBAR_TITLE_UPDATE_BUYER = "Update buyer"
        val TOOLBAR_TITLE_UPDATE_BROKER = "Update broker"
        val TOOLBAR_TITLE_UPDATE_SELLER = "Update seller"
        val TOOLBAR_TITLE_UPDATE_PACKET = "Update packet"
        val TOOLBAR_TITLE_UPDATE_PACKET_DETAILS = "Update packet details"

        // Intent
        val OPERATION_FLAG = "OperationFlag"
        val STAKEHOLDER = "Stakeholder"
        val PACKET = "Packet"
        val PACKET_DETAILS = "PacketDetails"

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
        val PDF_DIR_PATH = "/storage/emulated/0/Tradinget/PDF/"

        // Tables
        const val TABLE_STAKEHOLDER = "Stakeholder_table"
        const val TABLE_PACKET = "Packet_table"
        const val TABLE_PACKET_DETAILS = "Packet_details_table"

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
        const val COLUMN_PACKET_NUMBER = "Packet_number"
        const val COLUMN_PACKET_DETAILS_NUMBER = "Packet_details_number"
        const val COLUMN_PACKET_NAME = "Packet_name"
        const val COLUMN_SIEVE = "Sieve"
        const val COLUMN_WEIGHT = "Weight"
        const val COLUMN_SOLD_WEIGHT = "Sold_weight"
        const val COLUMN_REMAINING_WEIGHT = "Remaining_weight"
        const val COLUMN_RATE = "Rate"
        const val COLUMN_PRICE = "Price"
        const val COLUMN_CODE = "Code"
        const val COLUMN_REMARK = "Remark"


        // Invoice
        val PDF_FORMAT = ".pdf"
        val INVOICE_AUTHOR = "BinaryItPlanet"
        val INVOICE_CREATOR = "Tradinget"
        val INVOICE_NAME = "Invoice"
        val TITLE_FONT_SIZE = 20.0f
        val FONT_SIZE = 20.0f
    }
}