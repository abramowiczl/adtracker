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
public class OlxService {

    @PostConstruct
    public List<Advertisment> getAdvertisments() throws IOException {
        log.info("Scanning {}", Url.OLX);
        List<Advertisment> advertisments = new ArrayList<>();
        Document doc = Jsoup.connect(Url.OLX.getUrl()).get();

        Elements elements = doc.select("td.offer");

        for (Element element : elements) {
            String price = element.select("p.price").text();
            String location = element.select("span i[data-icon=location-filled]").parents().get(0).text();
            String link = element.select("td.photo-cell a").attr("href");

            Advertisment advertisment = Advertisment.builder()
                    .link(link)
                    .location(location)
                    .price(price)
                    .build();

            advertisments.add(advertisment);
        }

        AdvertismentsRegistry.updateRegistry(advertisments);

        return advertisments;
    }

    @Scheduled(cron = "0 15,45 * * * *")
    public List<Advertisment> run() throws IOException {
        return getAdvertisments();
    }
}
