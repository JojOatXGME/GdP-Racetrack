Gruppe: 8VektorAce
==================

Funktionen und Ausbaustuffe:
----------------------------
### Zufällig generierte Karten ###
Es existiert ein Mapgenerator (MapGenerator) der zufällig generierte Karten mit
unterschiedlichem (frei wählbarem) Schwierigkeitsgrad generiert.
Dieser ist Lauffähig.

### Regeln ###
Es existiert ein "dynamisches" Regelsystem, mit welchem die gewünschten Regeln geladen werden
können. Allerdings existiert im Wesentlichen von den einzelnen Teilregeln nur eine
Implementation:
* TurnRule_8, TurnRule_J
* VictoryRule_First
* EnvironmentCollisionRule_Bounce
* PlayerCollision_PhaseThrough
Diese Implementation scheinen soweit lauffähig konnten aber noch nicht
ausreichend getestet werden.

### GUI ###
Die GUI welche durch die main-Methode standartmäßig gestartet wird ist zwar Lauffähig
aber nicht fertig gestellt. Man kann einen Schwierigkeitsgrad auswählen und sieht darauf
eine Karte aber das Spiel ist noch nicht weiter bedienbar.

### KI ###
Es existiert eine KI (GurKI) welche allerdings noch nicht im Zusammenhang mit dem restlichen
Spielelementen Lauffähig ist. Es ist zwar Möglich ein Bot zu erstellen aber dieser macht
(oft) keine gültigen Züge wobei noch nicht sicher ist, ob das an der KI oder der restlichen
Implementation liegt.

Starten:
--------
Einfach die Klasse "Main" ausführen und dann mit GUI bedienen.

Erweiterte Startoptionen:
-------------------------
* test  
  Startet einen einfachen Test.
* mapgen  
  Generiert mit hilfe des Mapgenerators mehrere Maps und speichert diese als png in aktuellen Ordner.
* listai  
  Listet alle möglichen KI's auf.
* listrules  
  Listet alle möglichen Regeln auf.
