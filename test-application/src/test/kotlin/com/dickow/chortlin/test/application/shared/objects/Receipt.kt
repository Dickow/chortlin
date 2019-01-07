package com.dickow.chortlin.test.application.shared.objects

class Receipt constructor() {
    constructor(itemId: Int, price: Int, itemName: String, sellerId: String) : this() {
        this.itemId = itemId
        this.price = price
        this.itemName = itemName
        this.sellerId = sellerId
    }

    private var itemId: Int = 0
    private var price: Int = 0
    private lateinit var itemName: String
    private lateinit var sellerId: String

}