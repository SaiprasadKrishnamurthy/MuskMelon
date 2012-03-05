package org.sai.muskmelon.domain

/**
 * Created by IntelliJ IDEA.
 * User: Sai
 * Date: 04/03/12
 * Time: 23:18
 * To change this template use File | Settings | File Templates.
 */
public interface Connector {
       public <T extends Visitable> List<T> getAll(QueryContext queryParam)
       public <T extends Visitable> List<T> getDelta(QueryContext queryParam)
}