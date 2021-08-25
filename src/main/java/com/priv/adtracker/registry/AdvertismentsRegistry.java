package com.priv.adtracker.registry;

import com.priv.adtracker.model.Advertisment;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class AdvertismentsRegistry {
    private static Set<Advertisment> advertisments = ConcurrentHashMap.newKeySet();

    public static void updateRegistry(List<Advertisment> advertismentList) {
        int before = advertisments.size();
        advertisments.addAll(advertismentList);
        int after = advertisments.size();
        log.info("Updated registry with {} advertisments.", after - before);
    }
}
