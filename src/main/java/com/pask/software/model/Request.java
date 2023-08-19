package com.pask.software.model;

import java.time.LocalDateTime;

public record Request(String ip, LocalDateTime timestamp) {
}
