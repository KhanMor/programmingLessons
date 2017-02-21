package jaxbwork;

import org.apache.log4j.Logger;

import java.util.Set;

/**
 * Created by Mordr on 21.02.2017.
 */
public class InsertedWrapper {
    private final Set<Object> insertedObjects;
    private static final Logger logger = Logger.getLogger(InsertedWrapper.class);

    public InsertedWrapper(Set<Object> insertedObjects) {
        this.insertedObjects = insertedObjects;
    }

    public boolean contains(Object object) {
        boolean isInSet = insertedObjects.contains(object);
        logger.trace("Search object " + object + " in " + insertedObjects + " result: " + isInSet);
        return isInSet;
    }

    public void add(Object object) {
        insertedObjects.add(object);
    }
}
