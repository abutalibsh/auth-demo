package com.inma.invest.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageTaskDto {
	private List<TaskDto> tasks=new ArrayList<TaskDto>();
	private long totalElements;
}
