var table, test, temp;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-foreign-language-catalog",
	    method: 'POST',
	    dataType: "json",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	    	var foreignLanguageCatalog = data.foreignLanguageCatalog;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	    	table = $('#table-foreign-language-catalog').DataTable({
	   		 data: foreignLanguageCatalog,
	           columns: [
	              
	               { "data": "foreignLanguageCatalogId" },
	               { "data": "foreignLanguageCatalogName" }
	              
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
	    	
			$(document).contextmenu({
			    delegate: "#table-foreign-language-catalog td",
			menu: [
			  {title: edit, cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: remove, cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var foreignLanguageCatalog = table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						changeForeignLanguageCatalog(foreignLanguageCatalog)
						break;
					case "delete":
						deleteForeignLanguageCatalog(foreignLanguageCatalog);
						break;
					}
				},
				beforeOpen: function(event, ui) {
					var $menu = ui.menu,
						$target = ui.target,
						extraData = ui.extraData;
					ui.menu.zIndex(9999);
			    }
			  });
	    }
	});
});

function changeForeignLanguageCatalog(foreignLanguageCatalog) {
 	new Promise(function(resolve, reject) {
 		resolve(new modal("#change-foreign-language-catalog").setting({
 			data: foreignLanguageCatalog,
 			columns: [
 			          {
 			        	  name: foreignLanguageCatalogId,
 			        	  value: "foreignLanguageCatalogId",
 			        	  
 			          },
 			         {
 			        	  name: foreignLanguageCatalogName,
 			        	  value: "foreignLanguageCatalogName"
 			          }
 			          ],
 			          title: titleEditForeignLanguageCatalog,
 			          action: {
 			        	  func: "saveForeignLanguageCatalog()",
 			        	  name: update
 			          }
 		}).render());
	}).then(function(modal) {
		modalChange = modal;
		$("#change-foreign-language-catalog #modal-template").modal('show');
	})
 	
 }

function newForeignLanguageCatalog() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-foreign-language-catalog").setting({
			data: {},
			columns: [	
			          {
			        	  name: foreignLanguageCatalogId,
			        	  value: "foreignLanguageCatalogId",
			        	  
	
			          },
			          
			          {
			        	  name: foreignLanguageCatalogName,
			        	  value: "foreignLanguageCatalogName"
			          }
			          ],
			          title: titleNewForeignLanguageCatalog,
			          action: {
			        	  func: "addForeignLanguageCatalog()",
			        	  name: add
			          }
		}).render());
	}).then(function(modal) {
		modalAdd = modal
		$("#add-foreign-language-catalog #modal-template").modal('show');
	})
}

function saveForeignLanguageCatalog() {
	openLoader();
	var foreignLanguageCatalog = modalChange.data();

	$.ajax({
	    url: "/bkeuniv/control/update-foreign-language-catalog",
	    type: 'post',
	    data: foreignLanguageCatalog,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.result) {
	    		table.rows().indexes().data().filter(function(e, index) {
	    			if(e.foreignLanguageCatalogCode == foreignLanguageCatalog.foreignLanguageCatalogCode) {
	    				e.index = index;
	    				return true;
	    			}
	    		}).map(function(el, index){
	    			el.foreignLanguageCatalogId = foreignLanguageCatalog.foreignLanguageCatalogId;
	    			el.foreignLanguageCatalogName = foreignLanguageCatalog.foreignLanguageCatalogName;
	    			table.row(el.index).data(el);
	    		})
	    		
	    		setTimeout(function() {
	    			closeLoader();
	    			$("#change-foreign-language-catalog #modal-template").modal('hide');
	    			alertify.success(data.result);
	    		}, 500);
	    	} else {
	    		setTimeout(function() {
	    			closeLoader();
	    			alertify.success(JSON.stringify(data._ERROR_MESSAGE_));
	    		}, 500);
	    	}
	    },
	    error: function(err) {
	    	setTimeout(function() {
	    		closeLoader();
	    		alertify.success(JSON.stringify(err));
	    	}, 500);
	    	console.log(err);
	    }
	})
}

function deleteForeignLanguageCatalog(foreignLanguageCatalog) {
	alertify.confirm("Confirm", BkEunivTitleDeleteForeignLanguageCatalog + " ID = " + foreignLanguageCatalog.foreignLanguageCatalogCode,
	function(){
		openLoader();

		$.ajax({
		    url: "/bkeuniv/control/delete-foreign-language-catalog",
		    type: 'post',
		    data: foreignLanguageCatalog,
		    datatype:"json",
		    success: function(data) {
		    	if(!!data.result) {
		    		table.rows().indexes().data().filter(function(e, index) {
		    			if(e.foreignLanguageCatalogCode == foreignLanguageCatalog.foreignLanguageCatalogCode) {
		    				e.index = index;
		    				return true;
		    			}
		    		}).map(function(el, index){
		    			table.row(el.index).remove().draw();
		    		})
		    		
		    		setTimeout(function() {
		    			closeLoader();
		    			alertify.success(data.result);
		    		}, 500);
		    	} else {
		    		setTimeout(function() {
		    			closeLoader();
		    			alertify.success(JSON.stringify(data.result));
		    		}, 500);
		    	}
		    },
		    error: function(err) {
		    	setTimeout(function() {
		    		closeLoader();
		    		alertify.success(JSON.stringify(err));
		    	}, 500);
		    	console.log(err);
		    	
		    }
		})
	},
	function(){
	});
}
 



function addForeignLanguageCatalog(){
	openLoader();
	var newForeignLanguageCatalog = modalAdd.data();

	$.ajax({
	    url: "/bkeuniv/control/create-foreign-language-catalog",
	    type: 'post',
	    data: newForeignLanguageCatalog,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.foreignLanguageCatalog) {
	    		table.row.add(data.foreignLanguageCatalog).draw();
		    	setTimeout(closeLoader(), 500);
		    	
		    	$("#add-foreign-language-catalog #foreignLanguageCatalogId").val("");
				$("#add-foreign-language-catalog #foreignLanguageCatalogName").val("");
				
				alertify.success('Created new row');
	    	} else {
	    		setTimeout(function() {
		    		closeLoader();
		    		alertify.success(JSON.stringify(data._ERROR_MESSAGE_LIST_));
		    	}, 500);
	    	}
	    	
	    },
	    error: function(err) {
	    	setTimeout(function() {
	    		closeLoader();
	    		alertify.success(JSON.stringify(err));
	    	}, 500);
	    	console.log(err);
	    }
	})
}


function openLoader() {
 	if($(".loader").hasClass("hidden-loading")) {
 		$(".loader").removeClass("hidden-loading");
 	}
 }
 
 function closeLoader() {
 	if(!$(".loader").hasClass("hidden-loading")) {
 		$(".loader").addClass("hidden-loading");
 	}
 }
