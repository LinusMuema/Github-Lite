package com.moose.githublite.model

import java.util.*
import kotlin.collections.ArrayList

data class metadata(
    val total_count : Int,
    val incomplete_results : Boolean,
    val items : ArrayList<Objects>
)
