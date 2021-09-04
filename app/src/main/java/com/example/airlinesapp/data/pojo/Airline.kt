package com.example.airlinesapp.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Airlines")
data class Airline(
    val __v: Int?=null,
    val _id: String?=null,
    val country: String?=null,
    val createdDate: String?=null,
    val established: String?=null,
    val head_quaters: String?=null,
    val id: Double?=null,
    val logo: String?=null,
    @PrimaryKey
    val name: String,
    val slogan: String?=null,
    val website: String?=null
):Serializable