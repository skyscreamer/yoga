Changelog
=========

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
