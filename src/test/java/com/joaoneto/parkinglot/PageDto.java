package com.joaoneto.parkinglot;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDto<T>{
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
}
