package com.iroff.supportlab.domain.common.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTime {
	protected LocalDateTime createdAt;
	protected LocalDateTime modifiedAt;
}