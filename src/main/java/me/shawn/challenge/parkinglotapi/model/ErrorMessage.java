package me.shawn.challenge.parkinglotapi.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Getter @RequiredArgsConstructor
public class ErrorMessage {
    @NonNull
    private String code;
    @NonNull
    private String message;
}
