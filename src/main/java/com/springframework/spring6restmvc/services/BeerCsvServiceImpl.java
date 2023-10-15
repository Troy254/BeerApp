package com.springframework.spring6restmvc.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.springframework.spring6restmvc.model.beerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
@Service
public class BeerCsvServiceImpl implements BeerCsvService{
    @Override
    public List<beerCSVRecord> convertCSV(File csvFile) {

        try {
            List<beerCSVRecord> beerCSVRecords = new CsvToBeanBuilder<beerCSVRecord>(new FileReader(csvFile))
                    .withType(beerCSVRecord.class)
                    .build().parse();
            return beerCSVRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
