package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class OrganizationSection extends Section {

    private static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
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

    @Override
    public List<String> getDataAsStringList() {
        List<String> result = new ArrayList<>();
        result.add(Integer.toString(organizations.size()));
        for (Organization org : organizations) {
            result.add(org.getHomePage().getName());
            result.add(org.getHomePage().getUrl());
            result.add(Integer.toString(org.getPositions().size()));
            for (Organization.Position position : org.getPositions()) {
                result.add(position.getStartDate().toString());
                result.add(position.getEndDate().toString());
                result.add(position.getTitle());
                result.add(position.getDescription());
            }
        }
        return result;
    }
}