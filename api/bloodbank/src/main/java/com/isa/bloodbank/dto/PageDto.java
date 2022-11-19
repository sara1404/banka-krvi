package com.isa.bloodbank.dto;

import java.util.List;

public class PageDto<T> {
    List<T> content;
    int totalPages;
}
