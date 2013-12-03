/* Copyright (C) 2003 Vladimir Roubtsov. All rights reserved.
 * 
 * This program and the accompanying materials are made available under
 * the terms of the Common Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * $Id: InterfaceCollection.java,v 1.1.1.1 2004/05/09 16:57:46 vlad_r Exp $
 */
package com.vladium.jcd.cls;

import java.io.IOException;
import java.util.Vector;

import com.vladium.jcd.lib.UDataOutputStream;

// ----------------------------------------------------------------------------
/**
 * @author (C) 2001, Vlad Roubtsov
 */
final class InterfaceCollection implements IInterfaceCollection
{
    // public: ................................................................
    
    // ACCESSORS:

    @Override
	public int get (final int offset)
    {
        return m_interfaces.get (offset);
    }
    
    @Override
	public int size ()
    {
        return m_interfaces.size ();
    }
    
    // Cloneable:
    
    /**
     * Performs a deep copy.
     */
    @Override
	public Object clone ()
    {
        try
        {
            final InterfaceCollection _clone = (InterfaceCollection) super.clone ();
            
            // deep clone:
            _clone.m_interfaces = (Vector<Integer>) m_interfaces.clone ();
            
            return _clone;
        }
        catch (CloneNotSupportedException e)
        {
            throw new InternalError (e.toString ());
        }        
    }
        
    // IClassFormatOutput:
    
    @Override
	public void writeInClassFormat (final UDataOutputStream out) throws IOException
    {
        int _interfaces_count = m_interfaces.size (); // use size() if class becomes non-final
        out.writeU2 (_interfaces_count);
        
        for (int i = 0; i < _interfaces_count; i++)
        {
            out.writeU2 (get (i));
        }
    }
    
    // Visitor:
    
    @Override
	public void accept (final IClassDefVisitor visitor, final Object ctx)
    {
        visitor.visit (this, ctx);
    }
    
    
    // MUTATORS:

    @Override
	public int add (final int interface_index)
    {
        final int newoffset = m_interfaces.size (); // use size() if class becomes non-final
        m_interfaces.add (interface_index);
        
        return newoffset;
    }
    
    @Override
	public int set (final int offset, final int interface_index)
    {
        return m_interfaces.set (offset, interface_index);
    }
    
    // protected: .............................................................

    // package: ...............................................................


    InterfaceCollection (final int capacity)
    {
        m_interfaces = capacity < 0 ? new Vector<Integer> () : new Vector<Integer> (capacity);
    }

    // private: ...............................................................

    
    private Vector<Integer> m_interfaces; // vector of constant pool indices
    
} // end of class
// ----------------------------------------------------------------------------
