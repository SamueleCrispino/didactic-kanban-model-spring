


$(document).ready(function() {


	console.log( "ready!" );

	$.get("/KanbanModel/userInfo", function(data, status) {
		$("#ciao").text("Ciao " + data['username']);
		console.log(data);
		if (data['admin']) {
			console.log("MasterAndCommander!");
			const adminButton = document.createElement("button");
			adminButton.innerHTML = 'SEZIONE ADMIN';
			$('#admin').append(adminButton);
			adminButton.onclick = spia;
		}

		const colonneArchiviate = document.createElement("button");
		colonneArchiviate.innerHTML = 'Vai a colonne archiviate';
		$('#admin').append(colonneArchiviate);
		colonneArchiviate.onclick = vaiAdArchiviate;

		var columnConteiner = $("#columnContainer");
		var columnList = data['colonneSet'];

		data['colonneSet'].sort(function(a, b) {
			return	a['idColonna'] - b['idColonna'];
		}).forEach(function(item, index){
			console.log(item);
			// BOTTONE
			const deleteButton = document.createElement("button");
			deleteButton.onclick = deleteColumn;
			const text_button = document.createTextNode("Elimina Colonna");
			deleteButton.setAttribute('class', "deleteButton");
			deleteButton.setAttribute('colonna', item['titolo']);
			deleteButton.appendChild(text_button);


			// titolo, testo, radioOrg, radioInfo, file, 
			// input titolo
			const inputTitle = document.createElement('input');
			inputTitle.setAttribute('id', "inputTitle");
			inputTitle.setAttribute('type', "text");
			inputTitle.setAttribute('name', "titolo");
			inputTitle.setAttribute('required', "true");
			const labelInputTitle = document.createElement('label');
			labelInputTitle.setAttribute("for", "inputTitle");			
			labelInputTitle.innerHTML = "Titolo tile"


				// input contenuto testuale
				const inputTesto = document.createElement('input');
			inputTesto.setAttribute('id', "inputTesto");
			inputTesto.setAttribute('name', "testo");
			const labelInputTesto = document.createElement('label');
			labelInputTesto.setAttribute("for", "inputTesto");
			labelInputTesto.setAttribute("id", "labelInputTesto");
			labelInputTesto.innerHTML = "Inserici testo"

				// radio button tipo messaggio  {ORGANIZZATIVO, INFORMATIVO}
				const radioOrg = document.createElement('input');
			radioOrg.setAttribute('id', "radioOrg");
			radioOrg.onclick = radioInfoOff;
			radioOrg.setAttribute('name', "radioOrg");
			radioOrg.setAttribute('type', "radio");
			radioOrg.setAttribute('value', "organizzativo");
			const labelRadioOrg = document.createElement('label');
			labelRadioOrg.setAttribute("for", "radioOrg");
			labelRadioOrg.innerHTML = "organizzativo";

			const radioInfo = document.createElement('input');
			radioInfo.setAttribute('id', "radioInfo");
			radioInfo.setAttribute('name', "radioInfo");
			radioInfo.setAttribute('type', "radio");
			radioInfo.onclick = radioOrgOff;
			radioInfo.setAttribute('value', "informativo");
			radioInfo.checked = true;
			const labelRadioInfo = document.createElement('label');
			labelRadioInfo.setAttribute("for", "radioInfo");
			labelRadioInfo.innerHTML = "informativo";

			// INPUT MULTIMEDIALE
			const inputMultimediale = document.createElement('input');
			inputMultimediale.setAttribute('id', "inputMultimediale");
			inputMultimediale.setAttribute('type', "file");
			inputMultimediale.setAttribute('class', "fileInput");
			inputMultimediale.setAttribute('name', "file");
			inputMultimediale.setAttribute('accept', "image/*");
			inputMultimediale.style.display = "none";



			// CONTENUTO TESTUALE / MULTIMEDIALE
			const testuale = document.createElement('input');
			testuale.setAttribute('id', "testuale");
			testuale.onclick = multimedialeOff;
			testuale.setAttribute('type', "radio");
			testuale.setAttribute('value', "testuale");
			testuale.checked = true;
			const labelTestuale = document.createElement('label');
			labelTestuale.setAttribute("for", "testuale");
			labelTestuale.innerHTML = "testuale";

			const multimediale = document.createElement('input');
			multimediale.setAttribute('id', "multimediale");
			multimediale.setAttribute('name', "multimediale");
			multimediale.setAttribute('type', "radio");
			multimediale.onclick = testualeOff;
			multimediale.setAttribute('value', "multimediale");
			const labelMultimediale = document.createElement('label');
			labelMultimediale.setAttribute("for", "multimediale");
			labelMultimediale.innerHTML = "multimediale";



			var divConfTile = document.createElement("form");
			divConfTile.setAttribute("method", "POST");
			divConfTile.setAttribute("action", "/KanbanModel/createTile");
			// method="POST" 

			//enctype="multipart/form-data"
			divConfTile.setAttribute("enctype", "multipart/form-data");

			//<input type="submit" value="Submit" />



			divConfTile.append(labelInputTitle);
			divConfTile.append(inputTitle);	
			divConfTile.append(labelInputTesto);
			divConfTile.append(inputTesto);	


			// INPUT RADIO BUTTON
			var tipoMessaggio = document.createElement('select');
			tipoMessaggio.setAttribute('name', 'tipoMessaggio');
			tipoMessaggio.setAttribute('class', "custom-select");				
			var org = document.createElement('option');
			org.setAttribute('value', "organizzativo");
			org.innerHTML = "organizzativo";			
			var inf = document.createElement('option');
			inf.setAttribute('value', "informativo");
			inf.innerHTML = "informativo";

			const labelTipoMessaggio = document.createElement('label');
			labelTipoMessaggio.setAttribute("for", "tipoMessaggio");
			labelTipoMessaggio.innerHTML = "tipo messaggio"

				tipoMessaggio.append(org);
			tipoMessaggio.append(inf);

			divConfTile.append(labelTipoMessaggio);	

			divConfTile.append(tipoMessaggio);	



//			divConfTile.append(labelRadioOrg);	
//			divConfTile.append(radioOrg);	
//			divConfTile.append(labelRadioInfo);	
//			divConfTile.append(radioInfo);

			divConfTile.append(labelTestuale);
			divConfTile.append(testuale);
			divConfTile.append(labelMultimediale);
			divConfTile.append(multimediale);

			// INPUT COLONNA
			var columnInput = document.createElement('input');
			columnInput.style.display = "none";
			columnInput.setAttribute('value', item['idColonna']);
			columnInput.setAttribute('name', 'colonna');
			divConfTile.append(columnInput);


			divConfTile.append(columnInput);


			divConfTile.append(inputMultimediale);	



			var submit = document.createElement('input');
			submit.setAttribute('type', "submit");
			submit.setAttribute('value', "Crea Tile");
			divConfTile.append(submit);




			// TITOLO
			const titolo = document.createElement("h2");
			const text_titolo = document.createTextNode(item['titolo']);

			titolo.appendChild(text_titolo);


			// COLONNA
			const columnObj = document.createElement("div");			  
			columnObj.setAttribute('class', "column");
			columnObj.setAttribute('id', item['idColonna']);
			columnObj.setAttribute('name', item['titolo']);
			columnObj.appendChild(deleteButton);
			if (data['admin']) {				
				const cambiaStato = document.createElement("button");
				cambiaStato.onclick = cambiaStatoColonna;
				cambiaStato.id =  item['idColonna'];
				cambiaStato.name = item['archiviato'];
				cambiaStato.innerHTML = "cambia stato colonna";
				columnObj.appendChild(cambiaStato);

			}
			columnObj.appendChild(titolo);
			
		
			
			
			const changeTitolo = document.createElement("button");
			changeTitolo.name = item['idColonna'];
			changeTitolo.innerHTML = "modifica titolo colonna";
			changeTitolo.onclick = uploadTitoloColonna;
			
			const nuovoTitoloInput = document.createElement("input");	
			nuovoTitoloInput.id = "nuovoTitoloInput";
			nuovoTitoloInput.type = "text";
			
			const nuovoTitoloInputLabel = document.createElement("label");
			nuovoTitoloInputLabel.setAttribute("for", "nuovoTitoloInput");
			nuovoTitoloInputLabel.innerHTML = "nuovo nome colonna";
			
			
			
			columnObj.appendChild(nuovoTitoloInputLabel);
			columnObj.appendChild(nuovoTitoloInput);
			columnObj.appendChild(changeTitolo);
			
			
			columnObj.append(divConfTile);	

			const tileContainer = document.createElement("div");
			tileContainer.setAttribute('class', "contenitore");

			item['tileSet'].sort(function(a, b) {
				return a['idTile'] -  b['idTile'];
			} ).forEach(function(item_tile, index) {
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


				// OPTION CAMBIA COLONNA
				var changeColumnForm = document.createElement('form');
				changeColumnForm.setAttribute("method", "POST");
				changeColumnForm.setAttribute("action", "/KanbanModel/updateTile/" + item_tile['idTile']);

				var changeColumnLabel = document.createElement('label');
				changeColumnLabel.setAttribute("for", "changeColumnSelect");
				changeColumnLabel.innerHTML = "sposta in";
				var changeColumnSelect = document.createElement('select');
				changeColumnSelect.setAttribute('name', 'colonnaId');
				changeColumnSelect.setAttribute("id", "changeColumnSelect");

				columnList.forEach(function(column, index) {
					var changeColumnOption = document.createElement('option');
					changeColumnOption.setAttribute("value", column['idColonna'])
					changeColumnOption.innerHTML = column['titolo'];
					changeColumnSelect.append(changeColumnOption);
				});

				changeColumnForm.append(changeColumnLabel);
				changeColumnForm.append(changeColumnSelect);

				var changeTileButton = document.createElement('input');
				changeTileButton.setAttribute('type', "submit");
				changeTileButton.setAttribute('value', "cambia colonna");
				changeColumnForm.append(changeTileButton);


				const deleteTile = document.createElement("button")
				deleteTile.innerHTML = 'delete';
				deleteTile.onclick = cancellaTile;
				tile.appendChild(deleteTile);
				tile.appendChild(changeColumnForm);  
				tile.appendChild(tileTitolo);

				// const //form??
				const updateTitleTitle = document.createElement("form");
				//const startButtom
				changeColumnForm.setAttribute("method", "POST");

				changeColumnForm.setAttribute("action", "/KanbanModel/updateTileTitle/" + item_tile['idTile']);




				if ((item_tile['contenutoTestuale'] == "" || item_tile['contenutoTestuale'] == null) 
						&& !((item_tile['contenutoMultimediale'] == null) || (item_tile['contenutoMultimediale'] == "") )) {
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

function vaiAdArchiviate(click) {
	console.log("archiviate");
	location.href = "/KanbanModel/goToArchiviate";
}

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

function spia(click) {
	//click.preventDefault();

	location.href = "admin/adminPage";
}

function multimedialeOff(click) {

	this.parentNode.querySelectorAll("#multimediale").item(0).checked = false;

	this.parentNode.querySelectorAll("#inputTesto").item(0).style.display = "block";
	this.parentNode.querySelectorAll("#labelInputTesto").item(0).style.display = "block";
	this.parentNode.querySelectorAll("#inputMultimediale").item(0).style.display = "none";
	this.parentNode.querySelectorAll("#inputMultimediale").item(0).val = "";

//	$("#inputTesto").show();
//	$("#labelInputTesto").show();
//	$("#inputMultimediale").hide();
//	$("#labelInputMultimediale").hide();
}

function testualeOff(click) {


	this.parentNode.querySelectorAll("#testuale").item(0).checked = false;

	this.parentNode.querySelectorAll("#inputTesto").item(0).style.display = "none";
	this.parentNode.querySelectorAll("#inputTesto").item(0).val = "";
	this.parentNode.querySelectorAll("#labelInputTesto").item(0).style.display = "none";
	this.parentNode.querySelectorAll("#inputMultimediale").item(0).style.display = "block";



//	$("#inputTesto").hide();
//	$("#labelInputTesto").hide();
//	$("#inputMultimediale").show();
//	$("#labelInputMultimediale").show();

}

function radioInfoOff(click) {
	this.parentNode.querySelectorAll("#radioInfo").item(0).checked = false;
}

function radioOrgOff(click) {
	this.parentNode.querySelectorAll("#radioOrg").item(0).checked = false;
}


function cancellaTile(click) {

	var idTile = this.parentNode.id;

	console.log("tile id = " + idTile + " will be deleted");



	$.ajax({
		type: 'POST',
		url: '/KanbanModel/deleteTile/' + idTile,
		data: idTile,
		cache: false,
		success: function() {
			//response = JSON.stringify(data);
			console.log("il tile verrà eliminato");


		},
		error: function(xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.Message);
			console.log("eliminazione colonna non riuscita");

		}
	});
	location.reload();

}


function deleteColumn(click) {

	console.log(this.parentNode.id + "will be deleted");

	var nomeColonna = this.parentNode.id;

	$.ajax({
		type: 'POST',
		url: '/KanbanModel/deleteColumn/' + nomeColonna,
		data: nomeColonna,
		cache: false,
		success: function() {
			//response = JSON.stringify(data);
			console.log("la colonna verrà eliminata");


		},
		error: function(xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.Message);
			console.log("eliminazione colonna non riuscita");

		}
	});
	location.reload();

}





$("#addColonnaButton").click(function(e) {   


	var nomeColonna = $('#nomeColonnaInput').val();

	var colonne = document.getElementsByClassName("column");

	var duplicato = false;

	for (item of colonne) {
		if (nomeColonna == item.getAttribute('name')) {
			duplicato = true;	
		}		
	}


	if (duplicato) {
		$('#avviso').text();
		window.alert("colonna " + nomeColonna + ' già presente!');
	} else {

		$.ajax({
			type: 'POST',
			url: '/KanbanModel/createColumn/' + $('#nomeColonnaInput').val(),
			data: nomeColonna,
			cache: false,
			success: function() {
				//response = JSON.stringify(data);
				console.log("dati colonna inviati");


			},
			error: function(xhr, status, error) {
				var err = eval("(" + xhr.responseText + ")");
				console.log(err.Message);
				console.log("creazione colonna non riuscita");

			}
		});
	}
});

function uploadTitoloColonna(click) {	
	
	idColonna = this.name;
	console.log(idColonna);
	
	nuovoNome = this.parentNode.querySelectorAll('#nuovoTitoloInput').item(0).value;
	console.log(nuovoNome);
	
	if (nuovoNome != "" && nuovoNome != null) {
		$.ajax({
			type: 'POST',
			url: '/KanbanModel/uploadTitoloColonna/' + idColonna + '/' + nuovoNome,
			cache: false,
			success: function(msg) {
				//response = JSON.stringify(data);
				console.log("changeNomeCOlonna");
				window.alert(msg);


			},
			error: function(xhr, status, error) {
				var err = eval("(" + xhr.responseText + ")");
				console.log(err.Message);
				console.log("creazione colonna non riuscita");

			}
		});
		
	}
	
	location.reload();
}
