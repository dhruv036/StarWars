package io.dhruv.starwars.constant


object RandomColors {
    val colors = arrayOf(
        "#BAE5F4",
        "#FFF3D3",
        "#E2D2FE",
        "#EDF8E7",
        "#FCCDCD"
    )

    fun getRandomColor() =  colors.random()
}