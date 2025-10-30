package com.bautista.dto;

import lombok.Data;
import java.util.List;

@Data
public class FilterOptions {
    private List<String> categories;
    private List<String> brands;
    private List<String> colors;
    private List<String> genders;
}