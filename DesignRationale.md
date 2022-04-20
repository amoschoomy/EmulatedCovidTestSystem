# Design Rationale

## SOLID Principles

### Single Responsibility Principle

Most of the classes are only responsible for their functionalities within the system. This allows
for loose coupling and changes to each class without affecting other classes in tandem. For example,
changes to a `Receptionist` class will not affect the functionalities of `Patient` or `Customer`
class

### Dependency Inversion Principle

The lower level classes depends on the higher level classes by inverting their dependencies. By
inverting their dependencies, the abstract logic of our system no longer depends on the details of
our system, but rather the other way round. This allows for more flexibility to refactor our codes
or add new features to the lower level modules without changing the codes in it's dependencies as
one will if the dependency is not inverted. This is because now both the lower level and higher
level modules depends on the same abstraction. Also, with now the higher level modules no longer
depends on the details, it makes for easier reuse of the higher level modules.

An example of this will be:

![alt text](markdown_images/dip1.png)\
*Figure 1. Abstract User Class*

![alt text](markdown_images/dip2.png)\
*Figure 2. Lower Level module depending on the abstract class*

## Package Level Principles

### The Release Reuse Equivalency Principle (RREP)

An `Exception` package is released as it is meant to be reused in our system.

TODO: Image for package

### The Common Closure Principle (CCP)

We have 4 separate packages in our system that adheres to ths principle, `UserPackage`
, `TestingFacilityPackage`,`LoginSystemPackage`and `BookingPackage`.

Any changes inside any of the classes inside these packages will also affect all of the other
classes inside the packages but no other packages shall be affected.

The reason this principle is adhered to is because as programmers, maintainability is the key to
good code practices and we want to ensure that changes or refactoring that occurs in those packages
are closed only to that packages being modified. This would allow simplification in our tasks if we
have to change the classes inside these packages without worrying about other packages. Had we not
adhered to this principle, each small change would need to be revalidated in every other packgages
to avoid any breaking changes.

For example, changes in one of the class in `User` package will only affect other classes in `User`
package.

However, it is not always possible to maintain a full closure, as some of these packages are also
used in other packages therefore may require changes in them.

A potential con of this principle is clients using our packages may find it hard to reuse our
packages as they may find our packages not to be useful but we are confident that we maintained a
balance between the size of our packages.

## Package Coupling Principles

### The Acyclic Dependencies Principle (ADP)

We have maintained our packages in a way such that there are no cycles between our packages. This is
achieved through dependency inversion principle mainly as we made the lower level classes inside the
package depend on the higher level class, while the classes inside the other packages will also
depend on the higher level class inside the earlier package therefore avoiding a cycle between these
two packages.

An example is from above, where we applied dependency inversion principle on `UserPackage`
and `LoginSystemPackage`, thereby also avoding cyclic dependencies between the packages.

The reason we want to avoid cycles between our packages is because we want to avoid tight coupling
between these packages which we want to avoid. Also, if any minor changes in these packages will
cause potential errors and bugs, if the programmers do not inspect all the remaining cyclic
dependencies between these packages. It may also cause trouble if there is a domino effect between
the packages where one change inside a package leads to another and so on which waste a lot of the
programmers time.

## Software Design Patterns

### Factory Method

![alt text](markdown_images/factory1.png)\
*Figure 3. Example of Factory method pattern applied*

We applied Factory method in our system, specifically the functionality to create a User, when a
user logined to our system. The system is able to have multiple type of user login to our system
such as customers, receptionist and healthcare workers. A factory method provides an interface to
create a `User` object in our system while also allowing subclasses of `User` such as `Receptionist`
and `Customer`. This allows us to pass respective user roles object into our client directly without
our client worrying on what User subclasses object they should create.

This is because we do not know what type of `User` someone is going to login to our system
beforehand therefore making the Factory method design pattern useful to our system. With this, we
also avoid a tight coupling between the creator (`UserFactory`) class and the concrete classes. One
disadvantage of this pattern is that we have to introduce multiple subclasses which introudces more
complexity to our backend system.

### Singleton

We also make use of the Singleton design pattern in our system, `LoginAuthentication` class
and `TestingFacilityCollection`

![alt text](markdown_images/singleton1.png)\
*Figure 4. Singleton pattern applied to LoginAuthentication*

![alt text](markdown_images/singleton2.png)\
*Figure 5. Singleton pattern applied to TestingFacilityCollection*

For `LoginAuthentication`, it is wise to use a singleton pattern as the application will only have
one instance of `User` at all time. A singleton pattern is used over a global variable because a
global variable does not prevent multiple instances for an object to be created.

The same reasoning is applied to 'TestingFacilityCollection` class, at any point in time, there will
be only one instance of a TestingFacilityCollection object. Since our application is single
threaded, we have no issues with using the Singleton pattern design.

A trade off of using Singleton pattern design is it violates the `Single Responsiblity Principle`.

### Facade

![alt text](markdown_images/facade1.png)\
*Figure 6. Facade pattern applied to Booking Subsystem*

We applied a Facade design pattern in our system, specifcally in our `Booking`subsystem. With a
facade design pattern, we have provided a simple interface for the clients to use without worrying
about the internal implementation of the subsystem. This provides an abstraction for the client
interface to our complex `Booking`subsytem. Our complex subsystem involves several methods of
Booking involving `HomeBooking` and `OnSiteBooking` and there are 3rd party dependencies needed to
implement the `HomeBooking` subsystem QR code. With these facade pattern, the interface to make a
booking is largely simplified for the client.

We also considered Strategy design pattern instead of Facade but decided against it because we have
covered all exhaustive cases of possible booking scenarios which are not a lot in the first place
and we did not feel a need to overcomplicate our system with too many classes and interface. Also,
with a Facade design pattern, they do not need to know what strategy to apply as opposed to a Facade
design pattern.
