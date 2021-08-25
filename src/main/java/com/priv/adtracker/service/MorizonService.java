package com.priv.adtracker.service;

import com.priv.adtracker.model.Advertisment;
import com.priv.adtracker.model.Url;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class MorizonService {

    @PostConstruct
    public List<Advertisment> getAdvertisments() throws IOException {
        Document doc = Jsoup.connect(Url.MORIZON.getUrl()).get();

        return null;
    }

    @Scheduled(cron = "0 5,35 * * * *")
    public List<Advertisment> run() throws IOException {
        return getAdvertisments();
    }
}
