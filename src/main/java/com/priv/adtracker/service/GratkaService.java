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
public class GratkaService {

    @PostConstruct
    public List<Advertisment> getAdvertisments() throws IOException {
        log.info("Scanning {}", Url.GRATKA);
        List<Advertisment> advertisments = new ArrayList<>();
        Document doc = Jsoup.connect(Url.GRATKA.getUrl()).get();

        Elements elements = doc.select("article.teaserUnified");

        for (Element element : elements) {
            String price = element.select("p.teaserUnified__price").text();
            String area = element.select("li.teaserUnified__listItem").text();
            String pricePerMeter = element.select("span.teaserUnified__additionalPrice").text();
            String location = element.select("span.teaserUnified__location").text();
            String link = element.select("a").attr("href");

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

    @Scheduled(cron = "0 0,30 * * * *")
    public List<Advertisment> run() throws IOException {
        return getAdvertisments();
    }
}
