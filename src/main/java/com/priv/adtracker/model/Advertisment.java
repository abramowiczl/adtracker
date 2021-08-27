package com.priv.adtracker.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

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
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("Europe/Berlin"));

    public List<Object> asList() {
        return Arrays.asList(
                this.timestamp.toString(),
                this.link,
                this.location,
                this.price,
                this.pricePerMeter,
                this.area,
                this.seller
        );
    }
}
