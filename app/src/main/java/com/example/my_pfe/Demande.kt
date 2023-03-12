package com.example.my_pfe

data class Demande(
    val nom: String ?= null,
    val prenom: String ?= null,
    val typeStage: String ?= null,
    val pdfUrl: String  ?= null,
    var confirm : String  ?= null,
    var date : java.util.Date  ?= null,
    var announceID : String ?= null,
    var studentId : String ?= null
)