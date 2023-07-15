package com.example._3dsmarthealthcare.pojo.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FormdaDTO {

    @SerializedName("胆囊")
    public String 胆囊;
    @SerializedName("食道")
    public String 食道;
    @SerializedName("主动脉")
    public String 主动脉;
    @SerializedName("胰腺")
    public String 胰腺;
    @SerializedName("膀胱")
    public String 膀胱;
}
