Crespo
======
File sharing client
-------------------
Contributors:
- Valeri Nistor   - 342C1
- Adrian Nicolau  - 342C1

Dependencies
- Ant
- Swing
- JUnit
- Log4j
- Apache Tomcat
- Axis 1.4

Build project
-------------
> `ant -f build.xml`

Cuprins
-------
1. GUI
2. Network
3. Web Service + Web Service Client

GUI
---
Obiectivul principal a fost realizarea unei interfețe grafice pentru un
client de partajare de fișiere. Aceasta este implementată folosind Swing GUI
widget toolkit.
Aplicația conține 4 componente principale:
- Lista de utilizatori
- Lista de fișiere a utilizatorului selectat
- Tabela ce conține informații despre transferuri
- Status bar

Arhitectura se bazează pe implementarea Mediator Pattern care definește un
obiect ce încapsulează un set de obiecte și descrie interacțiunea dintre
ele. Componentele principale sunt: clientul GUI, nivelul rețea și serviciul web
Pentru realizarea acțiunilor utilizatorului am implementat Command Pattern, unde
fiecare componentă gui își urmează propria logică de execuție.
Pentru simularea nivelului rețea și a serviciului web am folosit fire de
execuție separate care simulează apariția diferitelor evenimente, precum: un
utilizator intră sau iese, sosește lista de fișiere a unui utilizator, se adaugă
un nou transfer. Toate aceste evenimente se publică către clientul gui.

Network
-------
Pentru implementarea acestui nivel am separat logica în trei părți:
- Sender - logica de trimitere a unui fișier
- Receiver - logica de primire a unui fișier
- Network - logica de management a conexiunilor

În clasa Network se pornește serverul care așteaptă conexiuni într-o buclă
infinită și ia decizii în funcție de tipul cheilor selectate. Serverul deține
un pool de executori și pornește câte un Sender sau Receiver (ambele sunt obiecte
de tip Thread) pentru a răspunde tuturor cererilor.
Fișierele sunt trimise (și respectiv primite) în chunk-uri folosind obiecte 
ByteBuffer. Prin mediator se realizează actualizarea interfeței grafice în funcție
de procentul de upload/download al fișierelor. Actualizările se fac pe threaduri
separate prin apeluri `SwingUtilities.invokeLater`.

Web Service
-----------
####Server
Serverul expune două metode:
- registerUser: funcție apelată de client la pornirea programului pentru a se înregistra la baza de date
- retrieveUsers: funcție apelată periodic de clienți (prin polling) pentru a primi ultima versiune a bazei de date și astfel a determina dacă există clienți care au apărut/dispărut

Serverul ține de asemenea și un dicționar cu momentul ultimului polling al fiecărui client. Every once in a while, serverul compară ora curentă cu aceste intrări, iar dacă diferența este mai mare de o valoare prestabilită, consideră că acel utilizator a ieșit din program.

####Client
Clientul este un `SwingWorker` care în metoda `doInBackground` face poll. Prin perechea `publish` -> `process` se actualizează interfața grafică (doar dacă este nevoie) cu noile date primite de la server.
