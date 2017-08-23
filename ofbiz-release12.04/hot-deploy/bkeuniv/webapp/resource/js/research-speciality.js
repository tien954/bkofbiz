var table, test, temp;
var modalAdd;
var modalChange;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-research-speciality-management",
	    type: 'post',
	    dataType: "json",
	    success: function(data) {
	    	var researchSpeciality = data.researchSpeciality;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	    	table = $('#table-research-speciality').DataTable({
	   		 data: researchSpeciality,
	           columns: [
	               { "data": "researchSpecialityId" },
	               { "data": "researchSpecialityName" }
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
	    	
			$(document).contextmenu({
			    delegate: "#table-research-speciality td",
			menu: [
			  {title: edit, cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: remove, cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var researchSpeciality = table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						changeResearchSpeciality(researchSpeciality)
						break;
					case "delete":
						deleteResearchSpeciality(researchSpeciality);
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

function changeResearchSpeciality(researchSpeciality) {
	new Promise(function(resolve, reject) {
		
		resolve(new modal("#change-research-speciality").setting({
			data: researchSpeciality,
			columns: [
			          {
			        	  name: researchSpecialityId,
			        	  value: "researchSpecialityId"
			          },
			          {
			        	  name: researchSpecialityName,
			        	  value: "researchSpecialityName"
			          }
			          ],
			          title: titleEditResearchSpeciality,
			          action: {
			        	  func: "saveResearchSpeciality()",
			        	  name: update
			          }
		}).render());
	}).then(function(modal) {
		modalChange = modal;
		$("#change-research-Speciality #modal-template").modal('show');
	})
	
}

function newResearchSpeciality() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-research-Speciality").setting({
			data: {},
			columns: [
			          {
			        	  name: researchSpecialityId,
			        	  value: "researchSpecialityId"
			          },
			          {
			        	  name: researchSpecialityName,
			        	  value: "researchSpecialityName"
			          }
			          ],
			title: titleNewResearchSpeciality,
			action: {
				name: add,
				url: "/bkeuniv/control/create-research-Speciality-management",
				dataTable: table,
				keys:["researchSpecialityId"],
				fieldDataResult: "researchSpeciality",
				hidden: "auto"
			}
		}).render());
	}).then(function(modal) {
		modalAdd = modal
		$("#add-research-speciality #modal-template").modal('show');
	})
}

function saveResearchSpeciality() {
	openLoader();
	var researchSpeciality = modalChange.data();

	$.ajax({
	    url: "/bkeuniv/control/update-research-speciality-management",
	    type: 'post',
	    data: researchSpeciality,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.result) {
	    		table.rows().indexes().data().filter(function(e, index) {
	    			if(e.researchSpecialityId == researchSpeciality.researchSpecialityId) {
	    				e.index = index;
	    				return true;
	    			}
	    		}).map(function(el, index){
	    			el.researchSpecialityId = researchSpeciality.researchSpecialityId;
	    			el.researchSpecialityName = researchSpeciality.researchSpecialityName;	    			
	    			table.row(el.index).data(el);
	    		})
	    		
	    		setTimeout(function() {
	    			closeLoader();
	    			$("#change-research-Speciality #modal-template").modal('hide');
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

function deleteResearchSpeciality(researchSpeciality) {
	alertify.confirm("Confirm", BkEunivTitleDeleteResearchSpeciality + " ID = " + researchSpeciality.researchSpecialityId,
	function(){
		openLoader();

		$.ajax({
		    url: "/bkeuniv/control/delete-research-speciality-management",
		    type: 'post',
		    data: researchSpeciality,
		    datatype:"json",
		    success: function(data) {
		    	if(!!data.result) {
		    		table.rows().indexes().data().filter(function(e, index) {
		    			if(e.researchDomainId == researchSpeciality.researchSpecialityId) {
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

function addResearchSpeciality(){
	openLoader();
	var newResearchDomain = modalAdd.data();

	$.ajax({
	    url: "/bkeuniv/control/create-research-speciality-management",
	    type: 'post',
	    data: newResearchSpeciality,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.researchSpeciality) {
	    		table.row.add(data.researchSpeciality).draw();
		    	setTimeout(closeLoader(), 500);
		    	
		    	$("#add-research-speciality #researchSpecialityId").val("");
				$("#add-research-speciality #researchSpecialityName").val("");				
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