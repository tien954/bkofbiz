<head>
	<!-- import DataTable -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dataTables.bootstrap.min.js"></script>
	
	<!-- import css component -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/foreign-language-catalog.css">
		

</head>

<script>

var remove = '${uiLabelMap.BkEunivRemove}';
var edit = '${uiLabelMap.BkEunivEdit}';


var foreignLanguageCatalogId = '${foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogId}';
var foreignLanguageCatalogName = '${foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogName}';

var add = '${uiLabelMap.BkEunivAddRow}';
var update = '${uiLabelMap.BkEunivUpdate}';
var titleNewForeignLanguageCatalog = '${foreignLanguageCatalogUiLabelMap.BkEunivNewForeignLanguageCatalog}';
var titleEditForeignLanguageCatalog = '${foreignLanguageCatalogUiLabelMap.BkEunivEditForeignLanguageCatalog}';
var BkEunivTitleDeleteForeignLanguageCatalog = '${foreignLanguageCatalogUiLabelMap.BkEunivTitleDeleteForeignLanguageCatalog}';

</script>

<!-- import js component -->
<script src="/resource/bkeuniv/js/foreignlanguagecatalog.js"></script>

<body>
<div class="body">
		<div class="foreign-language-catalog">
		<div class="title">
			<a href="#" class="title-hyperlink">
				${uiLabelMap.BkEunivForeignLanguageCatalog}
			</a>
		</div>
		<div id="button-add-foreign-language-catalog" onClick="newForeignLanguageCatalog()">
			${uiLabelMap.BkEunivAdd}
		</div>
		
		<table id="table-foreign-language-catalog" class="table table-striped table-bordered">
			<thead>
				<td>${foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogId}</td>
				<td>${foreignLanguageCatalogUiLabelMap.BkEunivForeignLanguageCatalogName}</td>
			<tbody>
			</tbody>
		</table>
	</div>
</div>

<div class="loader hidden-loading"></div>
<div id="add-foreign-language-catalog"></div>
<div id="change-foreign-language-catalog"></div>
