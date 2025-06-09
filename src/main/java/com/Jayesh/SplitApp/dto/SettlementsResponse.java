package com.Jayesh.SplitApp.dto;

import java.math.BigDecimal;

public record SettlementsResponse(String from, String to, BigDecimal amount) { }
