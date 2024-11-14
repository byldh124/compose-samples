package com.moondroid.compose.navigation.route

import kotlinx.serialization.Serializable

interface Destination

@Serializable
object First: Destination

@Serializable
object Second: Destination

@Serializable
object Third: Destination

@Serializable
object NewNav: Destination

@Serializable
object NewNavFirst :Destination

@Serializable
object NewNavSecond: Destination

