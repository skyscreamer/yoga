# Yoga #

Yoga is a Java library that enables relational queries on existing REST implementations to accelerate query performance and development time.

## Wiki ##
https://github.com/skyscreamer/yoga/wiki

## Inspiration ##
LinkedIn’s JavaOne presentation on building flexible REST interfaces (http://blog.linkedin.com/2009/07/08/brandon-duncan-java-one-building-consistent-restful-apis-in-a-high-performance-environment/)

## Can You Explain in English? ##
Imagine there's a RESTful API to a simple music-based social network site, and you want to discover some songs by artists that your friends like.  A traditional RESTful approach might take several steps:

1. Get my friends
2. Get their favorite artists
3. Get those artists' albums
4. Get the songs from the albums

This can end up being a LOT of queries.  Yoga lets you do the same with one query, for example:

    GET /user/1.json?selector=:(friends:(favoriteArtists:(albums:(songs))))

( Find a more detailed explanation of our selectors here: https://github.com/skyscreamer/yoga/wiki/REST-Selectors )

In short:

    Advantage               Gets you
    ----------              --------
    Fewer requests          Less total latency, ie faster overall response
    Fewer sockets           Better hardware capacity
    Simpler client code     Happier client developers

## What Frameworks Are Supported ##
 + RESTEasy
 + Any RESTful Spring MVC application

## How Do I Use It? ##
Yoga is a "plumbing" package.  To hook it up, you just add a handful of annotations and a few lines of configuration.  Check out the documentation on our wiki for a more detailed explanation.

If you find the Yoga approach as useful as we do, we provide a lot of handy patterns to simplify adding your own extensions to its capabilities.

Once Yoga has been set up, clients can build and modify their own relational queries without any server-side programming.  Yay!

Yoga is created by:

 * Carter Page (carter@skyscreamer.org)
 * Corby Page (corby@skyscreamer.org)
 * Solomon Duskis (solomon@skyscreamer.org)
 * ... and contributors like you!
