<head>
	<!-- import DataTable -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dataTables.bootstrap.min.js"></script>
	
	<!-- import css component -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/foreignlanguage.css">
		

</head>

<script>

var remove = '${uiLabelMap.BkEunivRemove}';
var edit = '${uiLabelMap.BkEunivEdit}';

var foreignLanguageId = '${foreignLanguageUiLabelMap.BkEunivForeignLanguageId}';
var staffId = '${foreignLanguageUiLabelMap.BkEunivStaffId}';
var listen = '${foreignLanguageUiLabelMap.BkEunivListen}';
var speaking = '${foreignLanguageUiLabelMap.BkEunivSpeaking}';
var reading = '${foreignLanguageUiLabelMap.BkEunivReading}';
var writing = '${foreignLanguageUiLabelMap.BkEunivWriting}';
var titleEditForeignLanguage = '${foreignLanguageUiLabelMap.BkEunivEditForeignLanguage}';
var add = '${uiLabelMap.BkEunivAddRow}';
var update = '${uiLabelMap.BkEunivUpdate}';
var titleNewForeignLanguage = '${foreignLanguageUiLabelMap.BkEunivNewForeignLanguage}';
var BkEunivTitleDeleteForeignLanguage = '${foreignLanguageUiLabelMap.BkEunivTitleDeleteForeignLanguage}';

</script>

<!-- import js component -->
<script src="/resource/bkeuniv/js/foreignlanguage.js"></script>

<body>
<div class="body">
		<div class="foreign-language">
		<div class="title">
			<a href="#" class="title-hyperlink">
				${uiLabelMap.BkEunivForeignLanguage}
			</a>
		</div>
		<div id="button-add-foreign-language" onClick="newForeignLanguage()">
			${uiLabelMap.BkEunivAdd}
		</div>
		
		<table id="table-foreign-language" class="table table-striped table-bordered">
			<thead>
				<td>${foreignLanguageUiLabelMap.BkEunivForeignLanguageId}</td>
				<td>${foreignLanguageUiLabelMap.BkEunivStaffId}</td>
				<td>${foreignLanguageUiLabelMap.BkEunivListen}</td>
				<td>${foreignLanguageUiLabelMap.BkEunivSpeaking}</td>
				<td>${foreignLanguageUiLabelMap.BkEunivReading}</td>
				<td>${foreignLanguageUiLabelMap.BkEunivWriting}</td>
			<tbody>
			</tbody>
		</table>
	</div>
</div>

<div class="loader hidden-loading"></div>
<div id="add-foreign-language"></div>
<div id="change-foreign-language"></div>
