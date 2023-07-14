package com.example._3dsmarthealthcare.pojo.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class PrepareDTO {


    @SerializedName("organ_diameters")
    public OrganDiametersDTO organDiameters;
    @SerializedName("organ_areas")
    public OrganAreasDTO organAreas;
    @SerializedName("organ_volume")
    public OrganVolumeDTO organVolume;
    @SerializedName("count")
    public CountDTO count;

    @NoArgsConstructor
    @Data
    public static class OrganDiametersDTO {
        @SerializedName("spleen")
        public SpleenDTO spleen;
        @SerializedName("right kidney")
        public RightKidneyDTO rightKidney;// FIXME check this code
        @SerializedName("left kidney")
        public LeftKidneyDTO leftKidney;// FIXME check this code
        @SerializedName("gall bladder")
        public GallBladderDTO gallBladder;// FIXME check this code
        @SerializedName("esophagus")
        public EsophagusDTO esophagus;
        @SerializedName("liver")
        public LiverDTO liver;
        @SerializedName("stomach")
        public StomachDTO stomach;
        @SerializedName("arota")
        public ArotaDTO arota;
        @SerializedName("postcava")
        public PostcavaDTO postcava;
        @SerializedName("pancreas")
        public PancreasDTO pancreas;
        @SerializedName("bladder")
        public BladderDTO bladder;

        @NoArgsConstructor
        @Data
        public static class SpleenDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class RightKidneyDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class LeftKidneyDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class GallBladderDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class EsophagusDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class LiverDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class StomachDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class ArotaDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class PostcavaDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class PancreasDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }

        @NoArgsConstructor
        @Data
        public static class BladderDTO {
            @SerializedName("mean_diameter")
            public String meanDiameter;
        }
    }

    @NoArgsConstructor
    @Data
    public static class OrganAreasDTO {
        @SerializedName("spleen")
        public String spleen;
        @SerializedName("right kidney")
        public String rightKidney;// FIXME check this code
        @SerializedName("left kidney")
        public String leftKidney;// FIXME check this code
        @SerializedName("gall bladder")
        public String gallBladder;// FIXME check this code
        @SerializedName("esophagus")
        public String esophagus;
        @SerializedName("liver")
        public String liver;
        @SerializedName("stomach")
        public String stomach;
        @SerializedName("arota")
        public String arota;
        @SerializedName("postcava")
        public String postcava;
        @SerializedName("pancreas")
        public String pancreas;
        @SerializedName("bladder")
        public String bladder;
    }

    @NoArgsConstructor
    @Data
    public static class OrganVolumeDTO {
        @SerializedName("spleen")
        public String spleen;
        @SerializedName("right kidney")
        public String rightKidney;// FIXME check this code
        @SerializedName("left kidney")
        public String leftKidney;// FIXME check this code
        @SerializedName("gall bladder")
        public String gallBladder;// FIXME check this code
        @SerializedName("esophagus")
        public String esophagus;
        @SerializedName("liver")
        public String liver;
        @SerializedName("stomach")
        public String stomach;
        @SerializedName("arota")
        public String arota;
        @SerializedName("postcava")
        public String postcava;
        @SerializedName("pancreas")
        public String pancreas;
        @SerializedName("bladder")
        public String bladder;
    }

    @NoArgsConstructor
    @Data
    public static class CountDTO {
        @SerializedName("spleen")
        public String spleen;
        @SerializedName("right kidney")
        public String rightKidney;// FIXME check this code
        @SerializedName("left kidney")
        public String leftKidney;// FIXME check this code
        @SerializedName("liver")
        public String liver;
        @SerializedName("stomach")
        public String stomach;
        @SerializedName("postcava")
        public String postcava;
        @SerializedName("bladder")
        public String bladder;
        @SerializedName("13")
        public String $13;
        @SerializedName("14")
        public String $14;
        @SerializedName("15")
        public String $15;
        @SerializedName("The number of organs")
        public String theNumberOfOrgans;// FIXME check this code
    }
}
