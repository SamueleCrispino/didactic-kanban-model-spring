
$(document).ready(function() {
	console.log("caricamento dati utenti");

	$("#colonneDiv").hide();

	$.get("/KanbanModel/admin/observeUser", function(data, status) {
		console.log(data);
		var table = $("#listaLogin");
		data.forEach(function(loginDto, index) {

			// RIGA
			const riga = document.createElement("tr");

			// username
			const username = document.createElement("th");
			username.innerHTML = loginDto['username'];
			riga.append(username);

			//TIMESTAMP CREAZIONE
			const creazione = document.createElement("th");
			creazione.innerHTML = loginDto['timestampCreazione'];
			console.log(typeof loginDto['timestampCreazione']);
			riga.append(creazione);

			// TIMESTAMP LOGIN
			const login = document.createElement("th");
			login.innerHTML = loginDto['timestampLogin'];
			riga.append(login);


			// TIMESTAMP LOGOUT
			const logout = document.createElement("th");
			logout.innerHTML = loginDto['timestampLogout'];
			riga.append(logout);

			//SPIA UTENTE
//			const guarda = document.createElement("button");
//			guarda.innerHTML = "guarda";
//			guarda.setAttribute('id', loginDto['idLogin']);
//			guarda.setAttribute('username', loginDto['username']);
//			guarda.onclick = guarda;
//			guarda.append(elimina);

			// ELIMINA
			const eliminaTh = document.createElement("th");
			const elimina = document.createElement("button");
			eliminaTh.append(elimina);
			elimina.innerHTML = "elimina";
			elimina.setAttribute('id', loginDto['idLogin']);
			elimina.setAttribute('username', loginDto['username']);
			elimina.onclick = eliminaUtente;			
			riga.append(eliminaTh);


			// GUARDA TABELLE
			const vediTh = document.createElement("th");

			const vedi = document.createElement("button");
			vediTh.append(vedi);
			vedi.innerHTML = "Guarda le tabelle di questo utente";
			vedi.setAttribute('id', loginDto['idLogin']);
			vedi.setAttribute('username', loginDto['username']);
			vedi.onclick = vediTabelle;			
			riga.append(vediTh);


			table.append(riga);

		});

	}).fail(function(xhr, status, error) {
		var err = eval("(" + xhr.responseText + ")");
		console.log(err.Message);
	});
});

function guarda(click) {

}

function eliminaUtente(click) {
	click.preventDefault();

	idUser = this.id;
	username = this.getAttribute('username');

	console.log("will be deleted user " + username);



	$.ajax({
		type: 'POST',
		url: '/KanbanModel/admin/eliminaUtente/' + idUser + '/' + username,
		data: idUser,
		cache: false,
		success: function(data) {
			//response = JSON.stringify(data);
			console.log("eliminato !!");

			if (data == "adminEliminato") {
				console.log("adminEliminato !!");
				location.href = "/KanbanModel/login";
			} else {
				console.log(data);
				location.reload();
			}

		},
		error: function(xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.Message);
			console.log("eliminazione utente non riuscita");

		}
	});
	//
};

function vediTabelle(click) {

	var idUser = this.id;
	var username = this.getAttribute('username');
	console.log(username)

	var inCorso = $("#columnContainerInCorso");
	var archiviate = $("#columnContainerArchiviate");
	var colonneDiv = $("#colonneDiv");
	
	const chiudiTabelle = document.createElement("button");
	$("#backButton").append(chiudiTabelle);
	chiudiTabelle.innerHTML = "Chiudi Tabelle";
	chiudiTabelle.onclick = function(click) {
		location.reload();
	}			

	$.ajax({
		type: 'POST',
		url: '/KanbanModel/admin/getColonne/' + username,
		cache: false,
		success: function(userInfo) {
			console.log(userInfo)

			userInfo['colonneSet'].sort(function(a, b) {
				return	a['idColonna'] - b['idColonna'];
			}).forEach(function(item, index){

				// TITOLO
				const titolo = document.createElement("h2");
				const text_titolo = document.createTextNode(item['titolo']);
				titolo.appendChild(text_titolo);


				// COLONNA
				const columnObj = document.createElement("div");			  
				columnObj.setAttribute('class', "column");
				columnObj.setAttribute('id', item['idColonna']);
				columnObj.setAttribute('name', item['titolo']);


				const cambiaStato = document.createElement("button");
				// dovrebbe prenderla dal js
				cambiaStato.onclick = cambiaStatoColonna;
				cambiaStato.id =  item['idColonna'];
				cambiaStato.name = item['archiviato'];
				cambiaStato.innerHTML = "cambia stato colonna";
				columnObj.appendChild(cambiaStato);


				columnObj.appendChild(titolo);

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

				//aggiungo tile a colonna
				columnObj.append(tileContainer);

				console.log(item['archiviato']);
				// aggiungo colonna al corretto divisore
				if (item['archiviato']) {
					archiviate.append(columnObj);
				} else {
					inCorso.append(columnObj);
				}

//				colonneDiv.append(inCorso);
//				colonneDiv.append(archiviate);
				
				

			});
			
			$("h1").append(" di " + userInfo['username']);
			colonneDiv.show()
			$("#listaLogin").hide();


		},
		error: function(xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.Message);
			console.log("eliminazione utente non riuscita");

		}
	});
	//
};



