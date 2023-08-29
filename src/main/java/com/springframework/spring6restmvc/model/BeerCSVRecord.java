package com.springframework.spring6restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerCSVRecord {
  private Integer row;
  private Integer count;
  private String abv;
  private String ibu;
  private Integer id;
  private String beer;
  private String style;
  private Integer breweryId;
  private float ounces;
  private String style2;
  private String city;
  private String state;
  private String label;
}