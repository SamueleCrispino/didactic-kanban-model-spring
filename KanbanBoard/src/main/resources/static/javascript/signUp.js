

$('#registrami').click(function(event) {   
	event.preventDefault();
	var username = $('#usernameSignUp').val();
	var email = $('#email').val();
	var password = $('#passwordSignUp').val();

	const emailPattern = /^(\w|\d)[\w,.,-]+@[\w,-]+\.[\w,\d]+$/

		//Gli indirizzi email possono contenere esclusivamente le cifre (0-9), le lettere ASCII (a-z), 
		//il punto (.) ed il trattino (-). 
		// Il punto ed il trattino non possono trovarsi all'inizio o all fine dell'indirizzo email.
		if(email.match(emailPattern)) {
			var userSignUp = {
					"username": username,
					"email": email,
					"password":  password,
			}

			// TODO: sistemare url
			var url = "http://localhost:8080/KanbanModel/saveUser";
			var payload = JSON.stringify(userSignUp);
			console.log("ok");
			$.ajax({
				type: 'POST',
				contentType: "application/json",
				url: url,
				data: payload,
				cache: false,
				success: function(data) {
					//response = JSON.stringify(data);
					$('#avviso').text(data);	
					$('#usernameSignUp').val('');
					$('#email').val('');
					$('#passwordSignUp').val('');

				},
				error: function() {
						$('#avviso').text("Opss qualcosa è andato storto");

				}
			});


		} else {
			window.alert("INDIRIZZO EMAIL NON VALIDO");
		}


});



$('.form').find('input, textarea').on('keyup blur focus', function (e) {

	var $this = $(this),
	label = $this.prev('label');

	if (e.type === 'keyup') {
		if ($this.val() === '') {
			label.removeClass('active highlight');
		} else {
			label.addClass('active highlight');
		}
	} else if (e.type === 'blur') {
		if( $this.val() === '' ) {
			label.removeClass('active highlight');
		} else {
			label.removeClass('highlight');
		}
	} else if (e.type === 'focus') {

		if( $this.val() === '' ) {
			label.removeClass('highlight');
		}
		else if( $this.val() !== '' ) {
			label.addClass('highlight');
		}
	}

});

$('.tab a').on('click', function (e) {

	e.preventDefault();
	
	$('#avviso').text("");
	

	$(this).parent().addClass('active');
	$(this).parent().siblings().removeClass('active');

	target = $(this).attr('href');

	$('.tab-content > div').not(target).hide();

	$(target).fadeIn(600);

});