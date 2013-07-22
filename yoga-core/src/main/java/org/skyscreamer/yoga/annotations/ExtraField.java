package org.skyscreamer.yoga.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>ExtraField is used to identify a method in an entity configuration that extends the returned fields for an entity.</p>
 *
 * <p>The field name is specified by the annotation's value.  If a parameter is passed to the method it should be an
 * object of the class supported by the configuration.</p>
 *
 * <p>Example:</p>
 *
 * <pre>
 *      public class UserConfiguration extends YogaEntityConfiguration {
 *          &#064;Resource private MyService myService;
 *
 *          public Class getEntityClass() { return User.class; }
 *
 *          <b>&#064;ExtraField("recommendedAlbums")</b>
 *          public List<Album> getRecommendedAlbums(User user) {
 *              myService.getRecommendedAlbums(user);
 *          }
 *      }
 * </pre>
 *
 * @see org.skyscreamer.yoga.configuration.YogaEntityConfiguration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ExtraField
{
    String value();
}
