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
public class OtodomService {

    private final static String OTODOM_DOMAIN = "https://www.otodom.pl/";


    @PostConstruct
    public List<Advertisment> getAdvertisments() throws IOException {
        log.info("Scanning {}", Url.OTODOM.toString());
        List<Advertisment> advertisments = new ArrayList<>();
        Document doc = Jsoup.connect(Url.OTODOM.getUrl()).get();

        Elements elements = doc.select("li.css-x9km8e.es62z2j31");

        for (Element element : elements) {
            String price = element.select("p.css-lk61n3.es62z2j20").text();
            String area = element.select("p.css-xlgkh5.es62z2j22 span").get(0).text();
            String pricePerMeter = element.select("p.css-xlgkh5.es62z2j22 span").get(1).text();
            String seller = element.select("p.css-1cmayta.es62z2j13").text();
            String location = element.select("span.css-17o293g.es62z2j23").text();
            String linkPath = element.select("a").attr("href");

            Advertisment advertisment = Advertisment.builder()
                    .link(OTODOM_DOMAIN + linkPath)
                    .area(area)
                    .location(location)
                    .price(price)
                    .pricePerMeter(pricePerMeter)
                    .seller(seller)
                    .build();

            advertisments.add(advertisment);
        }

        AdvertismentsRegistry.updateRegistry(advertisments);

        return advertisments;
    }

    @Scheduled(cron = "0 20,50 * * * *")
    public List<Advertisment> run() throws IOException {
        return getAdvertisments();
    }
}
