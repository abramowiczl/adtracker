package com.priv.adtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Url {
    OTODOM("https://www.otodom.pl/pl/oferty/sprzedaz/dzialka/" +
            "nadarzyn?" +
            "priceMax=600000&" +
            "areaMin=1300&" +
            "areaMax=2000&" +
            "distanceRadius=5&" +
            "by=LATEST&" +
            "direction=DESC"),

    OLX("https://www.olx.pl/nieruchomosci/dzialki/" +
            "nadarzyn_102489/?search%5B" +
            "filter_float_price%3Ato%5D=600000&search%5B" +
            "filter_float_m%3Afrom%5D=1300&search%5B" +
            "filter_float_m%3Ato%5D=2000&search%5B" +
            "order%5D=created_at%3Adesc&search%5B" +
            "dist%5D=5"),

    NIERUCHOMOSCI_ONLINE("https://www.nieruchomosci-online.pl/szukaj.html?3,dzialka,sprzedaz,," +
            "Nadarzyn:20997,,,5,-600000,1300-2000&o=modDate,desc"),

    MORIZON("https://www.morizon.pl/dzialki/najnowsze/pruszkowski/" +
            "nadarzyn/?ps%5B" +
            "price_to%5D=600000&ps%5B" +
            "living_area_from%5D=1300&ps%5B" +
            "living_area_to%5D=2000&ps%5B" +
            "date_filter%5D=3"),

    GRATKA("https://gratka.pl/nieruchomosci/dzialki-grunty/" +
            "nadarzyn?" +
            "promien=5&" +
            "cena-calkowita:max=600000&" +
            "powierzchnia-dzialki-w-m2:max=2000&" +
            "powierzchnia-dzialki-w-m2:min=1300&" +
            "sort=newest");


    private String url;
}
