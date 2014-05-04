# Yoga #

Yoga extends JAX-RS and SpringMVC RESTful servers to provide GData and LinkedIn style field selectors.
 
* Choose which fields you want to see at call-time
* Navigate entity relationships in a single call for complex views
* **Much** faster speeds in high-latency (e.g. mobile) apps
* Streamline client development
* Browsable APIs

## Wiki ##
Most of the documentation is here: https://github.com/skyscreamer/yoga/wiki

## Quickstart ##

    % git clone git@github.com:skyscreamer/yoga.git
    % cd yoga
    % mvn install
    % cd yoga-demos/yoga-demo-jersey
    % mvn jetty:run

## Inspiration ##
Yoga was initially inspired by LinkedIn’s JavaOne presentation on building flexible REST interfaces (http://blog.linkedin.com/2009/07/08/brandon-duncan-java-one-building-consistent-restful-apis-in-a-high-performance-environment/), and it continues to be influenced by GData selectors, and ongoing discussions of HATEOAS.

## How does this work? ##
For an example, we'll use a hypothetical music-oriented social network site.  If I'm a user of that site, one of the first things I'd like to do is see my friends' favorite bands.  A standard RESTful navigation approach involves two steps:

1. Get my friends
2. Get their favorite artists

If I only have two dozen friends on this site, I'm already making 25 queries using pure REST.

With Yoga, you can do it in one call:

    GET /user/1.json?selector=friends(favoriteArtists)

In a more complicated example, let's say I want to compile a play list of songs from my friend's favorite artists.  This could involve thousands of queries in strict RESTful navigation, but it can still be aggregated as a single query:

    GET /user/1.json?selector=friends(favoriteArtists(albums(songs)))

The other option is to program custom controller methods, but the point here is to avoid that, since there's a cost to that and specific client requirements frequently change.

Find a more detailed explanation of our selectors here: https://github.com/skyscreamer/yoga/wiki/REST-Selectors

## How Do I Use It? ##
Yoga works with existing frameworks.  To hook it up, you need to add some annotations and a few lines of configuration.  Check out the documentation on our wiki for a more detailed explanation.

For advanced users, we provide hooks to further extend Yoga's capabilities.

Once Yoga has been set up, clients can build and modify their own relational queries without any server-side programming.  

## POM? ##
Right here:

    <dependency>
        <groupId>org.skyscreamer</groupId>
        <artifactId>yoga-core</artifactId>
        <version>1.0.5</version>
    </dependency>
    
And pick one of the integration packages:

    <dependency>
        <groupId>org.skyscreamer</groupId>
        <artifactId>yoga-springmvc</artifactId>
        <version>1.0.5</version>
    </dependency>

or

    <dependency>
        <groupId>org.skyscreamer</groupId>
        <artifactId>yoga-jaxrs</artifactId>
        <version>1.0.5</version>
    </dependency>

If this version was recently released, it may take a couple of days for it to propagate to all repositories.
