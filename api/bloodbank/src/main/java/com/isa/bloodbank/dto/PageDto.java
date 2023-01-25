package com.isa.bloodbank.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PageDto<T> {
    List<T> content;
    int totalElements;
}
