package org.skyscreamer.yoga.configuration;

import java.util.Collection;
import java.util.List;

/**
 * A simple implementation of YogaEntityConfiguration.  Allows easy creation of Spring XML configurations of existing
 * entities.
 *
 * This is an example of a configuration for a hypothetical Person entity.  The configuration states that id and name
 * are core fields (returned by default), and address and favoriteColor can be selected:
 *
 * <bean class="org.skyscreamer.yoga.configuration.SimpleYogaEntityConfiguration">
 *     <property name="entityClass" value="example.Person" />
 *     <property name="coreFields">
 *         <list>
 *             <value>id</value>
 *             <value>name</value>
 *         </list>
 *     </property>
 *     <property name="selectableFields>
 *         <list>
 *             <value>address</value>
 *             <value>favoriteColor</value>
 *         </list>
 *     </property>
 * </bean>
 *
 * This example identifies the core fields, but all getters within the Person entity can be selected.  (Potentially a
 * security risk).
 *
 * <bean class="org.skyscreamer.yoga.configuration.SimpleYogaEntityConfiguration">
 *     <property name="entityClass" value="example.Person" />
 *     <property name="coreFields">
 *         <list>
 *             <value>id</value>
 *             <value>name</value>
 *         </list>
 *     </property>
 * </bean>
 *
 * This example relies on the entity to specify the core fields (or not) via the @Core annotation.  It further controls
 * which getters can be selected as a security or safety measure.
 *
 * <bean class="org.skyscreamer.yoga.configuration.SimpleYogaEntityConfiguration">
 *     <property name="entityClass" value="example.Person" />
 *     <property name="selectableFields>
 *         <list>
 *             <value>address</value>
 *             <value>favoriteColor</value>
 *         </list>
 *     </property>
 * </bean>
 *
 */
public class SimpleYogaEntityConfiguration<T> extends YogaEntityConfiguration<T> {
    private Class<T> _entityClass;
    private List<String> _coreFields = null;
    private List<String> _selectableFields = null;
    private String _uriTemplate = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<T> getEntityClass() {
        return _entityClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getCoreFields() {
        return _coreFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getSelectableFields() {
        return _selectableFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getURITemplate() {
        return _uriTemplate;
    }

    /**
     * Setter for entity class supported by this configuration.
     *
     * @param entityClass A class object representing the entity
     */
    public void setEntityClass(Class<T> entityClass) {
        _entityClass = entityClass;
    }

    /**
     * Setter for list of core fields for the entity being configured.
     *
     * @param coreFields A list of strings identifying the name of each core field
     */
    public void setCoreFields(List<String> coreFields) {
        _coreFields = coreFields;
    }

    /**
     * Setter for list of selectable fields for the entity being configured.
     *
     * @param selectableFields A list of strings identifying the name of each selectable field
     */
    public void setSelectableFields(List<String> selectableFields) {
        _selectableFields = selectableFields;
    }

    /**
     * Setter for URI template for the entity being configured
     *
     * @param uriTemplate
     */
    public void setUriTemplate(String uriTemplate) {
        _uriTemplate = uriTemplate;
    }
}
