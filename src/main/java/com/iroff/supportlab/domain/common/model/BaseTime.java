package com.iroff.supportlab.domain.common.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTime {
	protected LocalDateTime createdAt;
	protected LocalDateTime modifiedAt;
}