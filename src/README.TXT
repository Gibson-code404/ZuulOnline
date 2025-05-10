Zuul over Telnet – Textbasiertes Adventure-Spiel im Netzwerk
=============================================================

Dieses Projekt ist eine erweiterte Version des klassischen "World of Zuul" Text-Adventures,
die so angepasst wurde, dass das Spiel über eine Netzwerkverbindung per Telnet spielbar ist.

Projektstruktur
---------------
Die wichtigsten Klassen:

- Game.java            → Hauptklasse des Spiels, angepasst für Input/Output über Socket-Streams
- Parser.java          → Liest Eingaben über Socket-InputStream
- Command.java         → Repräsentiert Benutzerbefehle
- CommandWords.java    → Enthält alle gültigen Befehle
- Room.java            → Repräsentiert Räume im Spiel
- Items.java           → Repräsentiert Items, die aufgesammelt werden können
- Server.java          → Startet den Socket-Server und leitet Verbindungen an eine Game-Instanz weiter

Spielidee
---------
Der Spieler wacht an der Uni auf und hat alle seine Gegenstände verloren.
Ziel ist es, alle Items wiederzufinden, indem man sich durch verschiedene Räume bewegt und diese durchsucht.

Spielstart & Test
-----------------
1. Projekt kompilieren:
   > javac *.java

2. Server starten:
   > java Server
   Ausgabe: "Server läuft auf Port 8000"

3. In neuer Konsole per Telnet verbinden:
   > telnet localhost 8000

4. Spiel spielen mit Befehlen wie:
   > go east
   > look
   > check
   > pick Bottle
   > items
   > quit

Bekannte Befehle:
-----------------
- go <richtung>        → Gehe in eine Richtung (east, west, north, south)
- look                 → Beschreibung des aktuellen Raums
- check                → Zeigt Items im Raum
- pick <item>          → Hebt ein Item auf
- items                → Zeigt Inventar
- help                 → Zeigt alle verfügbaren Befehle
- quit                 → Beendet das Spiel

Hinweise
--------
- Das Spiel funktioniert auch mit mehreren parallelen Telnet-Verbindungen.
- Jeder Client sp
