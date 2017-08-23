<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign columns=[
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"data": "researchSpecialityId"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityName?j_string,
			"data": "researchSpecialityName"
		}
	] />
	
	<#assign fields=[
		"researchSpecialityId",
		"researchSpecialityName"		
	] />
	
	<#assign columnsChange=[
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"value": "researchSpecialityId"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityName?j_string,
			"value": "researchSpecialityName"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"value": "researchSpecialityId"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityName?j_string,
			"value": "researchSpecialityName"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-research-speciality-management" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-research-speciality-management" 
		urlAdd="/bkeuniv/control/create-research-speciality-management" 
		urlDelete="/bkeuniv/control/delete-research-speciality-management" 
		keysId=["researchSpecialityId"] 
		fieldDataResult = "researchSpeciality" 
		titleChange="Edit"
		titleNew="Add"
		titleDelete="Delete"
		jqTitle="Research Speciality"
	/>
</div>