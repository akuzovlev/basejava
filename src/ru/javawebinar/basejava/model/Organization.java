package ru.javawebinar.basejava.model;


import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link link;
    private final List<OnePeriod> organization;

    public Organization(Link link, List<OnePeriod> organization) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(organization, "organization must not be null");
        this.link = link;
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        return organization != null ? organization.equals(that.organization) : that.organization == null;
    }

    @Override
    public int hashCode() {
        int result = link != null ? link.hashCode() : 0;
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        return result;
    }
}
