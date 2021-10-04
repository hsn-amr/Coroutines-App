package com.example.coroutinesapp

import com.google.gson.annotations.SerializedName

class Advice {

    @SerializedName("slip")
    var slip: AdviceText? =  null

    class AdviceText{

        @SerializedName("id")
        var id: Int? = null

        @SerializedName("advice")
        var advice: String? = null
    }

}