package org.registrator.community.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import org.springframework.security.access.prepost.PreAuthorize;

import com.itextpdf.text.Document;

public interface PrintService {

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REGISTRATOR')")
	ByteArrayOutputStream printProcuration(Integer inquiryId)  throws IOException, DocumentException;

	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
	ByteArrayOutputStream printExtract(Integer inquiryId) throws IOException, DocumentException;

	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
	ByteArrayOutputStream printProcurationOnSubmitInfo(Integer inquiryId) throws IOException, DocumentException;

}