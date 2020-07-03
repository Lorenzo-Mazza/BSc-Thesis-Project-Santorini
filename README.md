# Prova finale di Ingegneria del Software 2020
![Santorini : game cover](https://cf.geekdo-images.com/opengraph/img/aL3ylg4WfWekpXaOq9fij-eRgHg=/fit-in/1200x630/pic3283110.png)


##### “You should always let people underestimate you. Because when people misappraise your intelligence and abilities, they’re merely pointing out their own vulnerabilities—the gaping holes in their judgment that need to stay open if you want to cartwheel through later on a flaming horse, correcting the record with your sword of justice.”
― Edward Snowden, Permanent Record

## Gruppo PSP50
App Developers: 

- ###   10615502    Lorenzo Mazza ([@Lorenzo-Mazza](https://github.com/Lorenzo-Mazza))<br>lorenzo2.mazza@mail.polimi.it
- ###   10612183    Albi Miraka ([@DiNAZRED](https://github.com/DiNAZRED))<br>albi.miraka@mail.polimi.it
(Luca Garofalo abandoned the project on May the 19th after committing 169 total code lines)

The [Deliveries](/Deliveries) folder contains the Test coverage report, UML diagrams, and Javadoc.
The [src](/src) folder contains source code, resources and unit tests.

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI 3D | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| (Advanced)Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

### Requirements
Java 9, or newer versions.
### Build instructions

The JARs are built using the [Maven Assembly Plugin]
To build the executables jar files run the Maven command:
```
mvn clean compile assembly:single
```
### Run instructions
To launch the Application Server run from Terminal:
```
java -jar JARNAME.jar server
```
To launch the Application as a Client run from Terminal:
```
java -jar JARNAME.jar 
```
### JAR
The JAR is too heavy to load on GitHub (128 MB) because of the 3D GUI, so it is not provided inside the repository. 

