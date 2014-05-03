Changelog
=========

Version 1.0.5 - 2014-05-03
--------------------------
 - Added the ability to use * in selectors
 - Added Map support to ResultTraverser
 - Fixed null handling in iterables
 - Handle Date primitive

Version 1.0.4 - 2013-10-07
--------------------------
 - Updating maven compiler plugin to 3.1 for build performance.
 - Adding some additional configuration options for YogaBuilder based on user requests.  
   You can now override CoreSelector, SelectorResolver and MetaDataRegistry.

Version 1.0.2 - 2013-08-25
--------------------------
 - Various refactors including improvements to Jersey demo projects
 - Add null check to MetaDataRegistry

Version 1.0.1 - 2013-07-28
--------------------------
 - Removed Spring dependencies from JAX-RS and Jersey integration libraries
 - Moved demo resources into shared library
 - Added some sensible configuration defaults

Version 1.0.0 - 2013-06-22
--------------------------
 - Our official 1.0 release!
 - Added a Jersey demo
 - Updated documentation
 - Improve usability of selector builder
 - Fix XML output issue

Version 1.0.0 RC2 - 2013-03-16
------------------------------
 - Added SelectorResolver so that it can be used in the controller
 - Performed some cleanup
 - Hopefully last release before GA

Version 1.0.0 RC1 - 2012-12-07
------------------------------
 - Our first release candidate for 1.0!
 - Add the ability to limit yoga's handling to only those Controller handler methods with @ResponseBody
 - Add a lot more comments
 - Clean up demos

Version 0.9.4 - 2012-10-29
--------------------------
 - Cleaned up the mucky state of the 0.9.3 version in Maven (some deps were missing)
 - Added some additional comments
 - Refactor field populator annotation into an abstract entity configuration class

Version 0.9.3 - 2012-09-19 (deprecated)
---------------------------------------
 - Getting awfully close to a 1.0 state
 - Fixed selector builder race condition that was causing weird UI behavior
 - More documentation including 
 - Reorganized a bunch of the code into more appropriate modules
 - Added yoga-hibernate integration project
 - Made a new view (.yoga) that brings up the selector builder
 - Changed default selector style from LinkedIn to GData
 - Visual clean up of web site

Version 0.9.2 - 2012-08-22
--------------------------
 - Corby's birthday edition!
 - Updated web design
 - Move to a listener-style architecture for consistency and better extensibility
 - Provided a self-documented configuration template: yogaConfiguration.xml
 - Provided better defaults to simplify out-of-the-box configurations

Version 0.9.1 - 2012-06-29
--------------------------
 - Support for JAX-RS
 - GData-style selectors (in addition to LinkedIn-style)
 - DoS protection with a configurable throttle that errors out with oversized responses
 - Added ability to disable explicit selector as additional protection for production deployments
 - Architecture improved to use event observer patterns for core behavior.  Simplifies and improves extensibility.
 - Replaced invididual parameters being passed through the stack with ResultTraverserContext
 - Updated design of demo page and selector builder interface
 - Improved test infrastructure and coverage
 - Added @CoreFields and @SupportedFields annotations

Version 0.9.0 - 2012-03-18
--------------------------
 - Core functionality complete for 1.0 release!  Now we'll add documentation and some polish.
 - Refactored JSONassert into a separate project (https://github.com/skyscreamer/JSONassert)
 - Integrated URLRewriter and added some "magic" to allow for REST-y URL's for aliased selectors
 - Allow disabling of explicit selectors with properties
 - Added hooks to load large datasets for testing

Version 0.4.0 - 2012-02-13
--------------------------

 - Created single copy of static demo files (removed c&p anti-pattern)
 - Created JSONAssert to streamline unit tests
 - Implemented support for aliased selectors
 - Added URLRewriter

Version 0.3.0 - 2011-12-14
--------------------------

### New features

  - Added developer-friendly graphical selector builder interface with .yoga extension
  - Added navigation links to make the API more discoverable
  - Added entity metadata views
  - Created plugin architecture for extended ResultTraverser functionality

Version 0.2.1 - 2011-09-01
--------------------------

### New features

  - Added @ExtraField functionality

Version 0.2.0 - 2011-08-23
--------------------------

### New features

  - Made objectToURI functionality available in yoga-core
  - Renamed variables/methods to clarify distinctions between populator and mapper
  - Migrated HierarchyModels and dependencies to core package
  - Demo created
  - XHTML view created
  - Implemented Populator and ResultTraverser registry
  - Simplified configuration of field populators

### Bugfix

  - Fixed suffix on HREF's
  - Fixed tests

### Code quality

  - Stable with 100% high-level functional test coverage

Version 0.1.0 - 2011-06-28
--------------------------

### Licensing

  Yoga is licensed under the Apache License v2.0

### New features

 - Basic implementation of core features
