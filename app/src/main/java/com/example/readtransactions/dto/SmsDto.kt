package com.example.readtransactions.dto

import java.io.Serializable

/**
 * ================================================================================
 *
 * Created by Sandeep on 2019-06-03.
 *
 * ================================================================================
 */
class SmsDto {
    var amount : Int = 0
    var from : String = ""
    var transactionType : TransactionType ?= null
}

class TransactionType {
    companion object {
        const val CREDIT = "credit"
        const val DEBIT = "debit"
    }
}