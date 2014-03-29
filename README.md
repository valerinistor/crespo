crespo
======

File sharing client

##Dependencies
>Ant,Swing

##Useful commands

#### build project
>`ant -f build.xml`

--------------------------------------------------------------------------------
Cuprins:
        1. Informatie Generala.
        2. Reguli Compilare/Rulare.
        3. Descriere Implementare.
--------------------------------------------------------------------------------
                            1. Informatie Generala
--------------------------------------------------------------------------------
                                Tema 1 - IDP
Nume:
    Valeri Nistor  - 342 C1
    Adrian Nicolau - 342 C1

Continut Arhiva:
    1. src/main/java/ro/pub/cs/elf/crespo/app:
        Crespo.java  ICommand.java
    2. src/main/java/ro/pub/cs/elf/crespo/dto:
        File.java  TransferData.java  User.java
    3. src/main/java/ro/pub/cs/elf/crespo/gui:
        AbstractList.java  CommandListener.java  Draw.java
        FileList.java ProgressCellRender.java  TransferTable.java
        TransferTableModel.java UserList.java
    4. src/main/java/ro/pub/cs/elf/crespo/mediator:
        Mediator.java
    5. src/main/java/ro/pub/cs/elf/crespo/test:
        NetworkWorker.java  WebServiceWorker.java
    6. src/main/resources:
        user.properties
    7. build.xml
    8. README.md
--------------------------------------------------------------------------------
                            2. Reguli Compilare/Rulare
--------------------------------------------------------------------------------
Compilare si rulare: ant -f build.xm
--------------------------------------------------------------------------------
                            3. Descriere Implementare
--------------------------------------------------------------------------------
    Obiectivul principal a fost realizarea unei interfete grafice pentru un
client de partajare de fisiere. Aceasta este implementata folosind Swing GUI
widget toolkit.
Aplicatia contine 4 componente principale:
    1. Lista de utilizatori
    2. Lista de fisiere a utilizatorului selectat.
    3. Tabela ce contine informatii despre transferuri
    4. Status bar.
Arhitectura se baseaza pe implementarea Mediator Pattern care defineste un
obiect care incapsuleaza un set de obiecte si cum acestea interactioneaza intre
ele. Componentele principale sunt: clientul GUI, serviciul web si nivelul retea.
Pentru realizarea actiunilor utilizatorului am implementat Command Pattern, unde
fiecare componenta gui, urmeaza propria logica de executie.
Pentru simularea nivelului de retea si a serviciului web am folosit fire de
executie separate, care simuleaza aparitia diferitelor evenimente, precum: un
utilizator intra sau iese, soseste lista de fisiere a unui utilizator, se adauga
un nou transfer. Toate aceste evenimente se publica catre clientul de gui.
--------------------------------------------------------------------------------

