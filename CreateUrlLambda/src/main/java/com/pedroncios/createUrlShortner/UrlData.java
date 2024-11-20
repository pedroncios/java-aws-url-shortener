package com.pedroncios.createUrlShortner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlData {
    private String orignalUrl;
    private long expirationTime;
}
