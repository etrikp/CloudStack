(function(cloudStack, testData) {  
  cloudStack.sections.accounts = {
    title: 'Accounts',
    id: 'accounts',
    listView: {
      fields: {
        name: { label: 'Name', editable: true },
        domain: { label: 'Domain' },
        state: { label: 'State' }
      },
      filters: {
        mine: { label: 'My Accounts' },
        all: { label: 'All Accounts' }
      },
      
	  //dataProvider: testData.dataProvider.listView('accounts')
	  dataProvider: function(args) {        
		$.ajax({
		  url: createURL("listAccounts&page="+args.page+"&pagesize="+pageSize),
		  dataType: "json",
		  async: true,
		  success: function(json) { 	
			var items = json.listaccountsresponse.account;			    
			args.response.success({data:items});			               			
		  }
		});  	
	  }
	  
    }
  };  
})(cloudStack, testData);