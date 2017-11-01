package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class OrganizationSection extends Section {
    private final Map<Link,List<Organization>> organizations;

    public OrganizationSection(Map<Link,List<Organization>> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public Map<Link,List<Organization>> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);

    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}