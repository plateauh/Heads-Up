package com.najed.headsupv2

import com.google.gson.annotations.SerializedName

class Celebs : ArrayList<CelebItem>()

data class CelebItem (

    @SerializedName("name")
    val name: String,

    @SerializedName("taboo1")
    val taboo1: String,

    @SerializedName("taboo2")
    val taboo2: String,

    @SerializedName("taboo3")
    val taboo3: String
)