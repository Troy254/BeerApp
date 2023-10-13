package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.beerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<beerCSVRecord> convertCSV(File csvFile);
}
