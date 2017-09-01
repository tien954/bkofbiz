<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign columns=[
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"data": "researchSpecialityId"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivFromDate?j_string,
			"data": "fromDate"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivThruDate?j_string,
			"data": "thruDate"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffId"
		}
	] />
	
	
	
	
	<#assign fields=[
		"staffResearchSpecialityId",
		"staffId",
		"researchSpecialityId",
		"fromDate",
		"thruDate"
	] />
	
	<#assign columnsChange=[
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"value": "researchSpecialityId"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivFromDate?j_string,
			"value": "fromDate",
			"type": "date"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivThruDate?j_string,
			"value": "thruDate",
			"type": "date"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
			
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"value": "researchSpecialityId"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivFromDate?j_string,
			"value": "fromDate",
			"type": "date"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivThruDate?j_string,
			"value": "thruDate",
			"type": "date"
		},
		{
			"name": staffResearchSpecialityUiLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="DataTable-StaffResearchSpeciality"
		urlData="/bkeuniv/control/get-staff-research-speciality" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-staff-research-speciality" 
		urlAdd="/bkeuniv/control/create-staff-research-speciality" 
		urlDelete="/bkeuniv/control/delete-staff-research-speciality" 
		keysId=["staffResearchSpecialityId"] 
		fieldDataResult = "staffResearchSpeciality" 
		titleChange="Edit"
		titleNew="Add"
		titleDelete="Delete"
		jqTitle="Staff Research Speciality"
		contextmenu=true;
	/>
</div>
