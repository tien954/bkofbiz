var table, test, temp;
var modalAdd;
var modalChange;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-research-domain-management",
	    type: 'post',
	    dataType: "json",
	    success: function(data) {
	    	var researchDomain = data.researchDomain;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	    	table = $('#table-research-domain').DataTable({
	   		 data: researchDomain,
	           columns: [
	               { "data": "researchDomainId" },
	               { "data": "researchDomainName" }
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
	    	
			$(document).contextmenu({
			    delegate: "#table-research-domain td",
			menu: [
			  {title: edit, cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: remove, cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var researchDomain = table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						changeResearchDomain(researchDomain)
						break;
					case "delete":
						deleteResearchDomain(researchDomain);
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

function changeResearchDomain(researchDomain) {
	new Promise(function(resolve, reject) {
		
		resolve(new modal("#change-research-domain").setting({
			data: researchDomain,
			columns: [
			          {
			        	  name: researchDomainId,
			        	  value: "researchDomainId"
			          },
			          {
			        	  name: researchDomainName,
			        	  value: "researchDomainName"
			          }
			          ],
			          title: titleEditResearchDomain,
			          action: {
			        	  func: "saveResearchDomain()",
			        	  name: update
			          }
		}).render());
	}).then(function(modal) {
		modalChange = modal;
		$("#change-research-domain #modal-template").modal('show');
	})
	
}

function newResearchDomain() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-research-domain").setting({
			data: {},
			columns: [
			          {
			        	  name: researchDomainId,
			        	  value: "researchDomainId"
			          },
			          {
			        	  name: researchDomainName,
			        	  value: "researchDomainName"
			          }
			          ],
			title: titleNewResearchDomain,
			action: {
				name: add,
				url: "/bkeuniv/control/create-research-domain-management",
				dataTable: table,
				keys:["researchDomainId"],
				fieldDataResult: "researchDomain",
				hidden: "auto"
			}
		}).render());
	}).then(function(modal) {
		modalAdd = modal
		$("#add-research-domain #modal-template").modal('show');
	})
}

function saveResearchDomain() {
	openLoader();
	var researchDomain = modalChange.data();

	$.ajax({
	    url: "/bkeuniv/control/update-research-domain-management",
	    type: 'post',
	    data: researchDomain,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.result) {
	    		table.rows().indexes().data().filter(function(e, index) {
	    			if(e.researchDomainId == researchDomain.researchDomainId) {
	    				e.index = index;
	    				return true;
	    			}
	    		}).map(function(el, index){
	    			el.researchDomainId = researchDomain.researchDomainId;
	    			el.researchDomainName = researchDomain.researchDomainName;	    			
	    			table.row(el.index).data(el);
	    		})
	    		
	    		setTimeout(function() {
	    			closeLoader();
	    			$("#change-research-domain #modal-template").modal('hide');
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

function deleteResearchDomain(researchDomain) {
	alertify.confirm("Confirm", BkEunivTitleDeleteResearchDomain + " ID = " + researchDomain.researchDomainId,
	function(){
		openLoader();

		$.ajax({
		    url: "/bkeuniv/control/delete-research-domain-management",
		    type: 'post',
		    data: researchDomain,
		    datatype:"json",
		    success: function(data) {
		    	if(!!data.result) {
		    		table.rows().indexes().data().filter(function(e, index) {
		    			if(e.researchDomainId == researchDomain.researchDomainId) {
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

function addResearchDomain(){
	openLoader();
	var newResearchDomain = modalAdd.data();

	$.ajax({
	    url: "/bkeuniv/control/create-research-domain-management",
	    type: 'post',
	    data: newResearchDomain,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.researchDomain) {
	    		table.row.add(data.researchDomain).draw();
		    	setTimeout(closeLoader(), 500);
		    	
		    	$("#add-research-domain #researchDomainId").val("");
				$("#add-research-domain #researchDomainName").val("");				
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
