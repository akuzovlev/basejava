package ru.javawebinar.basejava.model;


class OnePlaceExperience {

    private String company;
    private String hyperlink;
    private String datesFromTo;
    private String position;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OnePlaceExperience)) return false;

        OnePlaceExperience that = (OnePlaceExperience) o;

        if (!company.equals(that.company)) return false;
        if (!datesFromTo.equals(that.datesFromTo)) return false;
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        int result = company.hashCode();
        result = 31 * result + datesFromTo.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public String getDatesFromTo() {
        return datesFromTo;
    }

    public void setDatesFromTo(String datesFromTo) {
        this.datesFromTo = datesFromTo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
