var patientUniqueId = 'VincentRivoire'; // Hard coded because there is no login...
var patient;

$(document).ready(function () {
    $('input:text').button().addClass('my-textfield');
    $("#pages").spinner();
    $("#publication").spinner();
    $("#files").selectmenu();
    $('#table').basictable();
    $('#table-breakpoint').basictable({
        breakpoint: 768
    });
    $('#table-container-breakpoint').basictable({
        containerBreakpoint: 485
    });
    $('#table-swap-axis').basictable({
        swapAxis: true
    });
    $('#table-force-off').basictable({
        forceResponsive: false
    });
    $('#table-no-resize').basictable({
        noResize: true
    });
    $('#table-two-axis').basictable();
    $('#table-max-height').basictable({
        tableWrapper: true
    });

    $.post('/addAppointment',
            {availabilityId: $("#sel_availabilities").val(),
                professionalId: $('#sel_professional :selected').val(),
                patientId: patient},
            function (result, status, xhr) {
                if (status === 'success') {
                    $('#feedback').html("<h4>Le rendez-vous à été crée avec succès!</h4>");
                } else {
                    $('#feedback').html("<h4>Response</h4><pre>An error occured.</pre>");
                    console.log("ERROR : ", xhr.responseText);
                }
            });

    $.get("/getPatient",
            {patientUniqueId: patientUniqueId},
            function (patient, status, xhr) {
                if (status === 'success') {
                    this.patient = patient;
                    $('#patientName').html(patient.displayName);
                    for (var i = 0; i < patient.appointments.length; i++) {
                        var appointment = patient.appointments[i];
                        $('<tr>').append(
                                $('<td>').text(formatDate(new Date(appointment.startTime))),
                                $('<td>').text(formatDate(new Date(appointment.endTime))),
                                ).appendTo('#table');
                    }
                } else {
                    $('#feedback').html("<h4>Response</h4><pre>An error occured.</pre>");
                    console.log("ERROR : ", xhr.responseText);
                }
            });

    $('#cancel-button').click(function () {
        $('#add_form').toggle();
    });

    $('#add-button').click(function () {
        $.ajax({
            url: '/getProfessionals',
            type: 'get',
            dataType: 'json',
            success: function (response) {
                var len = response.length;
                $("#sel_professional").empty();
                for (var i = 0; i < len; i++) {
                    var id = response[i]['id'];
                    var firstName = response[i]['firstName'];
                    var lastName = response[i]['lastName'];
                    $("#sel_professional").append("<option value='" + id + "'>" + firstName + ' ' + lastName + "</option>");
                }
                getAvailabilities();
            }
        });
        $('#add_form').toggle();
    });

    $("#sel_professional").click(function () {
        getAvailabilities();
    });

    function getAvailabilities() {
        var professionalId = $('#sel_professional :selected').val();
        $.ajax({
            url: 'getAvailabilities',
            type: 'get',
            data: {professionalId: professionalId},
            dataType: 'json',
            success: function (availabilities) {
                $("#sel_availabilities").empty();
                for (var i = 0; i < availabilities.length; i++) {
                    var availability = availabilities[i];
                    console.log(availability);
                    $("#sel_availabilities").append("<option value='" + availability.id + "'> De " + formatDate(availability.startTime) + ' à ' + formatDate(availability.endTime) + "</option>");
                }
            }
        });
    }

    function formatDate(date) {
        var newDate = new Date(date);
        return  newDate.getFullYear() + "-" + appendLeadingZeroes(newDate.getMonth() + 1) + "-" + appendLeadingZeroes(newDate.getDate()) + " " + appendLeadingZeroes(newDate.getHours()) + ":" + appendLeadingZeroes(newDate.getMinutes());
    }

    function appendLeadingZeroes(n) {
        if (n <= 9) {
            return "0" + n;
        }
        return n;
    }

});
