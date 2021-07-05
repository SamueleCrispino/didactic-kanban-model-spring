$(document).ready(function() {

	console.log( "ready!" );

	$.get("/KanbanModel/userInfoArchiviate", function(data, status) {
		$("#ciao").text("Ciao " + data['username']);
		console.log(data);
		
		
		var columnConteiner = $("#columnContainer");
		var columnList = data['colonneSet'];
		
		data['colonneSet'].forEach(function(item, index){
			console.log(item);


			// TITOLO
			const titolo = document.createElement("h2");
			const text_titolo = document.createTextNode(item['titolo']);
			titolo.appendChild(text_titolo);


			// COLONNA
			const columnObj = document.createElement("div");			  
			columnObj.setAttribute('class', "column");
			columnObj.setAttribute('id', item['idColonna']);
			columnObj.setAttribute('name', item['titolo']);

			if (data['admin']) {				
				const cambiaStato = document.createElement("button");
				cambiaStato.onclick = cambiaStatoColonna;
				cambiaStato.id =  item['idColonna'];
				cambiaStato.name = item['archiviato'];
				cambiaStato.innerHTML = "cambia stato colonna";
				columnObj.appendChild(cambiaStato);
					
			}
			columnObj.appendChild(titolo);

			const tileContainer = document.createElement("div");
			tileContainer.setAttribute('class', "contenitore");

			item['tileSet'].forEach(function(item_tile, index) {
				const tile = document.createElement("div");
				tile.setAttribute('id', item_tile['idTile']);
				tile.setAttribute('class', "contenitore");
				const tileTitolo = document.createElement("h2");
				
				if (item_tile['tipoMessaggio'] == "organizzativo") {
					tile.style.borderColor = "yellow";
				} else {
					tile.style.borderColor = "blue";
				}

				tileTitolo.innerHTML = item_tile['titolo']
				

				if (item_tile['contenutoTestuale'] == "" || item_tile['contenutoTestuale'] == null) {
					const img = document.createElement("img");
					img.setAttribute("src", "data:image/png;base64," + item_tile['contenutoMultimediale']);
					// width="500" height="600"
					img.setAttribute("width", "200");
					img.setAttribute("height", "100");					
					tile.appendChild(img);
					
				} else {
					const tileTesto = document.createElement("h3");
					tileTesto.innerHTML = item_tile['contenutoTestuale'];
					tile.appendChild(tileTesto);
				}



				tileContainer.appendChild(tile);
			});

			columnObj.append(tileContainer);	

			columnConteiner.append(columnObj);
		});



	}).fail(function(xhr, status, error) {
		var err = eval("(" + xhr.responseText + ")");
		console.log(err.Message);
	});
});



function cambiaStatoColonna(click) {
	console.log("stato colonna will be cambiato");

	var idColonna = this.id;
	var archiviato = this.name;

	$.ajax({
		type: 'POST',
		url: '/KanbanModel/admin/cambiaStatoColonna/' + idColonna + '/' + archiviato,
		cache: false,
		success: function() {
			//response = JSON.stringify(data);
			console.log("stato verrà cambiato");

		},
		error: function(xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.Message);
			console.log("cambio non riuscito");

		}
	});
	location.reload();

}