package com.example.home_1.models

class Contact {

    var id: Int? = null
    var name: String? = null
    var phoneNumber: String? = null
    var kategoriya: String? = null

    constructor(name: String?, phoneNumber: String?, kategoriya: String?) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.kategoriya = kategoriya
    }

    constructor(id: Int?, name: String?, phoneNumber: String?, kategoriya: String?) {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
        this.kategoriya = kategoriya
    }


}