var patientUniqueId = 'VincentRivoire'; // Hard coded because there is no login...
var patient;

$(document).ready(function () {

    $("#sel_professional").selectmenu();
    $("#sel_availabilities").selectmenu();
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

    fillTable();

    $('#submit').click(function (event) {
        event.preventDefault();

        var query = {
            availabilityId: parseInt($("#sel_availabilities :selected").val(), 10),
            professionalId: parseInt($('#sel_professional :selected').val(), 10),
            patientId: patient.id};

        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: '/bookAppointment',
            data: query,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            complete: function (xhr) {
                if (xhr.status === 200) {
                    $('#feedback').html("<h4>Le rendez-vous à été crée avec succès!</h4>");
                } else {
                    $('#feedback').html("<h4>Réponse</h4><pre>Une erreur est survenue: " + xhr.responseText + "</pre>");
                }
                $('#feedback').fadeOut(10000);
                $('#add_form').slideUp();
                $('#add-button').slideDown();
                fillTable();
            }
        });
    });

    $('#cancel-button').click(function () {
        $('#add-button').slideDown();
        $('#add_form').slideUp();
    });

    $('#add-button').click(function () {
        $('#add-button').slideUp();
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
            },
            error: function (xhr, textStatus, errorThrown) {
                $('#feedback').html("<h4>Response</h4><pre>An error occured.</pre>");
                $('#feedback').fadeOut(10000);
                console.log("ERROR : ", errorThrown);
            }
        });
        $('#add_form').slideDown();
    });

    $("#sel_professional").click(function () {
        getAvailabilities();
    });

    function fillTable() {
        $.get("/getPatient",
                {patientUniqueId: patientUniqueId},
                function (result, status, xhr) {
                    if (status === 'success') {
                        patient = result;
                        $('#patientName').html('Nom du patient: ' + patient.displayName);
                        $('tbody').empty();
                        for (var i = 0; i < patient.appointments.length; i++) {
                            var appointment = patient.appointments[i];
                            var displayName = 'Non trouvé';
                            $.ajax({
                                url: '/getProfessionalForAppointment',
                                type: 'get',
                                dataType: 'json',
                                data: {appointementId: appointment.id},
                                async: false,
                                success: function (professional) {
                                    if (professional !== null || professional !== undefined) {
                                        displayName = professional.displayName;
                                    }
                                    $('<tbody>').append(
                                            $('<tr>'),
                                            $('<td>').text(displayName),
                                            $('<td>').text(formatDate(new Date(appointment.startTime))),
                                            $('<td>').text(formatDate(new Date(appointment.endTime))),
                                            $('</tr>')
                                            ).appendTo('#table');
                                },
                                error: function (xhr, textStatus, errorThrown) {
                                    $('#feedback').html("<h4>Response</h4><pre>An error occured.</pre>");
                                    $('#feedback').fadeOut(10000);
                                    console.log("ERROR : ", errorThrown);
                                }
                            });
                        }
                    } else {
                        $('#feedback').html("<h4>Response</h4><pre>An error occured.</pre>");
                        $('#feedback').fadeOut(10000);
                        console.log("ERROR : ", xhr.responseText);
                    }
                });
    }

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
                    $("#sel_availabilities").append("<option value='" + availability.id + "'> De " + formatDate(availability.startTime) + ' à ' + formatDate(availability.endTime) + "</option>");
                }
            },
            error: function (xhr, textStatus, errorThrown) {
                $('#feedback').html("<h4>Response</h4><pre>An error occured.</pre>");
                $('#feedback').fadeOut(10000);
                console.log("ERROR : ", errorThrown);
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

}
);
