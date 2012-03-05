package org.sai.muskmelon.domain

/**
 * Created by IntelliJ IDEA.
 * User: Sai
 * Date: 04/03/12
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */
public interface Repository {
    public <T extends Visitable> void save(List<T> visitable)
}