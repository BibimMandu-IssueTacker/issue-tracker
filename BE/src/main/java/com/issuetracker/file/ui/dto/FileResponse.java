package com.issuetracker.file.ui.dto;

import com.issuetracker.file.application.dto.FileMetadata;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FileResponse {

	private String fileName;
	private String url;

	public static FileResponse from(FileMetadata fileMetadata) {
		return new FileResponse(fileMetadata.getFileName(), fileMetadata.getUrl());
	}
}
