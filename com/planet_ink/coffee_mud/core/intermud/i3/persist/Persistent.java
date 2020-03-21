package com.planet_ink.coffee_mud.core.intermud.i3.persist;

/**
 * Specified methods which must be implemented by
 * objects wishing to be saved.
 * @author George Reese (borg@imaginary.com)
 * @version 1.0
 */
public interface Persistent 
{
	/**
	 * The Persistent has not yet been modified since last
	 * save or restore.
	 */
	static public final int UNMODIFIED = 0;
	/**
	 * The Peersistence has been modified since last save
	 * or restore.
	 */
	static public final int MODIFIED = 1;
	/**
	 * The Persistent is a brand new object.
	 */
	static public final int NEW = 2;
	/**
	 * The Persistent needs to be deleted from the data store.
	 */
	static public final int DELETED = 3;

	/**
	 * Prescribes a method for restoration from a data
	 * store.  An implementation will usually call the
	 * object's PersistentPeer method to perform the
	 * actual save.
	 * @throws PersistenceException thrown when an error occurs in restoring
	 * @see com.planet_ink.coffee_mud.core.intermud.i3.persist.PersistentPeer
	 */
	public abstract void restore() throws PersistenceException;

	/**
	 * Prescribes a method for saving this object's data
	 * to a data store.  An implementation will usually
	 * check to see if the object has been modified and
	 * trigger the actual saving mechanism in its
	 * PersistentPeer implementation.
	 * @throws PersistenceException thrown when an error occurs in saving
	 * @see com.planet_ink.coffee_mud.core.intermud.i3.persist.PersistentPeer
	 */
	public abstract void save() throws PersistenceException;
}
