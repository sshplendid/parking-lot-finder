package me.shawn.challenge.parkinglotapi.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder @Getter
public class CommonResponse<T> {
    private String status;
    private String message;
    private long size;
    private List<T> data;
}
