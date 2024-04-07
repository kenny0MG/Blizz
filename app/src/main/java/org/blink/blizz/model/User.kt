package org.blink.blizz.model

data class User(val uid:String = "",
                val name:String="",
                var phone:String="",
                var sex:String="",
                var sexPartner:String ="",
                var couple: Hobie=Hobie("","",0,false),
                var birthday:String = "",
                var bio:String ="",
                var images: Map<String,PostItem> = emptyMap(),
                var hobie: Map<String,Hobie> = emptyMap(),
                var personality:Double =0.00,
                var score:Double = 400.00,
                val verification:Boolean = false,
                var state:String ="",
                var longitude: Double=0.0,
                var latitude: Double= 0.0):java.io.Serializable
{
}