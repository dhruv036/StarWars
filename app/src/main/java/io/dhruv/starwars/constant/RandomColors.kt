package io.dhruv.starwars.constant

import io.dhruv.starwars.R
import kotlin.random.Random

object RandomColors {
    val colors = arrayOf(
        R.color.blue,
        R.color.yellow,
        R.color.purple,
        R.color.green,
        R.color.orange
    )

    fun getRandomColor() =  colors[Random.nextInt(colors.size)]
}