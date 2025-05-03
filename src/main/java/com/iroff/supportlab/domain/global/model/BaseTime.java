package com.iroff.supportlab.domain.global.model;

import java.time.LocalDateTime;

public interface BaseTime {
	LocalDateTime getCreatedAt();

	LocalDateTime getModifiedAt();
}
