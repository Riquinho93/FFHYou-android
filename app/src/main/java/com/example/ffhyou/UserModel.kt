package com.example.ffhyou

 class UserModel (
    var name: String,
    var email: String,
    var password: String,
    var country: String,
    var institution: String,
    var Neighborhood: String,
    var image: String
){
  constructor() : this("", "", "", "", "", "","" ) {}
 }