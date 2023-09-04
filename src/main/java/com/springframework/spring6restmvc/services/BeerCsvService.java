package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.BeerCSVRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
