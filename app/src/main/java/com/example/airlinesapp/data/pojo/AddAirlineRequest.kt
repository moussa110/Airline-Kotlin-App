package com.example.airlinesapp.data.pojo

import java.io.Serializable


data class AddAirlineRequest(
    var country: String = "",
    var head_quaters: String = "",
    var name: String = "",
    var slogan: String = ""
):Serializable