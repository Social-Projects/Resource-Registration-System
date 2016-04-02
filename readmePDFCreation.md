Classes responsible for  for PDF generation are : 
	org.registrator.community.service.impl.PrintServiceImpl
	org.registrator.community.dto.HeaderDTO
	org.registrator.community.dto.ExtractDTO
	org.registrator.community.dto.MandatToExtractDTO
	org.registrator.community.dto.InputDTO
	
1. To change header of PDF document check  org.registrator.community.dto.HeaderDTO class
and its methods : 
	a) addSentenceToHeaderContent();  - to fill header by text content
	b) setImage(); - to set URL of image, and set size of image

2. For creating pdf document "Доручення на витяг" check org.registrator.community.service.impl.PrintServiceImpl
 and it's method: 
 
   printProcuration(Integer inquiryId); - api for user
   createMandatToExtractPdf(MandatToExtractDTO extract, HeaderDTO header); - to generate all content of document with header

3. For creating pdf document "Витяг" check org.registrator.community.service.impl.PrintServiceImpl
 and it's method: 
 
 	printExtract(Integer inquiryId); - api for user 
 	createExtractPdf(ExtractDTO extract, HeaderDTO header); - to generate all content of document with table and header
 	createExtractTable(ExtractDTO extract); - to generates table to current pdf document
 	
4. For creating pdf document "Доручення на внесення" check org.registrator.community.service.impl.PrintServiceImpl
 and it's method: 
 
 	printProcurationOnSubmitInfo(Integer inquiryId); - api for user 
 	createInputPdf(InputDTO extract, HeaderDTO header); -  to generate all content of document with table and header
 	createInputTable(InputDTO extract); - to generates table to current pdf document
 
 		
 	
   