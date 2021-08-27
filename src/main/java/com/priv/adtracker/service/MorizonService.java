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
public class MorizonService {

    @PostConstruct
    public List<Advertisment> getAdvertisments() throws IOException {
        log.info("Scanning {}", Url.MORIZON);
        List<Advertisment> advertisments = new ArrayList<>();
        Document doc = Jsoup.connect(Url.MORIZON.getUrl()).get();

        Elements elements = doc.select("section.single-result__content.single-result__content--height");

        for (Element element : elements) {
            try {
                String price = element.select("p.single-result__price").get(0).text();
                String area = element.select("ul.param.list-unstyled.list-inline li").text();
                String pricePerMeter = element.select("p.single-result__price").get(1).text();
                String location = element.select("h2.single-result__title").text();
                String link = element.select("a").attr("href");

                Advertisment advertisment = Advertisment.builder()
                        .link(link)
                        .area(area)
                        .location(location)
                        .price(price)
                        .pricePerMeter(pricePerMeter)
                        .build();

                advertisments.add(advertisment);
            } catch (Exception e) {
                log.error("Error while scanning MORIZON: {}", e.toString());
                continue;
            }
        }

        AdvertismentsRegistry.updateRegistry(advertisments);

        return advertisments;
    }

    @Scheduled(cron = "0 5,35 * * * *")
    public List<Advertisment> run() throws IOException {
        return getAdvertisments();
    }
}
