package com.ep.custom_honor_library.bean

import com.google.gson.annotations.SerializedName

class EventTJBean(
    @SerializedName("adNetworkPlatformName")
    var advertisingName: String,
    @SerializedName("networkPlacementId")
    var networkId: String,
    @SerializedName("ecpm")
    var adEcpmStr: String,
    @SerializedName("biddingType")
    var bdType: Int
) {
}