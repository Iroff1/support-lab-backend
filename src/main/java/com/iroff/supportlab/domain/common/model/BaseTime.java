package com.iroff.supportlab.domain.common.model;

import java.time.LocalDateTime;

public interface BaseTime {
	LocalDateTime getCreatedAt();

	LocalDateTime getModifiedAt();
}
