const search = () => {
	let query = $("#search-input").val();
	
	
	if(query == ""){
		$(".search-result").hide();
	}
	else{
		// send request to server
		
		let url = `http://localhost:8002/search/${query}`;
		fetch(url).then((response) =>{
			return response.json();
		}).then((data) =>{
			
			let text = `<div class='list-group'>`;
			data.forEach((contact) =>{
				text+= `<a href='/user/${contact.cid}/contact' class='list-group-item list-group-item-action'> ${contact.cname} </a>`;
				
			});
			text+=`</div>`;
			$(".search-result").html(text);
			$(".search-result").show();
		});
		
	}
	
};