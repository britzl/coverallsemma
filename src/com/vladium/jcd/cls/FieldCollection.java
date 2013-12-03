/* Copyright (C) 2003 Vladimir Roubtsov. All rights reserved.
 * 
 * This program and the accompanying materials are made available under
 * the terms of the Common Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * $Id: FieldCollection.java,v 1.1.1.1 2004/05/09 16:57:45 vlad_r Exp $
 */
package com.vladium.jcd.cls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.vladium.jcd.lib.UDataOutputStream;

// ----------------------------------------------------------------------------
/**
 * @author (C) 2001, Vlad Roubtsov
 */
final class FieldCollection implements IFieldCollection
{
    // public: ................................................................

    // ACCESSORS:
        
    @Override
	public Field_info get (final int offset)
    {
        return (Field_info) m_fields.get (offset);
    }
    
    @Override
	public Integer [] get (final ClassDef cls, final String name)
    {
        if (cls == null) throw new IllegalArgumentException  ("null input: cls");
        
        // TODO: hash impl [not possible without having access to the parent ClassDef]
        
        final int count = m_fields.size (); // use size() if class becomes non-final
        final Vector<Integer> result = new Vector<Integer> (count);

        for (int f = 0; f < count; ++ f)
        {
            final Field_info field = (Field_info) m_fields.get (f);
            
            if (field.getName (cls).equals (name))  
                result.add (f);
        }
        
        return result.toArray(new Integer[0]); // IntVector optimizes for the empty case 
    }
    
    @Override
	public int size ()
    {
        return m_fields.size ();
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
            final FieldCollection _clone = (FieldCollection) super.clone ();
            
            // deep clone:
            final int fields_count = m_fields.size (); // use size() if class becomes non-final
            _clone.m_fields = new ArrayList<Field_info> (fields_count);
            for (int f = 0; f < fields_count; ++ f)
            {
                _clone.m_fields.add ((Field_info) (m_fields.get (f)).clone ());
            }
            
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
        final int fields_count = m_fields.size (); // use size() if class becomes non-final
        out.writeU2 (fields_count);
        
        for (int i = 0; i < fields_count; i++)
        {
            get (i).writeInClassFormat (out);
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
	public int add (final Field_info field)
    {
        final int newoffset = m_fields.size (); // use size() if class becomes non-final
        m_fields.add (field);
        
        return newoffset;
    }
    
    @Override
	public Field_info set (final int offset, final Field_info field)
    {
        return (Field_info) m_fields.set (offset, field);
    }
    
    // protected: .............................................................

    // package: ...............................................................


    FieldCollection (final int capacity)
    {
        m_fields = capacity < 0 ? new ArrayList<Field_info> () : new ArrayList<Field_info> (capacity);
    }

    // private: ...............................................................

    
    private List<Field_info> m_fields; // never null

} // end of class
// ----------------------------------------------------------------------------
