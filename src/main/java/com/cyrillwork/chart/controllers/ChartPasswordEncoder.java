package com.cyrillwork.chart.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ChartPasswordEncoder extends BCryptPasswordEncoder {
    public ChartPasswordEncoder(){
        super(8);
    }
}
