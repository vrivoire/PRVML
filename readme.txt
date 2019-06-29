In the project directory (PRVML-master):

The database graph picture is in the file Database "Grapg.png"
The database structure SQL is in the file "prvml.db.sql"

Build the project:
mvn cleam install

Run the project:
java -jar target/PRVML-1.0.jar

Go to http://localhost:8080
H2 console URL: http://127.0.0.1:8080/h2-console  JDBC URL: jdbc:h2:mem:prvml or jdbc:h2:./database/prvml.db ((user: sa)



Vous êtes en charge de développer l’API REST qui sert à supporter une application
simplifiée de prise de rendez-vous médical en ligne. Après analyse, vous arrivez à la
conclusion que vous aurez à implémenter les fonctionnalités suivantes :
- Le patient peut effectuer une recherche des différents professionnels de la clinique
- Le patient peut consulter les disponibilités d’un professionnel donné dans un
intervalle de temps défini (par exemple, les disponibilités du Dr. A entre le 8 et 12 mai
2019)
- Le patient peut prendre rendez-vous en choisissant l’une des disponibilités d’un
professionnel
Implémenter l’API REST simplifiée qui supporte ces fonctionnalités.
Quelques précisions :
- Clinique

o L’application ne supporte qu’une seule clinique pour l’instant
o Une clinique ne comporte qu’un seul attribut soit son nom qui agit
également à titre d’identifiant unique

- Patient
o
- Professionnel

o Une clinique peut avoir un ou plusieurs professionnels à son actif
o Un professionnel est défini par les attributs suivants :
§ Prénom
§ Nom
o La combinaison du prénom et du nom agit à titre d’identifiant unique

- Disponibilité

o Une disponibilité est une plage horaire pendant laquelle un
professionnel peut recevoir un patient, si ce dernier a pris rendez-vous
o Chaque professionnel possède une ou plusieurs disponibilités par jour
o Une disponibilité comporte les attributs suivants :
§ Date et heure (timestamp) de début
§ Date et heure (timestamp) de fin
o Afin de simplifier le problème, on suppose que les disponibilités sont
organisées par tranche de 15 minutes (8:00, 8:15, 8:30, etc.)

- Rendez-vous

o Une disponibilité qui a été choisie par un patient est convertie en
rendez-vous (une disponibilité = un rendez-vous)
o Un rendez-vous comporte les attributs suivants :
§ Un patient
§ Un professionnel
§ Une date de début
§ Une date de fin
o Comme pour les disponibilités, on supposera que les rendez-vous sont
organisés par tranche de 15 minutes