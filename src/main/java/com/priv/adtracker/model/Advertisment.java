package com.priv.adtracker.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Advertisment {
    @Builder.Default()
    String link = "";
    @Builder.Default()
    String price = "";
    @Builder.Default
    String pricePerMeter = "";
    @Builder.Default
    String area = "";
    @Builder.Default
    String location = "";
    @Builder.Default
    String seller = "";
}
