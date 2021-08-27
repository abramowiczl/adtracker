package com.priv.adtracker.service;

import com.priv.adtracker.model.Advertisment;
import com.priv.adtracker.model.Url;
import com.priv.adtracker.registry.AdvertismentsRegistry;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NieruchomosciOnlineService {

    @PostConstruct
    public List<Advertisment> getAdvertisments() throws IOException {
        log.info("Scanning {}", Url.NIERUCHOMOSCI_ONLINE);
        List<Advertisment> advertisments = new ArrayList<>();
        Document doc = Jsoup.connect(Url.NIERUCHOMOSCI_ONLINE.getUrl()).get();

        Elements elements = doc.select("div.tertiary");

        for (Element element : elements) {
            String price = element.select("p.title-a.primary-display span:not([class])").text();
            String area = element.select("p.title-a.primary-display span.area").text();
            String pricePerMeter = element.select("p.title-a.primary-display span.none").text();
            String location = element.select("p.province span").text();
            String link = element.select("h2 a").attr("href");

            Advertisment advertisment = Advertisment.builder()
                    .link(link)
                    .area(area)
                    .location(location)
                    .price(price)
                    .pricePerMeter(pricePerMeter)
                    .build();

            advertisments.add(advertisment);
        }

        AdvertismentsRegistry.updateRegistry(advertisments);

        return advertisments;
    }

    @Scheduled(cron = "0 10,40 * * * *")
    public List<Advertisment> run() throws IOException {
        return getAdvertisments();
    }
}
