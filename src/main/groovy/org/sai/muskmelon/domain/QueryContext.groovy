package org.sai.muskmelon.domain

/**
 * Created by IntelliJ IDEA.
 * User: Sai
 * Date: 04/03/12
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */
class QueryContext implements Serializable {
    Date startDate
    Date endDate
    String identifier
    String versionNumber


    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;

        QueryContext that = (QueryContext) o;

        if (endDate != that.endDate) return false;
        if (identifier != null && that.identifier != null && identifier != that.identifier) return false;
        if (startDate != null && that.startDate != null && startDate != that.startDate) return false;
        if (versionNumber != null && that.versionNumber != null && versionNumber != that.versionNumber) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (versionNumber != null ? versionNumber.hashCode() : 0);
        return result;
    }


    public String toString ( ) {
    return "QueryContext{" +
    "startDate=" + startDate +
    ", endDate=" + endDate +
    ", identifier='" + identifier + '\'' +
    ", versionNumber='" + versionNumber + '\'' +
    '}' ;
    }}
