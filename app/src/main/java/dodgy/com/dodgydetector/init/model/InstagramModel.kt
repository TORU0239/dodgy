package dodgy.com.dodgydetector.init.model

import com.google.gson.annotations.SerializedName

/**
 * Created by toruchoi on 18/11/2017.
 */

data class InstagramModel(val next:String, val posts:List<InstagramMediaModel>)

data class InstagramMediaModel(val id:String,
                          @SerializedName("__typename") val typeName:String,
                          @SerializedName("taken_at_timestamp") val takenTimeStamp:String,
                          @SerializedName("display_url") val displayUrl:String,
                          @SerializedName("thumbnail_src") val thumbnailUrl:String)