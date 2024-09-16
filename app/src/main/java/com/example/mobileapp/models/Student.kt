package com.example.mobileapp.models

class Student {
    var imageUrl: String=""
    var firstname: String=""
    var lastname: String=""
    var gender: String=""
    var desc: String=""
    var id: String=""

    constructor(imageUrl: String,firstname: String,lastname:String,gender:String,desc: String,id: String){
        this.imageUrl=imageUrl
        this.firstname=firstname
        this.lastname=lastname
        this.gender=gender
        this.desc=desc
        this.id=id
    }
    constructor()

}