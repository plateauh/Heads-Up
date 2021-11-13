package com.najed.headsupv2

class Celebs : ArrayList<CelebItem>()

data class CelebItem (
    val id: Int,
    val name: String,
    val taboo1: String,
    val taboo2: String,
    val taboo3: String
)