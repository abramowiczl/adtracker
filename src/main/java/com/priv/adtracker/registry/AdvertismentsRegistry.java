package com.priv.adtracker.registry;

import com.priv.adtracker.model.Advertisment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AdvertismentsRegistry {
    private static Set<String> advertismentLinksRegistry = ConcurrentHashMap.newKeySet();

    @PostConstruct
    public static void initRegistry() throws IOException {
        log.info("Initializing registry...");
        List<String> sheetAdvertismentLinks = Sheet.getAdvertismentLinks();
        AdvertismentsRegistry.advertismentLinksRegistry.addAll(sheetAdvertismentLinks);
        log.info("Initialized registry. Updated registry with {} advertisment links.", sheetAdvertismentLinks.size());
    }

    public static void updateRegistry(List<Advertisment> advertismentList) {
        int before = advertismentLinksRegistry.size();
        List<Advertisment> newAdvertisments = advertismentList.stream()
                .filter(ad -> !advertismentLinksRegistry.contains(ad.getLink()))
                .collect(Collectors.toList());

        try {
            Sheet.writeToSheet(newAdvertisments);
        } catch (IOException e) {
            log.error("Writing to sheet failed due to error: {}", e.toString());
            return;
        }

        newAdvertisments.stream()
                .map(Advertisment::getLink)
                .forEach(link -> advertismentLinksRegistry.add(link));

        int after = advertismentLinksRegistry.size();
        log.info("Updated registry with {} advertisment links.", after - before);
    }
}
