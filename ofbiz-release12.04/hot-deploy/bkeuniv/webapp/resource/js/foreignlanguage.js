var table, test, temp;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-foreign-language",
	    method: 'POST',
	    dataType: "json",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	    	var foreignLanguage = data.foreignLanguage;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	    	table = $('#table-foreign-language').DataTable({
	   		 data: foreignLanguage,
	           columns: [
	               { "data": "foreignLanguageId" },
	               { "data": "staffId" },
	               { "data": "listen" },
	               { "data": "speaking" },
	               { "data": "reading" },
	               { "data": "writing" }	              
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
	    	
			$(document).contextmenu({
			    delegate: "#table-foreign-language td",
			menu: [
			  {title: edit, cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: remove, cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var foreignLanguage = table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						changeForeignLanguage(foreignLanguage)
						break;
					case "delete":
						deleteForeignLanguage(foreignLanguage);
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

function changeForeignLanguage(foreignLanguage) {
 	new Promise(function(resolve, reject) {
 		resolve(new modal("#change-foreign-language").setting({
 			data: foreignLanguage,
 			columns: [
 			          {
 			        	  name: foreignLanguageId,
 			        	  value: "foreignLanguageId",
 			        	  edit: false
 			          },
 			         {
 			        	  name: staffId,
 			        	  value: "staffId"
 			          },
 			          {
 			        	  name: listen,
 			        	  value: "listen"
 			          },
 			          {
 			        	  name: speaking,
 			        	  value: "speaking"
 			          },
 			          {
 			        	  name: reading,
 			        	  value: "reading"
 			          },
 			          {
 			        	  name: writing,
 			        	  value: "writing"
 			          }
 			          ],
 			          title: titleEditForeignLanguage,
 			          action: {
 			        	  func: "saveForeignLanguage",
 			        	  name: update
 			          }
 		}).render());
 	}).then(function(val) {
 		$("#change-foreign-language #modal-template").modal('show');
 	})
 	
 }

function newForeignLanguage() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-foreign-language").setting({
			data: {},
			columns: [	
			          {
			        	  name: staffId,
			        	  value: "staffId"
			          },
			          {
			        	  name: listen,
			        	  value: "listen"
			          },
			          
			          {
			        	  name: speaking,
			        	  value: "speaking"
			          },
			          {
			        	  name: reading,
			        	  value: "reading"
			          },
			          {
			        	  name: writing,
			        	  value: "writing"
			          }
			          ],
			          title: titleNewForeignLanguage,
			          action: {
			        	  func: "addForeignLanguage",
			        	  name: add
			          }
		}).render());
	}).then(function(val) {
		$("#add-foreign-language #modal-template").modal('show');
	})
}

function saveForeignLanguage(foreignLanguageOld) {
 	openLoader();
 	var foreignLanguage = {
 		"foreignLanguageId": foreignLanguageOld["foreignLanguageId"],
 		"staffId": $("#staffid").val().trim(),
  		"listen": $("#listen").val().trim(),
  		"speaking": $("#speaking").val().trim(),
  		"reading": $("#reading").val().trim(),
  		"writing": $("#writing").val().trim()		
  	}
  
 	$.ajax({
 	    url: "/bkeuniv/control/update-foreign-language",
 	    type: 'post',
 	    data: foreignLanguage,
 	    datatype:"json",
 	    success: function(data) {
 	    	table.rows().indexes().data().filter(function(e, index) {
 	    		if(e.foreignLanguageId == foreignLanguage.foreignLanguageId) {
 	    			e.index = index;
 	    			return true;
 	    		}
 	    	}).map(function(el, index){
 	    		el.staffId = foreignLanguage.staffId;
 	    		el.listen = foreignLanguage.listen;
 	    		el.speaking = foreignLanguage.speaking;
 	    		el.reading = foreignLanguage.reading;
 	    		el.writing = foreignLanguage.writing;
 	    		
 	    		table.row(el.index).data(el);
 	    	})
     		
 	    	setTimeout(function() {
 	    		closeLoader();
 	    		$("#change-foreign-language #modal-template").modal('hide');
 				alertify.success(data.result);
  	    	}, 500);
  	    },
  	    error: function(err) {
 	    	setTimeout(function() {
 	    		closeLoader();
 	    		alertify.success(err.result);
 	    	}, 500);
  	    	console.log(err);
 	    	alertify.success(err.result);
  	    }
  	})
  }

function deleteForeignLanguage(foreignLanguage) {
 	alertify.confirm("Confirm", BkEunivTitleDeleteForeignLanguage + " ID = " + foreignLanguage.foreignLanguageId,
 	function(){
 		openLoader();
 
 		$.ajax({
 		    url: "/bkeuniv/control/delete-foreign-language",
 		    type: 'post',
 		    data: foreignLanguage,
 		    datatype:"json",
 		    success: function(data) {
 		    	table.rows().indexes().data().filter(function(e, index) {
 		    		if(e.foreignLanguageId == foreignLanguage.foreignLanguageId) {
 		    			e.index = index;
 		    			return true;
 		    		}
 		    	}).map(function(el, index){
 		    		table.row(el.index).remove().draw();
 		    	})
 
 		    	setTimeout(function() {
 		    		closeLoader();
 		    		$("#change-foreign-language #modal-template").modal('hide');
 					alertify.success(data.result);
  		    	}, 500);
  		    },
  		    error: function(err) {
 		    	setTimeout(function() {
 		    		closeLoader();
 		    		alertify.success(err.result);
 		    	}, 500);
  		    	console.log(err);
 		    	alertify.success(err.result);
 		    	
  		    }
  		})
  	},
 	function(){
 	});
 }
 



function addForeignLanguage(){
	openLoader();
	var newForeignLanguage = {
		"staffId": $("#staffid").val().trim(),
		"listen": $("#listen").val().trim(),
		"speaking": $("#speaking").val().trim(),
		"reading": $("#reading").val().trim(),
		"writing": $("#writing").val().trim()		
	}

	$.ajax({
	    url: "/bkeuniv/control/create-foreign-language",
	    type: 'post',
	    data: newForeignLanguage,
	    datatype:"json",
	    success: function(data) {
	    	table.row.add(data.foreignLanguage).draw();
	    	setTimeout(closeLoader(), 500);
	    	$("#staffid").val("");
	    	$("#listen").val("");
			$("#speaking").val("");
			$("#reading").val("");
			$("#writing").val("");
			
			alertify.success('Created new row');
	    },
	    error: function(err) {
	    	setTimeout(function() {
	    		closeLoader();
	    		alertify.success(err);
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
